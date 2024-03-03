package com.todo.app.config;

import com.todo.app.security.JwtAuthenticationEntryPoint;
import com.todo.app.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "Bearer"
)
public class SpringSecurityConfig {
@Autowired
private JwtAuthenticationEntryPoint authenticationEntryPoint;
@Autowired
private JwtAuthenticationFilter authenticationFilter;
@Bean
public static PasswordEncoder passwordEncoder(){
return new BCryptPasswordEncoder();
}
@Bean
SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
http.csrf(AbstractHttpConfigurer::disable)
.authorizeHttpRequests((authorize) -> {
authorize.requestMatchers("/api/auth/register", "/api/auth/login").permitAll();
authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll();
authorize.requestMatchers("/swagger-ui/**").permitAll();
authorize.requestMatchers("/v3/api-docs/**").permitAll();
authorize.requestMatchers(HttpMethod.PATCH,"api/todos/{id}/complete").hasAnyAuthority("ROLE_ADMIN","ROLE_USER");
authorize.requestMatchers(HttpMethod.PATCH,"api/todos/{id}/in-complete").hasAnyAuthority("ROLE_ADMIN","ROLE_USER");
authorize.requestMatchers(HttpMethod.POST,"/api/todos/").hasRole("ADMIN");
authorize.requestMatchers(HttpMethod.PUT,"/api/todos/{ID}").hasRole("ADMIN");
authorize.requestMatchers(HttpMethod.DELETE,"/api/todos/{ID}").hasRole("ADMIN");

authorize.anyRequest().authenticated();
}).httpBasic(Customizer.withDefaults());
http.exceptionHandling( exception -> exception
.authenticationEntryPoint(authenticationEntryPoint));
http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
return http.build();
}
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws
Exception {
return configuration.getAuthenticationManager();
}
}