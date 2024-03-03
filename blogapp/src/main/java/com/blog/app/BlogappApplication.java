package com.blog.app;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Blog App REST APIs",
                description = "Spring Boot Blog App REST APIs Documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Sk Upahar Ali",
                        email = "upaharalisk@gmail.com"
                ),
                license = @License(
                        name = "Sk Upahar Ali"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring Boot Blog App Github Repository",
                url = "https://github.com/"
        )
)
public class BlogappApplication {


    public static void main(String[] args) {
        SpringApplication.run(BlogappApplication.class, args);
    }

}
