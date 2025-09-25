package com.expnsetracker.gui;

import com.expnsetracker.dao.ExpenseTrackerDAO;
import com.expnsetracker.model.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class CategoryGUI extends JFrame {

    private ExpenseTrackerDAO dao;
    private JTable categoryTable;
    private DefaultTableModel tableModel;
    private JTextField nameField;
    private JButton addButton;

    public CategoryGUI() {
        dao = new ExpenseTrackerDAO();
        setTitle("Category Manager");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeComponents();
        setupLayout();
        setupListeners();
        loadCategories();

        pack();
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        String[] columns = {"ID", "Name"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        categoryTable = new JTable(tableModel);
        nameField = new JTextField(15);
        addButton = new JButton("Add Category");
    }

    private void setupLayout() {
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(categoryTable), BorderLayout.CENTER);
    }

    private void setupListeners() {
        addButton.addActionListener(e -> addCategory());
    }

    private void loadCategories() {
        try {
            List<Category> categories = dao.getAllCategories();
            tableModel.setRowCount(0);
            for (Category c : categories) {
                tableModel.addRow(new Object[]{c.getId(), c.getName()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading categories: " + e.getMessage());
        }
    }

    private void addCategory() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter category name.");
            return;
        }
        try {
            dao.addCategory(new Category(name));
            nameField.setText("");
            loadCategories();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding category: " + e.getMessage());
        }
    }
}
