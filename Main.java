package com.expnsetracker;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.expnsetracker.gui.MainGUI;
import com.expnsetracker.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try {
            Connection conn = DatabaseConnection.getDBConnection();
            System.out.println("Database connection successful!");
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            System.exit(1);
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.err.println("Could not set look and feel: " + e.getMessage());
        }
        SwingUtilities.invokeLater(() -> {
            MainGUI mainGui = new MainGUI();  
            mainGui.setVisible(true);        
        });
    }
}
