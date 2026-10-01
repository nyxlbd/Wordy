package com.wordy.admin;

import com.wordy.admin.service.AdminServiceClient;
import com.wordy.admin.service.AdminServiceClient.PlayerData;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Manage Players UI
 * Responsible Team Member: ADELYN JOY TELA
 * Interface for viewing player list, searching, and managing player accounts with action buttons
 */
public class ManagePlayersUI extends JFrame {

    private AdminServiceClient adminServiceClient;
    private JPanel tablePanel;

    /**
     * Initializes and displays the player management interface.
     * Responsible Team Member: ADELYN JOY TELA
     * Shows player list with search, edit, and delete action buttons.
     */
    public ManagePlayersUI() {
        this.adminServiceClient = new AdminServiceClient();
        setTitle("Wordy - Admin");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(30, 30, 30));

        // ===== Main Container =====
        JPanel mainContainer = new JPanel();
        mainContainer.setBounds(20, 20, 940, 620);
        mainContainer.setBackground(new Color(80, 80, 80));
        mainContainer.setLayout(null);
        add(mainContainer);

        // ===== Header =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 940, 80);
        headerPanel.setBackground(new Color(80, 80, 80));
        headerPanel.setLayout(null);
        mainContainer.add(headerPanel);

        JLabel title = new JLabel("Wordy - Admin");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBounds(30, 20, 200, 40);
        headerPanel.add(title);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(820, 25, 80, 25);
        logoutBtn.setBackground(new Color(200, 200, 200));
        logoutBtn.setBorder(BorderFactory.createEmptyBorder());
        headerPanel.add(logoutBtn);

        // ===== Divider Title =====
        JPanel divider = new JPanel();
        divider.setBounds(0, 80, 940, 70);
        divider.setLayout(null);
        divider.setOpaque(false); // no background

        JLabel sectionTitle = new JLabel("Player Management");
        sectionTitle.setBounds(20, 15, 400, 40);
        sectionTitle.setHorizontalAlignment(SwingConstants.LEFT);
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        sectionTitle.setForeground(Color.WHITE);

        divider.add(sectionTitle);
        mainContainer.add(divider);

        // ===== Search Bar =====
        JTextField searchField = new JTextField("Search by Username");
        searchField.setBounds(680, 170, 200, 25);
        mainContainer.add(searchField);

        // Add search listener
        searchField.addActionListener(e -> refreshPlayerTable(tablePanel, searchField.getText().trim()));

        // ===== Table Panel =====
        tablePanel = new JPanel();
        tablePanel.setBounds(20, 210, 900, 360);
        tablePanel.setBackground(new Color(220, 220, 220));
        tablePanel.setLayout(null);
        mainContainer.add(tablePanel);

        // ===== Table Headers =====
        String[] headers = {"Username", "Status", "Wins", "Joined", "Actions"};
        int[] xPos = {80, 220, 360, 500, 680};

        for (int i = 0; i < headers.length; i++) {
            JLabel headerLabel = new JLabel(headers[i]);
            headerLabel.setFont(new Font("Arial", Font.BOLD, 14));
            headerLabel.setBounds(xPos[i], 10, 120, 30);
            tablePanel.add(headerLabel);
        }

        // Load player data from server
        loadPlayerData(tablePanel);
    }

    /**
     * Loads player data from the server and displays in the table
     * Responsible Team Member: ADELYN JOY TELA
     */
    private void loadPlayerData(JPanel tablePanel) {
        SwingUtilities.invokeLater(() -> {
            try {
                List<PlayerData> players = adminServiceClient.getAllPlayers();
                int[] xPos = {80, 220, 360, 500, 680};
                int y = 50;
                for (PlayerData player : players) {
                    if (y > 290) break; // Max 5 rows visible
                    addPlayerRow(tablePanel, y, player.username, player.status, String.valueOf(player.wins), player.playerId, xPos);
                    y += 60;
                }
                tablePanel.repaint();
            } catch (Exception e) {
                System.err.println("Error loading player data: " + e.getMessage());
            }
        });
    }

    /**
     * Refreshes the player table with search results or all players
     * Responsible Team Member: ADELYN JOY TELA
     * 
     * @param tablePanel the table panel to refresh
     * @param searchQuery the search term (empty string to show all)
     */
    private void refreshPlayerTable(JPanel tablePanel, String searchQuery) {
        // Remove all player rows (keep header)
        java.awt.Component[] components = tablePanel.getComponents();
        for (java.awt.Component comp : components) {
            if (!(comp instanceof JLabel && comp.getBounds().y < 50)) {
                tablePanel.remove(comp);
            }
        }

        SwingUtilities.invokeLater(() -> {
            try {
                List<PlayerData> players;
                if (searchQuery == null || searchQuery.isEmpty()) {
                    players = adminServiceClient.getAllPlayers();
                } else {
                    players = adminServiceClient.searchPlayers(searchQuery);
                }

                int[] xPos = {80, 220, 360, 500, 680};
                int y = 50;
                for (PlayerData player : players) {
                    if (y > 290) break; // Max 5 rows visible
                    addPlayerRow(tablePanel, y, player.username, player.status, String.valueOf(player.wins), player.playerId, xPos);
                    y += 60;
                }
                tablePanel.repaint();
            } catch (Exception e) {
                System.err.println("Error refreshing table: " + e.getMessage());
            }
        });
    }

    /**
     * Adds a player row to the table
     * Responsible Team Member: ADELYN JOY TELA
     */
    private void addPlayerRow(JPanel tablePanel, int y, String username, String status, String wins, String joined, int[] xPos) {
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setBounds(xPos[0], y, 100, 30);
        tablePanel.add(usernameLabel);

        JLabel statusLabel = new JLabel(status);
        statusLabel.setBounds(xPos[1], y, 100, 30);
        tablePanel.add(statusLabel);

        JLabel winsLabel = new JLabel(wins);
        winsLabel.setBounds(xPos[2], y, 100, 30);
        tablePanel.add(winsLabel);

        JLabel joinedLabel = new JLabel(joined);
        joinedLabel.setBounds(xPos[3], y, 120, 30);
        tablePanel.add(joinedLabel);

        // Edit Button
        JButton editBtn = new JButton("Edit");
        editBtn.setBounds(xPos[4], y, 60, 25);
        editBtn.setBackground(new Color(60, 60, 60));
        editBtn.setForeground(Color.WHITE);
        editBtn.setFocusPainted(false);
        editBtn.setBorder(BorderFactory.createLineBorder(new Color(90,90,90), 1, true));
        editBtn.addActionListener(e -> new AdminEditAccountUI(username).setVisible(true));
        tablePanel.add(editBtn);

        // Delete Button
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(xPos[4] + 70, y, 70, 25);
        deleteBtn.setBackground(new Color(60, 60, 60));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setBorder(BorderFactory.createLineBorder(new Color(90,90,90), 1, true));
        deleteBtn.addActionListener(e -> {
            if (adminServiceClient.deletePlayer(username)) {
                tablePanel.remove(usernameLabel);
                tablePanel.remove(statusLabel);
                tablePanel.remove(winsLabel);
                tablePanel.remove(joinedLabel);
                tablePanel.remove(editBtn);
                tablePanel.remove(deleteBtn);
                tablePanel.repaint();
                tablePanel.revalidate();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete player.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        tablePanel.add(deleteBtn);

        // Row Divider
        JSeparator sep = new JSeparator();
        sep.setBounds(20, y + 40, 860, 1);
        sep.setForeground(new Color(180, 180, 180));
        tablePanel.add(sep);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManagePlayersUI().setVisible(true));
    }
}