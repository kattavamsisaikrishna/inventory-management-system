package com.vamsi.inventory.service;

import com.vamsi.inventory.dto.ProductRequest;
import com.vamsi.inventory.dto.ProductResponse;
import com.vamsi.inventory.entity.Product;
import com.vamsi.inventory.exception.ProductNotFoundException;
import com.vamsi.inventory.mapper.ProductMapper;
import com.vamsi.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findAll();
        List<ProductResponse> responses = products.stream()
                .map(product -> ProductMapper.toProductResponse(product))
                .collect(Collectors.toList());
        return responses;
    }

    public ProductResponse createProduct(ProductRequest request){
        Product product = ProductMapper.toProduct(request);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toProductResponse(savedProduct);
    }

    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product Not Found With Id: "+id));
        return ProductMapper.toProductResponse(product);
    }
}
