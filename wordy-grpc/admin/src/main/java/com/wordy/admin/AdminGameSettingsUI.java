package com.wordy.admin;

import com.wordy.admin.service.AdminServiceClient;
import com.wordy.admin.service.AdminServiceClient.GameSettings;
import com.wordy.common.WordyLoginUI;

import javax.swing.*;
import java.awt.*;

/**
 * Game Settings Configuration UI
 * Responsible Team Member: KATHRINA SHAYNE RAGOS
 * Interface for configuring game parameters such as waiting time and round duration
 */
public class AdminGameSettingsUI extends JFrame {

    private JTextField waitingField;
    private JTextField roundField;
    private AdminServiceClient adminServiceClient;

    /**
     * Initializes and displays the game settings configuration dialog.
     * Responsible Team Member: KATHRINA SHAYNE RAGOS
     * Allows admin to configure waiting time before game start and round duration.
     */
    public AdminGameSettingsUI() {
        this.adminServiceClient = new AdminServiceClient();

        setTitle("Wordy - Admin");
        setSize(1000, 700);
        setMinimumSize(new Dimension(1000, 700));
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(30, 30, 30));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(20, 20, 940, 620);
        mainPanel.setBackground(new Color(80, 80, 80));
        add(mainPanel);

        // ================= HEADER =================
        JLabel title = new JLabel("Wordy - Admin");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(30, 20, 300, 40);
        mainPanel.add(title);

        JButton logoutBtn = new JButton("Logout");
        styleTopButton(logoutBtn);
        logoutBtn.setBounds(820, 35, 80, 25);
        mainPanel.add(logoutBtn);

        logoutBtn.addActionListener(e -> {
            for (Window window : Window.getWindows()) {
                window.dispose();
            }
            new WordyLoginUI().setVisible(true);
        });

        JPanel line = new JPanel();
        line.setBackground(Color.WHITE);
        line.setBounds(0, 80, 940, 2);
        mainPanel.add(line);

        // ================= SETTINGS PANEL =================
        JPanel configPanel = new JPanel();
        configPanel.setLayout(null);
        configPanel.setBounds(260, 180, 420, 260);
        configPanel.setBackground(new Color(210, 210, 210));
        mainPanel.add(configPanel);

        JLabel panelTitle = new JLabel("Game Settings");
        panelTitle.setFont(new Font("Arial", Font.BOLD, 22));
        panelTitle.setBounds(30, 20, 300, 30);
        configPanel.add(panelTitle);

        JLabel waitingLbl = new JLabel("Waiting Time (seconds):");
        waitingLbl.setBounds(40, 80, 200, 25);
        configPanel.add(waitingLbl);

        waitingField = new JTextField();
        waitingField.setBounds(240, 80, 130, 25);
        configPanel.add(waitingField);

        JLabel roundLbl = new JLabel("Round Duration (seconds):");
        roundLbl.setBounds(40, 130, 200, 25);
        configPanel.add(roundLbl);

        roundField = new JTextField();
        roundField.setBounds(240, 130, 130, 25);
        configPanel.add(roundField);

        JButton saveBtn = new JButton("Save Changes");
        styleBottomButton(saveBtn);
        saveBtn.setBounds(140, 190, 140, 35);
        configPanel.add(saveBtn);

        JButton cancelBtn = new JButton("Cancel");
        styleBottomButton(cancelBtn);
        cancelBtn.setBounds(290, 190, 90, 35);
        configPanel.add(cancelBtn);

        cancelBtn.addActionListener(e -> dispose());

        saveBtn.addActionListener(e -> saveSettings());

        // Load settings from server
        loadGameSettings();
    }

    // ================= LOAD SETTINGS LOGIC =================
    /**
     * Loads game settings from the server
     * Responsible Team Member: KATHRINA SHAYNE RAGOS
     */
    private void loadGameSettings() {
        SwingUtilities.invokeLater(() -> {
            try {
                GameSettings settings = adminServiceClient.getGameSettings();
                waitingField.setText(String.valueOf(settings.waitingTime));
                roundField.setText(String.valueOf(settings.roundDuration));
            } catch (Exception e) {
                System.err.println("Error loading settings: " + e.getMessage());
                // Fallback to defaults
                waitingField.setText("10");
                roundField.setText("30");
            }
        });
    }

    // ================= SAVE LOGIC =================
    /**
     * Saves game settings to the server
     * Responsible Team Member: KATHRINA SHAYNE RAGOS
     */
    private void saveSettings() {

        String waiting = waitingField.getText().trim();
        String round = roundField.getText().trim();

        // VALIDATION
        if (!waiting.matches("\\d+") || !round.matches("\\d+")) {
            showMessage("Enter valid numbers only.");
            return;
        }

        int waitingTime = Integer.parseInt(waiting);
        int roundDuration = Integer.parseInt(round);

        if (waitingTime <= 0 || roundDuration <= 0) {
            showMessage("Values must be greater than 0.");
            return;
        }

        // Call service to update settings
        if (adminServiceClient.updateGameSettings(waitingTime, roundDuration)) {
            showMessage("Settings saved successfully!");
        } else {
            showMessage("Failed to save settings.");
        }
    }

    // ================= BUTTON STYLES =================
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
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Arial", Font.PLAIN, 13));
        button.setOpaque(true);
    }

    // ================= CUSTOM DIALOG =================
    private void showMessage(String message) {
        JDialog dialog = new JDialog(this, "Message", true);
        dialog.setSize(350, 180);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240));
        panel.setBounds(0, 0, 350, 180);
        dialog.add(panel);

        JLabel msg = new JLabel(message);
        msg.setBounds(0, 40, 350, 30);
        msg.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(msg);

        JButton okBtn = new JButton("OK");
        okBtn.setBounds(130, 90, 80, 30);
        okBtn.setBackground(new Color(200, 200, 200));
        okBtn.setFocusPainted(false);
        okBtn.setBorderPainted(false);
        okBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(okBtn);

        okBtn.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    // ================= MAIN METHOD =================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminGameSettingsUI().setVisible(true));
    }
}