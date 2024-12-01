package com.delivery.deliveryapp.model;

public class Product {
    String name;
    String description;
    Double price;
    String icon;



    Product(){}

    public Product(String name, String description, Double price, String icon) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
