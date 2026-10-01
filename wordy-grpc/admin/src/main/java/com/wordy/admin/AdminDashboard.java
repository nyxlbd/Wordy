package com.wordy.admin;

import com.wordy.admin.service.AdminServiceClient;
import com.wordy.admin.service.AdminServiceClient.PlayerData;
import com.wordy.common.WordyLoginUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;



/**
 * Admin Dashboard UI
 * Responsible Team Member: JENNY ANNE AWACAN
 * Main admin interface for managing players and accessing admin functions
 */
public class AdminDashboard extends JFrame {

    private String currentUsername;
    private AdminServiceClient adminServiceClient;
    private JPanel tablePanel;

    /**
     * Creates a new admin dashboard with no username.
     * Responsible Team Member: JENNY ANNE AWACAN
     */
    public AdminDashboard() {
        this(null);
    }

    /**
     * Initializes and displays the admin dashboard interface.
     * Responsible Team Member: JENNY ANNE AWACAN
     * Sets up player management section, search functionality, and admin controls.
     * 
     * @param username the logged-in admin's username (null if not authenticated)
     */
    public AdminDashboard(String username) {
        this.currentUsername = username;
        this.adminServiceClient = new AdminServiceClient();
        setTitle("Wordy - Admin");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        getContentPane().setBackground(new Color(30, 30, 30));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(20, 20, 940, 620);
        mainPanel.setBackground(new Color(80, 80, 80));
        add(mainPanel);

        // Header
        JLabel title = new JLabel("Wordy - Admin");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(30, 20, 300, 40);
        mainPanel.add(title);

        // Settings Button
        JButton settingsBtn = new JButton("Settings");
        settingsBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        settingsBtn.setBounds(710, 35, 90, 25);
        settingsBtn.setBackground(new Color(200, 200, 200));
        settingsBtn.setForeground(Color.BLACK);
        settingsBtn.setFocusPainted(false);
        settingsBtn.setBorder(BorderFactory.createEmptyBorder());
        settingsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        settingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminGameSettingsUI().setVisible(true);
            }
        });
        mainPanel.add(settingsBtn);

        // Logout Button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        logoutBtn.setBounds(820, 35, 80, 25);
        logoutBtn.setBackground(new Color(200, 200, 200));
        logoutBtn.setForeground(Color.BLACK);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder());
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call server logout before closing
                if (currentUsername != null) {
                    WordyLoginUI.logoutUser(currentUsername);
                }
                dispose();
                WordyLoginUI login = new WordyLoginUI((username, userRole) -> {
                    if ("ADMIN".equalsIgnoreCase(userRole)) {
                        new AdminDashboard(username).setVisible(true);
                    } else if ("PLAYER".equalsIgnoreCase(userRole)) {
                        JOptionPane.showMessageDialog(null, 
                            "You logged in as a player. Please use PlayerApp to access the player dashboard.",
                            "Player Access", JOptionPane.INFORMATION_MESSAGE);
                        new WordyLoginUI((u, r) -> {}).setVisible(true);
                    }
                });
                login.setVisible(true);
            }
        });

        mainPanel.add(logoutBtn);

        JPanel line = new JPanel();
        line.setBackground(Color.WHITE);
        line.setBounds(0, 80, 940, 2);
        mainPanel.add(line);


        JLabel sectionLabel = new JLabel("Player Management");
        sectionLabel.setForeground(Color.WHITE);
        sectionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        sectionLabel.setBounds(30, 100, 300, 30);
        mainPanel.add(sectionLabel);


        // Search Bar
        JTextField searchField = new JTextField("  🔍 Search by Username");
        searchField.setBounds(650, 100, 250, 30);
        searchField.setBackground(new Color(200, 200, 200));
        searchField.setForeground(Color.GRAY);
        searchField.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        mainPanel.add(searchField);

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("  🔍 Search by Username")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("  🔍 Search by Username");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        // Search button handler
        searchField.addActionListener(e -> refreshPlayerTable(tablePanel, searchField.getText().trim()));


        // Table
        tablePanel = new JPanel();
        tablePanel.setLayout(null);
        tablePanel.setBounds(30, 150, 880, 400);
        tablePanel.setBackground(new Color(210, 210, 210));
        mainPanel.add(tablePanel);


        JPanel headerStrip = new JPanel();
        headerStrip.setBounds(0, 0, 880, 60);
        headerStrip.setBackground(new Color(130, 130, 130));
        headerStrip.setLayout(null);
        tablePanel.add(headerStrip);

        // Columns in the Table
        String[] headers = {"Username", "Status", "Wins", "Joined", "Actions"};
        int[] xPos = {40, 220, 360, 520, 700};

        for (int i = 0; i < headers.length; i++) {
            JLabel header = new JLabel(headers[i]);
            header.setForeground(Color.WHITE);
            header.setFont(new Font("Arial", Font.BOLD, 16));
            header.setBounds(xPos[i], 15, 150, 30);
            headerStrip.add(header);
        }

        // Load player data from server
        loadPlayerData(tablePanel);
    }
    /**
     * Loads player data from the server and displays in the table
     * Responsible Team Member: JENNY ANNE AWACAN
     */
    private void loadPlayerData(JPanel tablePanel) {
        SwingUtilities.invokeLater(() -> {
            try {
                List<PlayerData> players = adminServiceClient.getAllPlayers();
                int y = 80;
                for (PlayerData player : players) {
                    if (y > 380) break; // Max 5 rows visible
                    addRow(tablePanel, y, player.username, player.status, String.valueOf(player.wins), player.playerId);
                    y += 60;
                }
                tablePanel.repaint();
            } catch (Exception e) {
                System.err.println("Error loading player data: " + e.getMessage());
                e.printStackTrace();
                JLabel errorLabel = new JLabel("Error loading players. Ensure server is running and database is connected.");
                errorLabel.setForeground(Color.RED);
                errorLabel.setBounds(30, 200, 600, 30);
                tablePanel.add(errorLabel);
                tablePanel.repaint();
            }
        });
    }

    /**
     * Refreshes the player table with search results or all players
     * Responsible Team Member: JENNY ANNE AWACAN
     * 
     * @param tablePanel the table panel to refresh
     * @param searchQuery the search term (empty string to show all)
     */
    private void refreshPlayerTable(JPanel tablePanel, String searchQuery) {
        // Remove all player rows (keep header)
        java.awt.Component[] components = tablePanel.getComponents();
        for (java.awt.Component comp : components) {
            if (comp instanceof JPanel && comp != tablePanel.getComponent(0)) {
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

                int y = 80;
                for (PlayerData player : players) {
                    if (y > 380) break; // Max 5 rows visible
                    addRow(tablePanel, y, player.username, player.status, String.valueOf(player.wins), player.playerId);
                    y += 60;
                }
                tablePanel.repaint();
            } catch (Exception e) {
                System.err.println("Error refreshing table: " + e.getMessage());
            }
        });
    }

    private void addRow(JPanel panel, int y, String name, String status, String wins, String joined) {

        JLabel nameLbl = new JLabel(name);
        nameLbl.setBounds(40, y, 150, 30);
        nameLbl.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(nameLbl);

        JLabel statusLbl = new JLabel(status);
        statusLbl.setBounds(220, y, 150, 30);
        statusLbl.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(statusLbl);

        JLabel winsLbl = new JLabel(wins);
        winsLbl.setBounds(360, y, 150, 30);
        winsLbl.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(winsLbl);

        JLabel joinedLbl = new JLabel(joined);
        joinedLbl.setBounds(520, y, 150, 30);
        joinedLbl.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(joinedLbl);

        JButton editBtn = new JButton("Edit");
        editBtn.setBounds(650, y, 80, 30);
        editBtn.setBackground(new Color(90, 90, 90));
        editBtn.setForeground(Color.WHITE);
        editBtn.setFocusPainted(false);
        editBtn.setBorder(BorderFactory.createLineBorder(new Color(120,120,120)));
        editBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // OPEN EDIT PLAYER ACCOUNT PANEL
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminEditAccountUI(name).setVisible(true);
            }
        });

        panel.add(editBtn);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(750, y, 80, 30);
        deleteBtn.setBackground(new Color(90, 90, 90));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setBorder(BorderFactory.createLineBorder(new Color(120,120,120)));
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(deleteBtn);

        deleteBtn.addActionListener(e -> {

            JDialog dialog = new JDialog(AdminDashboard.this, "Confirm Delete", true);
            dialog.setSize(350, 180);
            dialog.setLayout(null);
            dialog.setLocationRelativeTo(AdminDashboard.this);

            JPanel dPanel = new JPanel();
            dPanel.setLayout(null);
            dPanel.setBackground(new Color(240, 240, 240));
            dPanel.setBounds(0, 0, 350, 180);
            dialog.add(dPanel);

            JLabel msg = new JLabel("Are you sure you want to delete this account?");
            msg.setBounds(30, 30, 300, 30);
            dPanel.add(msg);

            JButton yesBtn = new JButton("Yes");
            JButton noBtn = new JButton("No");

            yesBtn.setFocusPainted(false);
            yesBtn.setBorderPainted(false);
            yesBtn.setBackground(new Color(200, 200, 200));

            noBtn.setFocusPainted(false);
            noBtn.setBorderPainted(false);
            noBtn.setBackground(new Color(200, 200, 200));

            yesBtn.setBounds(70, 90, 80, 30);
            noBtn.setBounds(180, 90, 80, 30);

            dPanel.add(yesBtn);
            dPanel.add(noBtn);

            yesBtn.addActionListener(ev -> {
                // Call delete service
                if (adminServiceClient.deletePlayer(name)) {
                    panel.remove(nameLbl);
                    panel.remove(statusLbl);
                    panel.remove(winsLbl);
                    panel.remove(joinedLbl);
                    panel.remove(editBtn);
                    panel.remove(deleteBtn);
                    panel.repaint();
                    panel.revalidate();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to delete player.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                dialog.dispose();
            });

            noBtn.addActionListener(ev -> dialog.dispose());

            dialog.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}
