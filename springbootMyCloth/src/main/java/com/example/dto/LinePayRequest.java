package com.example.dto; // Ensure this matches your package structure

public class LinePayRequest {
    private String orderId;
    private int amount;
    private String currency;
    // Add other fields as needed

    // Getters and setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    // Add other getters and setters as needed
}
