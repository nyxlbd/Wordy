package com.wordy.player;

import com.wordy.common.WordyLoginUI;
import com.wordy.common.config.ClientConfiguration;
import com.wordy.common.config.ServerConnectionDialog;
import com.wordy.player.service.GrpcConnectionManager;
import javax.swing.JOptionPane;

public class PlayerApp {
    
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
            // PlayerApp is for players
            if ("PLAYER".equalsIgnoreCase(userRole)) {
                System.out.println("Player " + username + " logged in successfully!");
                new WordyDashboard(username).setVisible(true);
            } else if ("ADMIN".equalsIgnoreCase(userRole)) {
                // Admin logged in, but this is PlayerApp
                JOptionPane.showMessageDialog(null, 
                    "You logged in as an admin. Please use AdminApp to access the admin dashboard.",
                    "Admin Access", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Admin " + username + " attempted to login through PlayerApp.");
                // Show login screen again
                new WordyLoginUI((u, r) -> {}).setVisible(true);
            } else {
                System.out.println("Unknown user role: " + userRole);
            }
        });
        login.setVisible(true);
    }
}
