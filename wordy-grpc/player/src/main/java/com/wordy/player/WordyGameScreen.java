package com.wordy.player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.wordy.common.WordyLoginUI;
import com.wordy.player.model.GameUpdate;
import com.wordy.player.service.GameStateManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Game Screen UI
 * Responsible Team Member: JAN VON KRISTOFF RASALAN
 * Main game screen where players view letters, submit words, and track round progress
 */
public class WordyGameScreen extends JFrame implements GameStateManager.GameUpdateListener {
    private JLabel timerLabel;
    private JTextField wordInputField;
    private JLabel roundLabel; // Moved to class level to update text
    private JLabel roundTitle; // Moved to class level to update text
    private JPanel submissionsPanel;
    private JPanel lettersPanel;
    private List<String> submissions;
    private int timeRemaining = 30;
    private int roundDuration = 30; // Duration from server in seconds
    private int currentRound;
    private Timer gameTimer;
    private GameStateManager gameManager;
    private int playerWins = 0;
    private int opponentWins = 0;
    private List<Integer> playerRoundScores = new ArrayList<>();
    private List<Integer> opponentRoundScores = new ArrayList<>();

    /**
     * Initializes and displays the main game screen.
     * Responsible Team Member: JAN VON KRISTOFF RASALAN
     * Sets up UI components: letter display, timer, word input field, submissions panel.
     * 
     * @param round the starting round number
     */
    public WordyGameScreen(int round) {
        this.currentRound = round;
        setTitle("Wordy - Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 650);
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(null);
        setLocationRelativeTo(null);

        submissions = new ArrayList<>();
        gameManager = GameStateManager.getInstance();
        gameManager.addGameUpdateListener(this);

        // Main Container
        JPanel mainContainer = new JPanel();
        mainContainer.setBackground(new Color(80, 80, 80));
        mainContainer.setBounds(15, 10, 770, 580);
        mainContainer.setLayout(null);
        add(mainContainer);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(100, 100, 100));
        headerPanel.setBounds(0, 0, 770, 60);
        headerPanel.setLayout(null);
        mainContainer.add(headerPanel);

        // Wordy Title
        JLabel wordyTitle = new JLabel("Wordy");
        wordyTitle.setFont(new Font("Arial", Font.PLAIN, 20));
        wordyTitle.setForeground(Color.WHITE);
        wordyTitle.setBounds(20, 15, 150, 30);
        headerPanel.add(wordyTitle);

        // Home Button
        JButton homeBtn = new JButton("Home");
        homeBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        homeBtn.setBounds(580, 18, 80, 25);
        homeBtn.setBackground(new Color(200, 200, 200));
        homeBtn.setForeground(Color.BLACK);
        homeBtn.setFocusPainted(false);
        homeBtn.setBorder(BorderFactory.createEmptyBorder());
        homeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeBtn.addActionListener(e -> {
            stopTimer();
            gameManager.removeGameUpdateListener(this);
            dispose();
            new WordyDashboard().setVisible(true);
        });
        headerPanel.add(homeBtn);

        // Logout Button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        logoutBtn.setBounds(670, 18, 80, 25);
        logoutBtn.setBackground(new Color(200, 200, 200));
        logoutBtn.setForeground(Color.BLACK);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder());
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            stopTimer();
            gameManager.removeGameUpdateListener(this);
            if (gameManager.logout()) {
                dispose();
                new WordyLoginUI().setVisible(true);
            }
        });
        headerPanel.add(logoutBtn);

        // Status Bar
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(new Color(200, 200, 200));
        statusPanel.setBounds(15, 65, 740, 40);
        statusPanel.setLayout(null);
        mainContainer.add(statusPanel);

        // Round Info
        roundLabel = new JLabel("Round " + currentRound + " (You: " + playerWins + " | Opponent: " + opponentWins + ")");
        roundLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        roundLabel.setForeground(Color.BLACK);
        roundLabel.setBounds(20, 10, 300, 20);
        statusPanel.add(roundLabel);

        // Timer
        timerLabel = new JLabel("30s");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setBackground(new Color(80, 80, 80));
        timerLabel.setOpaque(true);
        timerLabel.setBounds(680, 10, 50, 20);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusPanel.add(timerLabel);

        // Game Content Panel
        JPanel gamePanel = new JPanel();
        gamePanel.setBackground(new Color(80, 80, 80));
        gamePanel.setBounds(15, 110, 740, 455);
        gamePanel.setLayout(null);
        mainContainer.add(gamePanel);

        // Round Title
        roundTitle = new JLabel("Round " + currentRound);
        roundTitle.setFont(new Font("Arial", Font.PLAIN, 18));
        roundTitle.setForeground(Color.WHITE);
        roundTitle.setBounds(300, 10, 150, 30);
        gamePanel.add(roundTitle);

        // Available Letters Panel
        lettersPanel = new JPanel();
        lettersPanel.setBackground(new Color(200, 200, 200));
        lettersPanel.setBounds(30, 50, 680, 140);
        lettersPanel.setLayout(null);
        gamePanel.add(lettersPanel);

        // Letters Title
        JLabel lettersTitle = new JLabel("Available Letters (20)");
        lettersTitle.setFont(new Font("Arial", Font.PLAIN, 14));
        lettersTitle.setForeground(new Color(80, 80, 80));
        lettersTitle.setBounds(10, 5, 200, 20);
        lettersPanel.add(lettersTitle);

        // Word Input Field
        wordInputField = new JTextField();
        wordInputField.setFont(new Font("Arial", Font.PLAIN, 14));
        wordInputField.setBounds(30, 210, 680, 35);
        wordInputField.setBackground(new Color(200, 200, 200));
        wordInputField.setForeground(new Color(100, 100, 100));
        wordInputField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        wordInputField.setText("Enter word (min 5 letters)");
        wordInputField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (wordInputField.getText().equals("Enter word (min 5 letters)")) {
                    wordInputField.setText("");
                    wordInputField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (wordInputField.getText().isEmpty()) {
                    wordInputField.setText("Enter word (min 5 letters)");
                    wordInputField.setForeground(new Color(100, 100, 100));
                }
            }
        });
        gamePanel.add(wordInputField);

        // Submit Button
        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        submitBtn.setBounds(320, 260, 140, 35);
        submitBtn.setBackground(new Color(150, 150, 150));
        submitBtn.setForeground(Color.BLACK);
        submitBtn.setFocusPainted(false);
        submitBtn.setBorder(BorderFactory.createEmptyBorder());
        submitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitBtn.addActionListener(e -> submitWord());
        gamePanel.add(submitBtn);

        // Your Submissions Label
        JLabel submissionsLabel = new JLabel("Your Submissions");
        submissionsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        submissionsLabel.setForeground(Color.WHITE);
        submissionsLabel.setBounds(30, 310, 150, 20);
        gamePanel.add(submissionsLabel);

        // Submissions Panel
        submissionsPanel = new JPanel();
        submissionsPanel.setBackground(new Color(200, 200, 200));
        submissionsPanel.setBounds(30, 335, 680, 80);
        submissionsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        gamePanel.add(submissionsPanel);

        // Don't start timer here - wait for first ROUND update from server
    }

    private void displayLetters(String letters) {
        SwingUtilities.invokeLater(() -> {
            lettersPanel.removeAll();
            lettersPanel.setLayout(null); // Set to null for absolute positioning
            
            // Letters Title
            JLabel lettersTitle = new JLabel("Available Letters (" + letters.length() + ")");
            lettersTitle.setFont(new Font("Arial", Font.PLAIN, 14));
            lettersTitle.setForeground(new Color(80, 80, 80));
            lettersTitle.setBounds(10, 5, 200, 20);
            lettersPanel.add(lettersTitle);

            // Letter Buttons Grid
            char[] letterArray = letters.toCharArray();
            int col = 0;
            int row = 0;
            for (int i = 0; i < letterArray.length; i++) {
                JButton letterBtn = new JButton(String.valueOf(letterArray[i]));
                letterBtn.setFont(new Font("Arial", Font.BOLD, 12));
                letterBtn.setBounds(20 + (col * 50), 35 + (row * 50), 45, 40);
                letterBtn.setBackground(new Color(100, 100, 100));
                letterBtn.setForeground(Color.WHITE);
                letterBtn.setFocusPainted(false);
                letterBtn.setBorder(BorderFactory.createEmptyBorder());
                letterBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                letterBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (wordInputField.getText().equals("Enter word (min 5 letters)")) {
                            wordInputField.setText("");
                            wordInputField.setForeground(Color.BLACK);
                        }
                        wordInputField.setText(wordInputField.getText() + letterBtn.getText());
                    }
                });
                lettersPanel.add(letterBtn);

                col++;
                if (col == 12) {
                    col = 0;
                    row++;
                }
            }
            lettersPanel.revalidate();
            lettersPanel.repaint();
        });
    }

    private void submitWord() {
        String word = wordInputField.getText().trim();

        if (word.isEmpty() || word.equals("Enter word (min 5 letters)")) {
            JOptionPane.showMessageDialog(this, "Please enter a word", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (word.length() < 5) {
            JOptionPane.showMessageDialog(this, "Word must be at least 5 letters", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Submit word through backend
        if (gameManager.submitWord(word)) {
            submissions.add(word);
            updateSubmissionsDisplay();
            wordInputField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid word or submission failed", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateSubmissionsDisplay() {
        submissionsPanel.removeAll();
        for (String word : submissions) {
            JLabel wordLabel = new JLabel(word);
            wordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            wordLabel.setBackground(new Color(80, 80, 80));
            wordLabel.setForeground(Color.WHITE);
            wordLabel.setOpaque(true);
            wordLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
            submissionsPanel.add(wordLabel);
        }
        submissionsPanel.revalidate();
        submissionsPanel.repaint();
    }

    private void startTimer() {
        gameTimer = new Timer(1000, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                timerLabel.setText(timeRemaining + "s");

                if (timeRemaining <= 0) {
                    stopTimer();
                }
            }
        });
        gameTimer.start();
    }

    private void stopTimer() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }

    @Override
    public void onGameUpdate(GameUpdate update) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("DEBUG: Received update - Type: " + update.getType() + ", Letters: " + update.getLetters() + ", RoundNum: " + update.getRoundNumber());
            
            switch (update.getType().toUpperCase()) {
                case "START":
                    // Game started - display first round with letters and start timer
                    System.out.println("DEBUG: START case - letters null? " + (update.getLetters() == null) + ", isEmpty? " + (update.getLetters() != null && update.getLetters().isEmpty()));
                    if (update.getRoundNumber() > 0) {
                        currentRound = update.getRoundNumber();
                        roundTitle.setText("Round " + currentRound);
                        roundLabel.setText("Round " + currentRound + " (You: " + playerWins + " | Opponent: " + opponentWins + ")");
                    }
                    // Display letters if provided (even if empty string, still process)
                    String startLetters = update.getLetters();
                    if (startLetters != null && startLetters.length() > 0) {
                        System.out.println("DEBUG: Displaying letters in START: " + startLetters);
                        displayLetters(startLetters);
                        // Use round duration from server
                        roundDuration = update.getRoundDuration() > 0 ? update.getRoundDuration() : 30;
                        timeRemaining = roundDuration;
                        timerLabel.setText(timeRemaining + "s");
                        startTimer();
                    } else {
                        System.out.println("DEBUG: START has no letters, waiting for ROUND update");
                    }
                    break;

                case "ROUND":
                    // New round started - display letters and update round number
                    if (update.getRoundNumber() > 0) {
                        currentRound = update.getRoundNumber();
                        roundTitle.setText("Round " + currentRound);
                        roundLabel.setText("Round " + currentRound + " (You: " + playerWins + " | Opponent: " + opponentWins + ")");
                    }
                    if (update.getLetters() != null && !update.getLetters().isEmpty()) {
                        displayLetters(update.getLetters());
                        // Use round duration from server
                        roundDuration = update.getRoundDuration() > 0 ? update.getRoundDuration() : 30;
                        timeRemaining = roundDuration;
                        timerLabel.setText(timeRemaining + "s");
                        // Stop existing timer and start a new one
                        stopTimer();
                        startTimer();
                    }
                    break;

                case "RESULT":
                    // Round result - show winner
                    stopTimer();
                    if (update.getWinner() != null && !update.getWinner().isEmpty()) {
                        String resultMessage;
                        if (update.getWinner().equals(gameManager.getCurrentUsername())) {
                            playerWins++;
                            playerRoundScores.add(1);
                            opponentRoundScores.add(0);
                            resultMessage = "You won this round!";
                        } else {
                            opponentWins++;
                            playerRoundScores.add(0);
                            opponentRoundScores.add(1);
                            resultMessage = update.getWinner() + " won this round!";
                        }
                        resultMessage += "\n" + update.getMessage();
                        JOptionPane.showMessageDialog(this, resultMessage, "Round Result", JOptionPane.INFORMATION_MESSAGE);
                        roundLabel.setText("Round " + currentRound + " (You: " + playerWins + " | Opponent: " + opponentWins + ")");
                    } else {
                        playerRoundScores.add(0);
                        opponentRoundScores.add(0);
                        JOptionPane.showMessageDialog(this, "No winner this round. " + update.getMessage(), 
                            "Round Result", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;

                case "GAME_OVER":
                    // Game finished
                    stopTimer();
                    gameManager.removeGameUpdateListener(this);
                    String finalResult = playerWins > opponentWins ? "You won the game!" : "You lost the game!";
                    JOptionPane.showMessageDialog(this, finalResult + "\n" + update.getMessage(), 
                        "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    new WordyGameResults(playerWins, opponentWins, playerRoundScores, opponentRoundScores).setVisible(true);
                    dispose();
                    break;
            }
        });
    }

    @Override
    public void onGameStreamError(Throwable error) {
        SwingUtilities.invokeLater(() -> {
            stopTimer();
            gameManager.removeGameUpdateListener(this);
            JOptionPane.showMessageDialog(this, 
                "Game stream error: " + error.getMessage(), 
                "Connection Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            new WordyDashboard().setVisible(true);
        });
    }

    @Override
    public void dispose() {
        stopTimer();
        gameManager.removeGameUpdateListener(this);
        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WordyGameScreen(1).setVisible(true));
    }
}