package com.delivery.deliveryapp.model;

import java.util.Date;

public class Order {
    private String orderId;
    private Product product;
    private Date orderDate;

    public Order() {
    }

    public Order(String orderId, Product product, Date orderDate) {
        this.orderId = orderId;
        this.product = product;
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", product=" + product +
                ", orderDate=" + orderDate +
                '}';
    }
}
