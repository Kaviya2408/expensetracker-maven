package com.expnsetracker.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI extends JFrame {

    private JButton categoryButton;
    private JButton expenseButton;

    public MainGUI() {
        setTitle("Expense Tracker - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use FlowLayout to place components horizontally
        setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50)); // center, 50px horizontal & vertical gap

        // Initialize buttons
        categoryButton = new JButton("Category");
        expenseButton = new JButton("Expense");

        // Add buttons directly
        add(categoryButton);
        add(expenseButton);

        // Button actions
        categoryButton.addActionListener(e -> {
            new CategoryGUI().setVisible(true);
        });

        expenseButton.addActionListener(e -> {
            new ExpenseGUI().setVisible(true);
        });
        pack();
        setLocationRelativeTo(null); 
        setVisible(true);
    }
}
