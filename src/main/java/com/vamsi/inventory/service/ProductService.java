package com.vamsi.inventory.service;

import com.vamsi.inventory.dto.ProductRequest;
import com.vamsi.inventory.dto.ProductResponse;
import com.vamsi.inventory.entity.Product;
import com.vamsi.inventory.exception.ProductAlreadyExistsException;
import com.vamsi.inventory.exception.ProductNotFoundException;
import com.vamsi.inventory.mapper.ProductMapper;
import com.vamsi.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public ProductResponse createProduct(ProductRequest request){
        if(productRepository.existsByNameIgnoreCase(request.getName().trim())){
            throw new ProductAlreadyExistsException("Product with name \'"+request.getName()+"\' already exists");
        }
        Product product = ProductMapper.toProduct(request);
        Product savedProduct = productRepository.saveAndFlush(product);
        return ProductMapper.toProductResponse(savedProduct);
    }

    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product Not Found With Id: "+id));
        return ProductMapper.toProductResponse(product);
    }

   @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest productRequest){

        Product product = productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product Not found with Id "+id));

        if( !product.getName().equalsIgnoreCase(productRequest.getName().trim())   && productRepository.existsByNameIgnoreCase(productRequest.getName())){
            throw new ProductAlreadyExistsException("Product with name \'"+productRequest.getName()+"\' already exists");
        }

        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setStock(productRequest.getStock());

        productRepository.saveAndFlush(product); // ensures @UpdateTimeStamp is applied
        return ProductMapper.toProductResponse(product);
    }

    @Transactional
    public void deleteProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product Not Found With Id: "+id));
        productRepository.delete(product);
    }
}
