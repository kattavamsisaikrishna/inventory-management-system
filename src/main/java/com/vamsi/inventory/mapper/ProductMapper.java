package com.vamsi.inventory.mapper;


import com.vamsi.inventory.dto.ProductRequest;
import com.vamsi.inventory.dto.ProductResponse;
import com.vamsi.inventory.entity.Product;

public class ProductMapper {
   public static ProductResponse toProductResponse(Product product){
       ProductResponse productResponse = new ProductResponse();
       productResponse.setId(product.getId());
       productResponse.setName(product.getName());
       productResponse.setDescription(product.getDescription());
       productResponse.setPrice(product.getPrice());
       productResponse.setStock(product.getStock());
       productResponse.setCreatedAt(product.getCreatedAt().toString());
       productResponse.setUpdatedAt(product.getUpdateAt().toString());
       return productResponse;
   }

   public static Product toProduct(ProductRequest request){
       Product product = new Product();
       product.setName(request.getName());
       product.setPrice(request.getPrice());
       product.setStock(request.getStock());
       product.setDescription(request.getDescription());
       return product;
   }
}
