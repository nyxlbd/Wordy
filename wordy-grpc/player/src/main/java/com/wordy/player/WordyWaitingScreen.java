package com.wordy.player;

import javax.swing.*;
import java.awt.*;

import com.wordy.player.model.GameUpdate;
import com.wordy.player.service.GameStateManager;

/**
 * Game Lobby UI - Waiting Screen
 * Responsible Team Member: JAN VON KRISTOFF RASALAN
 * Displays waiting screen when a player joins a game and is waiting for other players
 */
public class WordyWaitingScreen extends JDialog implements GameStateManager.GameUpdateListener {
    private int playerCount = 1; // Start with 1 (self)
    private final int requiredPlayers = 2;
    private JLabel statusLabel;
    private Timer checkTimer;
    private GameStateManager gameManager;
    private boolean gameStarted = false;

    /**
     * Initializes and displays the game lobby waiting screen.
     * Responsible Team Member: JAN VON KRISTOFF RASALAN
     * Shows player count and waits for game to start via server stream.
     * 
     * @param parent the parent frame for dialog modality
     */
    public WordyWaitingScreen(JFrame parent) {
        super(parent, "Wordy", true);
        setSize(400, 150);
        setLocationRelativeTo(parent);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(80, 80, 80));
        statusLabel = new JLabel("Waiting for other players... (" + playerCount + "/" + requiredPlayers + ")");
        statusLabel.setBounds(115,20,200,30);
        statusLabel.setForeground(Color.WHITE);
        add(statusLabel);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(new Color(50,50,50));
        cancelBtn.setBorderPainted(false);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setBounds(150,65,100,30);
        cancelBtn.addActionListener(e -> {
            if (checkTimer != null) {
                checkTimer.stop();
            }
            // Unregister listener
            gameManager.removeGameUpdateListener(this);
            dispose();
        });
        add(cancelBtn);

        add(panel);

        // Get game manager and start streaming updates
        gameManager = GameStateManager.getInstance();
        gameManager.addGameUpdateListener(this);
        gameManager.startGameStream();

        // Fallback timeout (30 seconds) in case stream connection fails
        checkTimer = new Timer(30000, e -> {
            if (!gameStarted && checkTimer != null) {
                checkTimer.stop();
                JOptionPane.showMessageDialog(this, 
                    "Timeout waiting for other players. Game cancelled.", 
                    "Timeout", JOptionPane.WARNING_MESSAGE);
                gameManager.removeGameUpdateListener(this);
                dispose();
                JFrame dashboard = (JFrame) getParent();
                if (dashboard != null) {
                    dashboard.setVisible(true);
                }
            }
        });
        checkTimer.start();
    }

    @Override
    public void onGameUpdate(GameUpdate update) {
        SwingUtilities.invokeLater(() -> {
            if ("START".equalsIgnoreCase(update.getType())) {
                // Game has started
                gameStarted = true;
                if (checkTimer != null) {
                    checkTimer.stop();
                }
                statusLabel.setText("Game Starting! Loading game screen...");
                
                try {
                    Thread.sleep(500); // Brief pause to show message
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                JFrame dashboard = (JFrame) getParent();
                if (dashboard != null) {
                    dashboard.dispose();
                }
                dispose();
                gameManager.removeGameUpdateListener(this);
                // Open game screen
                new WordyGameScreen(1).setVisible(true);
                
            } else if ("WAITING".equalsIgnoreCase(update.getType())) {
                // Still waiting
                statusLabel.setText("Waiting for other players... " + update.getMessage());
            }
        });
    }

    @Override
    public void onGameStreamError(Throwable error) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, 
                "Error connecting to game stream: " + error.getMessage(), 
                "Connection Error", JOptionPane.ERROR_MESSAGE);
            if (checkTimer != null) {
                checkTimer.stop();
            }
            gameManager.removeGameUpdateListener(this);
            dispose();
        });
    }

    public void playerJoined() {
        playerCount++;
    }

}