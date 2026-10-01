package com.wordy.admin;

import com.wordy.admin.service.AdminServiceClient;
import com.wordy.common.WordyLoginUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

/**
 * Edit/Add Player Account UI
 * Responsible Team Member: JENNY ANNE AWACAN
 * Form interface for editing existing player information or adding new player accounts
 */
public class AdminEditAccountUI extends JFrame {

    private AdminServiceClient adminServiceClient;
    private boolean isEditMode;

    /**
     * Initializes the player edit/add account form dialog.
     * Responsible Team Member: JENNY ANNE AWACAN
     * Provides form fields for updating player information or creating new accounts.
     * 
     * @param username the username of the player to edit, or null to create new
     */
    public AdminEditAccountUI(String username) {
        this.adminServiceClient = new AdminServiceClient();
        this.isEditMode = username != null && !username.isEmpty();
        
        setTitle("Wordy - Admin");
        setSize(1000, 700);
        setMinimumSize(new Dimension(1000, 700));
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        getContentPane().setBackground(new Color(30, 30, 30));


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(20, 20, 940, 620);
        mainPanel.setBackground(new Color(80, 80, 80));
        add(mainPanel);


        JLabel topTitle = new JLabel("Wordy - Admin");
        topTitle.setForeground(Color.WHITE);
        topTitle.setFont(new Font("Arial", Font.BOLD, 24));
        topTitle.setBounds(30, 20, 300, 40);
        mainPanel.add(topTitle);

        // Logout Button
        JButton logoutBtn = new JButton("Logout");
        styleTopButton(logoutBtn);
        logoutBtn.setBounds(820, 35, 80, 25);
        mainPanel.add(logoutBtn);
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // close ALL open windows of the admin
                for (Window window : Window.getWindows()) {
                    window.dispose();
                }

                // open login only
                new WordyLoginUI().setVisible(true);
            }
        });


        int frameWidth = 940;
        int frameHeight = 700;

        int panelWidth = 820;
        int panelHeight = 500;

        int centerX = (frameWidth - panelWidth) / 2;
        int centerY = (frameHeight - panelHeight) / 2;

        JPanel line = new JPanel();
        line.setBackground(Color.WHITE);
        line.setBounds(0, 80, 940, 2);
        mainPanel.add(line);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(centerX, centerY, panelWidth, panelHeight);
        formPanel.setBackground(new Color(210, 210, 210));
        mainPanel.add(formPanel);

        JLabel title = new JLabel(isEditMode ? "Edit Player Account" : "Create New Player Account");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(20, 20, 400, 40);
        formPanel.add(title);

        JLabel subtitle = new JLabel(isEditMode ? "Update Player information" : "Add new player to the system");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitle.setBounds(20, 55, 300, 30);
        formPanel.add(subtitle);

        int leftLabelX = 60;
        int leftFieldX = 60;

        int rightLabelX = 380;
        int rightFieldX = 380;

        int fieldWidth = 260;
        int fieldHeight = 28;

        int y = 110;
        int gap = 60;


        JLabel userLbl = new JLabel("Username :");
        userLbl.setBounds(leftLabelX, y, 200, 20);
        formPanel.add(userLbl);

        y += 20;
        JTextField userField = new JTextField(username != null ? username : "");
        userField.setBounds(leftFieldX, y, fieldWidth, fieldHeight);
        userField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        userField.setEditable(!isEditMode); // Disable editing username in edit mode
        formPanel.add(userField);

        y -= 20;
        JLabel statusLbl = new JLabel("Status :");
        statusLbl.setBounds(rightLabelX, y, 200, 20);
        formPanel.add(statusLbl);

        y += 20;
        JTextField statusField = new JTextField("Active");
        statusField.setBounds(rightFieldX, y, 290, fieldHeight);
        statusField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        formPanel.add(statusField);

        y += gap;
        JLabel passLbl = new JLabel("Password :");
        passLbl.setBounds(leftLabelX, y, 200, 20);
        formPanel.add(passLbl);

        y += 20;
        JPasswordField passField = new JPasswordField();
        passField.setBounds(leftFieldX, y, fieldWidth, fieldHeight);
        passField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        formPanel.add(passField);

        y += gap;
        JLabel memberLbl = new JLabel("Member Since :");
        memberLbl.setBounds(leftLabelX, y, 200, 20);
        formPanel.add(memberLbl);

        y += 20;
        JTextField memberField = new JTextField("2026/01/01");
        memberField.setBounds(leftFieldX, y, fieldWidth, fieldHeight);
        memberField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        memberField.setEditable(false); // Read-only
        formPanel.add(memberField);

        y += gap;
        JLabel remarksLbl = new JLabel("Remarks :");
        remarksLbl.setBounds(leftLabelX, y, 200, 20);
        formPanel.add(remarksLbl);

        y += 20;
        JTextField remarksField = new JTextField();
        remarksField.setBounds(leftFieldX, y, fieldWidth, fieldHeight);
        remarksField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        formPanel.add(remarksField);


        JButton saveBtn = new JButton(isEditMode ? "Update Player" : "Create Player");
        JButton cancelBtn = new JButton("Cancel");
        JButton deleteBtn = new JButton("Delete Account");

        styleBottomButton(saveBtn);
        styleBottomButton(cancelBtn);
        styleBottomButton(deleteBtn);

        int btnY = 420;
        int btnHeight = 35;

        saveBtn.setBounds(350, btnY, 160, btnHeight);
        cancelBtn.setBounds(520, btnY, 100, btnHeight);
        deleteBtn.setBounds(630, btnY, 170, btnHeight);

        formPanel.add(saveBtn);
        formPanel.add(cancelBtn);
        formPanel.add(deleteBtn);
        
        // Hide delete button in create mode
        deleteBtn.setVisible(isEditMode);

        cancelBtn.addActionListener(e -> dispose());

        saveBtn.addActionListener(e -> {
            String playerUsername = userField.getText().trim();
            String playerPassword = new String(passField.getPassword()).trim();
            String playerStatus = statusField.getText().trim();

            if (playerUsername.isEmpty() || playerPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and password cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success;
            if (isEditMode) {
                success = adminServiceClient.updatePlayer(playerUsername, playerPassword, playerStatus);
            } else {
                success = adminServiceClient.createPlayer(playerUsername, playerPassword);
            }

            if (success) {
                JOptionPane.showMessageDialog(this, 
                    (isEditMode ? "Player updated" : "Player created") + " successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to " + (isEditMode ? "update" : "create") + " player.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this account?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String playerToDelete = userField.getText().trim();
                if (adminServiceClient.deletePlayer(playerToDelete)) {
                    JOptionPane.showMessageDialog(this,
                        "Account '" + playerToDelete + "' deleted successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Failed to delete account.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    private void styleTopButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setBackground(new Color(200, 200, 200));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleBottomButton(JButton button) {
        button.setBackground(new Color(100, 100, 100));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15)); // padding
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Arial", Font.PLAIN, 13));

        button.setOpaque(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminEditAccountUI("Name"));
    }
}
