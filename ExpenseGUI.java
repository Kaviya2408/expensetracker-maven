package com.expnsetracker.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.expnsetracker.dao.ExpenseTrackerDAO;
import com.expnsetracker.model.Category;
import com.expnsetracker.model.Expense;

public class ExpenseGUI extends JFrame {

    private ExpenseTrackerDAO dao;
    private JTable expenseTable;
    private DefaultTableModel tableModel;

    private JComboBox<Category> categoryCombo;
    private JTextField amountField;
    private JTextField dateField;
    private JButton addButton;
    private JButton deleteButton;

    public ExpenseGUI() {
        dao = new ExpenseTrackerDAO();
        setTitle("Expense Manager");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeComponents();
        setupLayout();
        setupListeners();
        loadExpenses();
        loadCategories();

        pack();
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        String[] columns = {"ID", "Category", "Amount", "Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        expenseTable = new JTable(tableModel);

        categoryCombo = new JComboBox<>();
        amountField = new JTextField(10);
        dateField = new JTextField(10);
        addButton = new JButton("Add Expense");
        deleteButton = new JButton("Delete Expense");
    }

    private void setupLayout() {
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryCombo);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        inputPanel.add(dateField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(expenseTable), BorderLayout.CENTER);
    }

    private void setupListeners() {
        addButton.addActionListener(e -> addExpense());
        deleteButton.addActionListener(e -> deleteExpense());
    }

    private void loadCategories() {
        try {
            List<Category> categories = dao.getAllCategories();
            categoryCombo.removeAllItems();
            for (Category c : categories) {
                categoryCombo.addItem(c);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading categories: " + e.getMessage());
        }
    }

    private void loadExpenses() {
        try {
            List<Expense> expenses = dao.getAllExpenses();
            tableModel.setRowCount(0);
            for (Expense ex : expenses) {
                tableModel.addRow(new Object[]{
                        ex.getId(),
                        dao.getCategoryNameById(ex.getCategoryId()),
                        ex.getAmount(),
                        ex.getDate()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading expenses: " + e.getMessage());
        }
    }

    private void addExpense() {
        Category category = (Category) categoryCombo.getSelectedItem();
        if (category == null) {
            JOptionPane.showMessageDialog(this, "Select a category.");
            return;
        }
        String amountText = amountField.getText().trim();
        String date = dateField.getText().trim();
        try {
            double amount = Double.parseDouble(amountText);
            dao.addExpense(new Expense(category.getId(), amount, date));
            amountField.setText("");
            dateField.setText("");
            loadExpenses();
            JOptionPane.showMessageDialog(this, "Expense added successfully");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding expense: " + e.getMessage());
        }
    }

    private void deleteExpense() {
        int row = expenseTable.getSelectedRow();
        if (row >= 0) {
            int id = (int) tableModel.getValueAt(row, 0);
            try {
                int option=JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this expense?");
                if(option==0){
                dao.deleteExpense(id);
                JOptionPane.showMessageDialog(this, "Expense deleted successfully");
                loadExpenses();
                } else if(option==1)
                {
                    JOptionPane.showMessageDialog(this, "Delete Expense cancelled");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error deleting expense: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select an expense to delete.");
        }
    }
}
