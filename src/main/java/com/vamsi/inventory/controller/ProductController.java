package com.vamsi.inventory.controller;

import com.vamsi.inventory.dto.ProductRequest;
import com.vamsi.inventory.dto.ProductResponse;
import com.vamsi.inventory.entity.Product;
import com.vamsi.inventory.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request){
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable
            @Min(value = 1, message = "Minimum Value should be 1")
            Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable  @Min(value = 1, message = "Minimum Value should be 1") Long id,
            @Valid @RequestBody ProductRequest request
            ){
        return ResponseEntity.ok(productService.updateProduct(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable  @Min(value = 1, message = "Minimum Value should be 1") Long id){
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}
