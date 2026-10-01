package com.wordy.common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import com.wordy.proto.AuthServiceGrpc;
import com.wordy.proto.LoginRequest;
import com.wordy.proto.LoginResponse;
import com.wordy.proto.LogoutRequest;
import com.wordy.proto.LogoutResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Login Screen UI (Shared Component)
 * Responsible Team Member: JAN VON KRISTOFF RASALAN (UI Design)
 * Shared login interface used by both Player and Admin applications
 * Backend authentication handled by: NICOLE DEOCALES
 */
public class WordyLoginUI extends JFrame {

    private static final int SERVER_PORT = 50051;
    private static String SERVER_HOST = "localhost"; // Default, can be overridden

    private AuthServiceGrpc.AuthServiceBlockingStub authStub;
    private ManagedChannel channel;
    private LoginCallback onLoginSuccess;
    private LoginCallbackWithRole onLoginSuccessWithRole;

    // Callback interface for successful login
    public interface LoginCallback {
        void onSuccess(String username);
    }

    // Callback interface for successful login with role information
    public interface LoginCallbackWithRole {
        void onSuccess(String username, String userRole);
    }

    public WordyLoginUI() {
        this((LoginCallback) null);
    }

    public WordyLoginUI(LoginCallback callback) {
        this.onLoginSuccess = callback;
        this.onLoginSuccessWithRole = null;
        initializeGrpcConnection();
        setupUI();
    }

    public WordyLoginUI(LoginCallbackWithRole callbackWithRole) {
        this.onLoginSuccessWithRole = callbackWithRole;
        this.onLoginSuccess = null;
        initializeGrpcConnection();
        setupUI();
    }

    public static void setServerHost(String host) {
        SERVER_HOST = host;
    }

    public static String getServerHost() {
        return SERVER_HOST;
    }

    private void setupUI() {
        setTitle("Wordy Login"); // Initial Log in Screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(null);
        setLocationRelativeTo(null);

        // Add window listener to cleanup resources when window closes
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cleanup();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                cleanup();
            }
        });

        //Main Container
        JPanel card = new JPanel();
        card.setBackground(new Color(80, 80, 80));
        card.setBounds(90,30,600,400);
        card.setLayout(null);
        add(card);

        JLabel titleLabel = new JLabel("WORDY"); // Container Title
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(180, 40, 300, 100);
        card.add(titleLabel);

        JLabel username = new JLabel("Username");
        username.setFont(new Font("Arial", Font.PLAIN, 16));
        username.setForeground(Color.WHITE);
        username.setBounds(50, 150, 150, 30);
        card.add(username);

        JTextField usernameTextField = new JTextField();
        usernameTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameTextField.setForeground(Color.BLACK);
        usernameTextField.setBounds(50, 180, 500, 30);
        card.add(usernameTextField);

        JLabel password = new JLabel("Password");
        password.setFont(new Font("Arial", Font.PLAIN, 16));
        password.setForeground(Color.WHITE);
        password.setBounds(50, 220, 150, 30);
        card.add(password);

        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordTextField.setForeground(Color.BLACK);
        passwordTextField.setBounds(50, 250, 500, 30);
        card.add(passwordTextField);

        JButton SignInButton = new JButton("Sign In");
        SignInButton.setFont(new Font("Arial", Font.PLAIN, 18));
        SignInButton.setForeground(Color.WHITE);
        SignInButton.setBackground(new Color(50,50,50));
        SignInButton.setBorderPainted(false);
        SignInButton.setFocusPainted(false);
        SignInButton.setBounds(250, 300, 100, 40);
        SignInButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SignInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin(usernameTextField.getText(), new String(passwordTextField.getPassword()));
            }
        });
        card.add(SignInButton);

        // Settings Button
        JButton settingsBtn = new JButton("⚙");
        settingsBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        settingsBtn.setForeground(Color.WHITE);
        settingsBtn.setBackground(new Color(100, 100, 100));
        settingsBtn.setBorderPainted(false);
        settingsBtn.setFocusPainted(false);
        settingsBtn.setBounds(500, 300, 40, 40);
        settingsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        settingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showConnectionSettings();
            }
        });
        card.add(settingsBtn);
    }

    private void initializeGrpcConnection() {
        try {
            // Close old channel if exists
            if (channel != null && !channel.isShutdown()) {
                channel.shutdownNow();
            }

            channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                    .usePlaintext()
                    .build();
            authStub = AuthServiceGrpc.newBlockingStub(channel);
            System.out.println("Connected to Wordy Server at " + SERVER_HOST + ":" + SERVER_PORT);
        } catch (Exception e) {
            System.err.println("Failed to initialize gRPC connection: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Failed to connect to server. Make sure the server is running.",
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            LoginRequest request = LoginRequest.newBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .setIsAdmin(false)
                    .build();

            LoginResponse response = authStub.login(request);

            if (response.getSuccess()) {
                JOptionPane.showMessageDialog(this, "Login successful!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                
                String userRole = response.getUserRole();
                
                // Call the appropriate callback
                if (onLoginSuccessWithRole != null) {
                    onLoginSuccessWithRole.onSuccess(username, userRole);
                } else if (onLoginSuccess != null) {
                    onLoginSuccess.onSuccess(username);
                }
                
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, response.getMessage(),
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error connecting to server. Please try again.",
                    "Server Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showConnectionSettings() {
        JDialog settingsDialog = new JDialog(this, "Server Settings", true);
        settingsDialog.setSize(400, 200);
        settingsDialog.setLocationRelativeTo(this);
        settingsDialog.setLayout(null);

        // Host label and field
        JLabel hostLabel = new JLabel("Server Host:");
        hostLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        hostLabel.setBounds(20, 20, 100, 30);
        settingsDialog.add(hostLabel);

        JTextField hostField = new JTextField(SERVER_HOST);
        hostField.setFont(new Font("Arial", Font.PLAIN, 12));
        hostField.setBounds(130, 20, 250, 30);
        settingsDialog.add(hostField);

        // Port label and field
        JLabel portLabel = new JLabel("Server Port:");
        portLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        portLabel.setBounds(20, 60, 100, 30);
        settingsDialog.add(portLabel);

        JTextField portField = new JTextField(String.valueOf(SERVER_PORT));
        portField.setFont(new Font("Arial", Font.PLAIN, 12));
        portField.setBounds(130, 60, 250, 30);
        portField.setEditable(false); // Port is fixed
        settingsDialog.add(portField);

        // Save button
        JButton saveBtn = new JButton("Save");
        saveBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        saveBtn.setBounds(150, 130, 100, 30);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newHost = hostField.getText().trim();
                if (!newHost.isEmpty()) {
                    SERVER_HOST = newHost;
                    JOptionPane.showMessageDialog(settingsDialog, 
                        "Server address updated to: " + newHost + ":" + SERVER_PORT,
                        "Settings Updated", JOptionPane.INFORMATION_MESSAGE);
                    settingsDialog.dispose();
                    // Reinitialize connection with new host
                    initializeGrpcConnection();
                } else {
                    JOptionPane.showMessageDialog(settingsDialog, "Please enter a valid host",
                        "Invalid Input", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        settingsDialog.add(saveBtn);

        settingsDialog.setVisible(true);
    }

    public void cleanup() {
        if (channel != null && !channel.isShutdown()) {
            try {
                channel.shutdownNow();
                // Wait for graceful shutdown
                if (!channel.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)) {
                    System.out.println("Channel did not terminate gracefully");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void logoutUser(String username) {
        try {
            ManagedChannel tempChannel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                    .usePlaintext()
                    .build();
            AuthServiceGrpc.AuthServiceBlockingStub tempStub = AuthServiceGrpc.newBlockingStub(tempChannel);

            LogoutRequest request = LogoutRequest.newBuilder()
                    .setUsername(username)
                    .build();

            LogoutResponse response = tempStub.logout(request);

            if (response.getSuccess()) {
                System.out.println("User " + username + " logged out successfully");
            } else {
                System.err.println("Logout failed: " + response.getMessage());
            }

            // Cleanup temporary channel
            if (!tempChannel.isShutdown()) {
                tempChannel.shutdownNow();
            }

        } catch (Exception e) {
            System.err.println("Error during logout: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WordyLoginUI().setVisible(true));
    }
}
