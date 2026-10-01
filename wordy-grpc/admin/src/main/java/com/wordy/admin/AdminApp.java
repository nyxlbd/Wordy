package com.wordy.admin;

import com.wordy.common.WordyLoginUI;
import com.wordy.common.config.ClientConfiguration;
import com.wordy.common.config.ServerConnectionDialog;
import com.wordy.admin.service.GrpcConnectionManager;
import javax.swing.JOptionPane;

public class AdminApp {
    
    public static void main(String[] args) {
        // Step 1: Show server configuration dialog BEFORE login
        ClientConfiguration config = new ClientConfiguration();
        ServerConnectionDialog serverDialog = new ServerConnectionDialog(
            config.getServerHost(),
            config.getServerPort()
        );
        serverDialog.setVisible(true);
        
        if (serverDialog.isConfirmed()) {
            // User confirmed server settings, update if changed
            GrpcConnectionManager connManager = GrpcConnectionManager.getInstance();
            connManager.setServerAddress(serverDialog.getHost(), serverDialog.getPort());
            System.out.println("Server set to: " + serverDialog.getHost() + ":" + serverDialog.getPort());
        } else {
            // User cancelled - use saved configuration
            System.out.println("Using saved server configuration: " + config.getServerAddress());
        }
        
        // Step 2: Show login screen
        WordyLoginUI login = new WordyLoginUI((username, userRole) -> {
            // AdminApp is for admins
            if ("ADMIN".equalsIgnoreCase(userRole)) {
                System.out.println("Admin " + username + " logged in successfully!");
                new AdminDashboard(username).setVisible(true);
            } else if ("PLAYER".equalsIgnoreCase(userRole)) {
                // Player logged in, but this is AdminApp
                JOptionPane.showMessageDialog(null, 
                    "You logged in as a player. Please use PlayerApp to access the player dashboard.",
                    "Player Access", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Player " + username + " attempted to login through AdminApp. Redirecting to login...");
                // Show login screen again
                new WordyLoginUI((u, r) -> {}).setVisible(true);
            } else {
                System.out.println("Unknown user role: " + userRole);
            }
        });
        login.setVisible(true);
    }
}
