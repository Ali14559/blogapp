package com.blog.app.controller;

import com.blog.app.dto.CategoryDto;
import com.blog.app.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@Tag(
        name = "REST APIs for Category Resource"
)
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Create Category REST API",
            description = "Create Category REST API is used to save category into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/addcategory")
    public ResponseEntity<CategoryDto>addCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto saveCatDto = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(saveCatDto,HttpStatus.CREATED);
        // return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Get Category By Id REST API",
            description = "Get Category By Id REST API is used to get single Category from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    // Build Get Category REST API
    @GetMapping("/getcategory/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long categoryId){
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

    @Operation(
            summary = "Get All Categories REST API",
            description = "Get All Categories REST API is used to fetch all the categories from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )

    // Build Get All Categories REST API

    @GetMapping("/getallcategorys")
    public ResponseEntity<List<CategoryDto>> getCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(
            summary = "update Category REST API",
            description = "Update Category REST API is used to update a particular category in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    // Build Update Category REST API
    @PutMapping("/updatecategory/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
                                                      @PathVariable("id") Long categoryId){
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
    }

    @Operation(
            summary = "Delete Category REST API",
            description = "Delete Category REST API is used to delete a particular category from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    // Build Delete Category REST API
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")

    @DeleteMapping("/deletecategory/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted successfully!.");
    }

}
