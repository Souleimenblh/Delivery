package com.delivery.deliveryapp.model;

import java.util.List;

public class Category {
    private String icon;
    private String name;
    private boolean isNew;
    private List<Product> products;

    public Category() {
    }

    public Category(String icon, String name, boolean isNew, List<Product> products) {
        this.icon = icon;
        this.name = name;
        this.isNew = isNew;
        this.products = products;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public boolean getNew() {
        return isNew;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public void setTitle(String title) {
        this.name = title;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }


}
