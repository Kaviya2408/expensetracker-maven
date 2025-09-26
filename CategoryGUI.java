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
    private JButton deleteButton;
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
        deleteButton=new JButton("delete category");
    }

    private void setupLayout() {
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(categoryTable), BorderLayout.CENTER);
    }

    private void setupListeners() {
        addButton.addActionListener(e -> addCategory());
        deleteButton.addActionListener(e -> deleteCategory());
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
            JOptionPane.showMessageDialog(this,"Category Added successfully");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding category: " + e.getMessage());
        }
    }
    private void deleteCategory()
    {
        int row=categoryTable.getSelectedRow();
        try{
           int todelete =JOptionPane.showConfirmDialog(this, "Are Sure you to delete row");
            if (todelete==0 && row >= 0) {
                int id = (int) tableModel.getValueAt(row, 0);
                dao.deleteCategory(id);
                loadCategories();
                JOptionPane.showMessageDialog(this,"Category deleted successfully");
            }
            else if(todelete==1)
            {
            JOptionPane.showMessageDialog(this, "Delete Category cancelled");
            } 
            else {
            JOptionPane.showMessageDialog(this, "Select an Category to delete.");
            }
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(this,"Error in deleting category"+e.getMessage());
        }
    }
}
