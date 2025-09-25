package com.expnsetracker.dao;

import com.expnsetracker.model.Category;
import com.expnsetracker.model.Expense; 
import com.expnsetracker.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ExpenseTrackerDAO {
    public List<Category> getAllCategories() throws SQLException {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories ORDER BY ID ASC";
        try (Connection conn=DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                list.add(new Category(rs.getInt("id"), rs.getString("name")));
            }


        }
        return list;
    }

    public int addCategory(Category category) throws SQLException {
        String sql = "INSERT INTO categories(name) VALUES(?)";
        try (Connection conn = DatabaseConnection.getDBConnection();
            PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) 
            {
            stmt.setString(1, category.getName());
            stmt.executeUpdate();
            try(ResultSet rs=stmt.getGeneratedKeys()) {
                if(rs.next()) 
                return rs.getInt(1);
            }
        }
        return -1;
    }
    public List<Expense> getAllExpenses() throws SQLException {
        List<Expense> list = new ArrayList<>();
        String sql = "SELECT * FROM expenses ORDER BY ID ASC";
        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                list.add(new Expense(
                        rs.getInt("id"),
                        rs.getInt("category_id"),
                        rs.getDouble("amount"),
                        rs.getDate("expense_date").toString()
                ));
            }
        }
        return list;
    }

    public int addExpense(Expense expense) throws SQLException {
        String sql = "INSERT INTO expenses(category_id, amount, expense_date) VALUES(?,?,?)";
        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, expense.getCategoryId());
            stmt.setDouble(2, expense.getAmount());
            stmt.setDate(3, Date.valueOf(expense.getDate()));
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if(rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public void deleteExpense(int id) throws SQLException {
        String sql = "DELETE FROM expenses WHERE id=?";
        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    public String getCategoryNameById(int categoryId) throws SQLException {
    String sql = "SELECT name FROM categories WHERE id=?";
    try (Connection conn = DatabaseConnection.getDBConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, categoryId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("name");
            }
        }
    }
    return "";
    
}
}
