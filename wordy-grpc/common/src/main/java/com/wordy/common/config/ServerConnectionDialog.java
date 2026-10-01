package com.wordy.common.config;

import javax.swing.*;

/**
 * Dialog for user to configure the server connection address.
 * Shown on app launch before login.
 */
public class ServerConnectionDialog extends JDialog {
    private JTextField hostField;
    private JTextField portField;
    private boolean confirmed = false;

    public ServerConnectionDialog(String currentHost, int currentPort) {
        setTitle("Server Connection Configuration");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Host label and field
        panel.add(new JLabel("Server Host:"));
        hostField = new JTextField(currentHost, 20);
        panel.add(hostField);
        panel.add(Box.createVerticalStrut(10));

        // Port label and field
        panel.add(new JLabel("Server Port:"));
        portField = new JTextField(String.valueOf(currentPort), 20);
        panel.add(portField);
        panel.add(Box.createVerticalStrut(20));

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton connectButton = new JButton("Connect");
        JButton cancelButton = new JButton("Cancel");

        connectButton.addActionListener(e -> {
            if (validateInputs()) {
                confirmed = true;
                dispose();
            }
        });

        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        buttonPanel.add(connectButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel);

        add(panel);
    }

    /**
     * Validates that inputs are valid.
     */
    private boolean validateInputs() {
        String host = hostField.getText().trim();
        
        if (host.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Host cannot be empty", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int port = Integer.parseInt(portField.getText().trim());
            if (port < 1 || port > 65535) {
                JOptionPane.showMessageDialog(this, "Port must be between 1 and 65535", "Invalid Port", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Port must be a valid number", "Invalid Port", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Gets the host entered by user.
     */
    public String getHost() {
        return hostField.getText().trim();
    }

    /**
     * Gets the port entered by user.
     */
    public int getPort() {
        return Integer.parseInt(portField.getText().trim());
    }

    /**
     * Checks if user confirmed the connection.
     */
    public boolean isConfirmed() {
        return confirmed;
    }
}
