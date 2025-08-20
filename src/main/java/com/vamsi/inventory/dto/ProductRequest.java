package com.vamsi.inventory.dto;

import jakarta.validation.constraints.*;

public class ProductRequest {

    @NotBlank(message = "name is required")
    @Size(min = 3,max = 120,message = "Product Name must be between 3 and 120 characters")
    private String name;

    @NotNull(message = "price is required")
    @DecimalMin(value = "0.00", message = "Price must be greater than equal to 0")
    @Digits(integer = 12, fraction = 2,message = "Price can have atmost 2 decimal places")
    private Double price;

    @NotNull(message = "stock is required")
    @Min(value = 0, message = "Stock must be positive value")
    private Integer stock;

    @Size(max = 255,message = "Description can be atmost 255 characters")
    private String description;

    public ProductRequest(){}

    public ProductRequest(String name, Double price, int stock, String description) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
