package com.expnsetracker.model;

public class Expense {
    private int id;
    private int categoryId;
    private double amount;
    private String date; 

    public Expense() {}

    public Expense(int categoryId, double amount, String date) {
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
    }

    public Expense(int id, int categoryId, double amount, String date) {
        this.id = id;
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
