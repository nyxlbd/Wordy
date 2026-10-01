package com.wordy.player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.wordy.common.WordyLoginUI;
import com.wordy.player.service.GameStateManager;

/**
 * Home Screen / Dashboard UI
 * Responsible Team Member: JAN VON KRISTOFF RASALAN
 * Main dashboard where players can view game rules, join games, or access leaderboard
 */
public class WordyDashboard extends JFrame {

    public static final String GAME_RULES =
                    "GETTING STARTED:\n" +
                    "At least 2 players must join within 10 seconds to start a game\n" +
                    "First player to win 3 rounds wins the game\n\n" +

                    "EACH ROUND:\n" +
                    "Server generates and sends 20 random letters to all players\n" +
                    "Letters will contain 5-7 vowels (letters may repeat)\n" +
                    "You have 30 seconds per round to submit your word\n\n" +

                    "SUBMITTING WORDS:\n" +
                    "Words must be at least 5 letters long\n" +
                    "Words must be valid English words\n" +
                    "Words can only use letters from the given list\n" +
                    "You can submit multiple words within the time limit\n" +
                    "Invalid words will be rejected by the server\n\n" +

                    "WINNING A ROUND:\n" +
                    "The player with the longest valid word wins the round\n" +
                    "If multiple players have the same longest word length,\n" +
                    "no winner is declared for that round\n" +
                    "If no valid words are submitted, no winner is declared\n\n" +

                    "GAME COMPLETION:\n" +
                    "First player to win 3 rounds is the overall winner\n" +
                    "Game statistics are recorded on the leaderboard";

    private String currentUsername;

    /**
     * Creates a new dashboard with no username.
     * Responsible Team Member: JAN VON KRISTOFF RASALAN
     */
    public WordyDashboard() {
        this(null);
    }

    /**
     * Creates and displays the home screen dashboard for a logged-in player.
     * Responsible Team Member: JAN VON KRISTOFF RASALAN
     * Sets up UI components, buttons, and game rules display.
     * 
     * @param username the logged-in player's username (null if not authenticated)
     */
    public WordyDashboard(String username) {
        this.currentUsername = username;
        
        // Update the GameStateManager singleton with the logged-in user
        if (username != null) {
            GameStateManager manager = GameStateManager.getInstance();
            manager.setCurrentUser(username, "PLAYER");
        }
        
        setTitle("Wordy - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 900);
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(null);
        setLocationRelativeTo(null);

        // Main Container Panel
        JPanel mainContainer = new JPanel();
        mainContainer.setBackground(new Color(80, 80, 80));
        mainContainer.setBounds(20, 20, 1140, 820);
        mainContainer.setLayout(null);
        add(mainContainer);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(80, 80, 80));
        headerPanel.setBounds(0, 0, 1140, 100);
        headerPanel.setLayout(null);
        mainContainer.add(headerPanel);

        // Wordy Title
        JLabel wordyTitle = new JLabel("Wordy");
        wordyTitle.setFont(new Font("Arial", Font.BOLD, 28));
        wordyTitle.setForeground(Color.WHITE);
        wordyTitle.setBounds(40, 30, 200, 40);
        headerPanel.add(wordyTitle);

        // Logout Button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        logoutBtn.setBounds(1020, 35, 80, 25);
        logoutBtn.setBackground(new Color(200, 200, 200));
        logoutBtn.setForeground(Color.BLACK);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder());
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call server logout using backend service
                GameStateManager manager = GameStateManager.getInstance();
                if (manager.isLoggedIn()) {
                    boolean logoutSuccess = manager.logout();
                    if (logoutSuccess) {
                        JOptionPane.showMessageDialog(null, 
                            "Successfully logged out", 
                            "Logout", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, 
                            "Error during logout", 
                            "Logout Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                dispose();
                WordyLoginUI login = new WordyLoginUI((username, userRole) -> {
                    if ("PLAYER".equalsIgnoreCase(userRole)) {
                        new WordyDashboard(username).setVisible(true);
                    } else if ("ADMIN".equalsIgnoreCase(userRole)) {
                        JOptionPane.showMessageDialog(null, 
                            "You logged in as an admin. Please use AdminApp to access the admin dashboard.",
                            "Admin Access", JOptionPane.INFORMATION_MESSAGE);
                        new WordyLoginUI((u, r) -> {}).setVisible(true);
                    }
                });
                login.setVisible(true);
            }
        });
        headerPanel.add(logoutBtn);

        // Divider Line
        JPanel divider = new JPanel();
        divider.setBackground(Color.WHITE);
        divider.setBounds(0, 100, 1200, 2);
        mainContainer.add(divider);

        // Content Area Panel
        JPanel contentArea = new JPanel();
        contentArea.setBackground(new Color(80, 80, 80));
        contentArea.setBounds(0, 102, 940, 718);
        contentArea.setLayout(null);
        mainContainer.add(contentArea);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome Player!");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(450, 20, 300, 50);
        contentArea.add(welcomeLabel);

        // Start Game Button
        JPanel startGamePanel = new JPanel();
        startGamePanel.setBackground(new Color(200, 200, 200));
        startGamePanel.setBounds(250, 90, 750, 100);
        startGamePanel.setLayout(null);
        startGamePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startGamePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Join game through backend service
                GameStateManager manager = GameStateManager.getInstance();
                if (manager.isLoggedIn()) {
                    if (manager.joinGame()) {
                        JOptionPane.showMessageDialog(WordyDashboard.this, 
                            "Successfully joined game! Waiting for other players...", 
                            "Game Joined", JOptionPane.INFORMATION_MESSAGE);
                        // Open waiting screen
                        new WordyWaitingScreen(WordyDashboard.this).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(WordyDashboard.this, 
                            "Failed to join game. Try again.", 
                            "Join Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(WordyDashboard.this, 
                        "You must be logged in to play.", 
                        "Not Logged In", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        contentArea.add(startGamePanel);

        JLabel startGameTitle = new JLabel("Start Game!");
        startGameTitle.setFont(new Font("Arial", Font.BOLD, 20));
        startGameTitle.setForeground(new Color(80, 80, 80));
        startGameTitle.setBounds(20, 10, 300, 30);
        startGamePanel.add(startGameTitle);


        JLabel startGameDesc = new JLabel("Start a game, and compete with other players");
        startGameDesc.setFont(new Font("Arial", Font.PLAIN, 14));
        startGameDesc.setForeground(new Color(100, 100, 100));
        startGameDesc.setBounds(20, 45, 500, 25);
        startGamePanel.add(startGameDesc);

        // LeaderBoard Button
        JPanel leaderboardPanel = new JPanel();
        leaderboardPanel.setBackground(new Color(200, 200, 200));
        leaderboardPanel.setBounds(250, 210, 750, 100);
        leaderboardPanel.setLayout(null);
        leaderboardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        leaderboardPanel.addMouseListener(new java.awt.event.MouseAdapter() { // Temporary Function LeaderBoard Button
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dispose();
                new WordyLeaderBoard(currentUsername).setVisible(true);
            }
        });
        contentArea.add(leaderboardPanel);

        JLabel leaderboardTitle = new JLabel("LeaderBoard");
        leaderboardTitle.setFont(new Font("Arial", Font.BOLD, 20));
        leaderboardTitle.setForeground(new Color(80, 80, 80));
        leaderboardTitle.setBounds(20, 10, 300, 30);
        leaderboardPanel.add(leaderboardTitle);

        JLabel leaderboardDesc = new JLabel("View Rankings, and best words in the games");
        leaderboardDesc.setFont(new Font("Arial", Font.PLAIN, 14));
        leaderboardDesc.setForeground(new Color(100, 100, 100));
        leaderboardDesc.setBounds(20, 45, 500, 25);
        leaderboardPanel.add(leaderboardDesc);

        // Rules Panel
        JPanel rulesPanel = new JPanel();
        rulesPanel.setBackground(new Color(200, 200, 200));
        rulesPanel.setBounds(250, 330, 750, 350);
        rulesPanel.setLayout(new BorderLayout());
        rulesPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contentArea.add(rulesPanel);

        JLabel rulesTitle = new JLabel("Rules of the Game");
        rulesTitle.setFont(new Font("Arial", Font.BOLD, 20));
        rulesTitle.setForeground(new Color(80, 80, 80));
        rulesPanel.add(rulesTitle, BorderLayout.NORTH);


        JTextArea rulesDesc = new JTextArea(GAME_RULES);
        rulesDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        rulesDesc.setForeground(new Color(100, 100, 100));
        rulesDesc.setBackground(new Color(200, 200, 200));
        rulesDesc.setLineWrap(true);
        rulesDesc.setWrapStyleWord(true);
        rulesDesc.setEditable(false);
        rulesDesc.setMargin(new Insets(5, 5, 5, 5));
        rulesDesc.setCaretPosition(0);


        JScrollPane scrollPane = new JScrollPane(rulesDesc);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(200, 200, 200));
        rulesPanel.add(scrollPane, BorderLayout.CENTER);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WordyDashboard().setVisible(true));
    }
}