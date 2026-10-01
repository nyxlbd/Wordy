package com.wordy.player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.wordy.common.WordyLoginUI;
import com.wordy.player.model.LeaderboardData;
import com.wordy.player.model.PlayerStats;
import com.wordy.player.model.WordRecord;
import com.wordy.player.service.GameStateManager;
import java.util.List;

/**
 * Leaderboard / Top Players UI
 * Responsible Team Member: JAN VON KRISTOFF RASALAN
 * Displays top players by wins and longest words submitted in games
 */
public class WordyLeaderBoard extends JFrame {
    private String currentUsername;
    private JPanel topWinsPanel;
    private JPanel longestWordsPanel;
    private LeaderboardData leaderboardData;

    /**
     * Creates a new leaderboard with no username.
     * Responsible Team Member: JAN VON KRISTOFF RASALAN
     */
    public WordyLeaderBoard() {
        this(null);
    }

    /**
     * Initializes and displays the leaderboard showing top players and longest words.
     * Responsible Team Member: JAN VON KRISTOFF RASALAN
     * Fetches leaderboard data from server and displays in two panels.
     * 
     * @param username the current player's username (for context, may be null)
     */
    public WordyLeaderBoard(String username) {
        this.currentUsername = username;
        setTitle("Wordy - Leaderboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(null);
        setLocationRelativeTo(null);

        JPanel mainContainer = new JPanel();
        mainContainer.setBackground(new Color(80, 80, 80));
        mainContainer.setBounds(20, 20, 940, 620);
        mainContainer.setLayout(null);
        add(mainContainer);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(80, 80, 80));
        headerPanel.setBounds(0, 0, 940, 100);
        headerPanel.setLayout(null);
        mainContainer.add(headerPanel);

        JLabel wordyTitle = new JLabel("Wordy");
        wordyTitle.setFont(new Font("Arial", Font.BOLD, 28));
        wordyTitle.setForeground(Color.WHITE);
        wordyTitle.setBounds(40, 30, 200, 40);
        headerPanel.add(wordyTitle);

        JPanel divider = new JPanel();
        divider.setBackground(new Color(80, 80, 80));
        divider.setLayout(null);
        divider.setBounds(0, 100, 940, 80);

        JLabel Rankings = new JLabel("Rankings");
        Rankings.setFont(new Font("Arial", Font.PLAIN, 30));
        Rankings.setForeground(Color.WHITE);
        Rankings.setHorizontalAlignment(SwingConstants.CENTER);
        Rankings.setBounds(0, 20, 940, 40);

        divider.add(Rankings);
        mainContainer.add(divider);

        // Home Button
        JButton homeBtn = new JButton("Home");
        homeBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        homeBtn.setBounds(700, 40, 80, 25);
        homeBtn.setBackground(new Color(200, 200, 200));
        homeBtn.setForeground(Color.BLACK);
        homeBtn.setFocusPainted(false);
        homeBtn.setBorder(BorderFactory.createEmptyBorder());
        homeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeBtn.addActionListener(e -> {
            dispose();
            if (currentUsername != null) {
                new WordyDashboard(currentUsername).setVisible(true);
            } else {
                new WordyDashboard().setVisible(true);
            }
        });
        headerPanel.add(homeBtn);

        // Logout Button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutBtn.setBounds(800, 40, 80, 25);
        logoutBtn.setBackground(new Color(200, 200, 200));
        logoutBtn.setForeground(Color.BLACK);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder());
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameStateManager manager = GameStateManager.getInstance();
                if (manager.isLoggedIn()) {
                    manager.logout();
                }
                dispose();
                new WordyLoginUI().setVisible(true);
            }
        });
        headerPanel.add(logoutBtn);

        JButton LongestWordButton = new JButton("Longest Words");
        JButton TopWinsButton = new JButton("Top Wins");

        topWinsPanel = new JPanel();
        topWinsPanel.setBackground(new Color(220, 220, 220));
        topWinsPanel.setLayout(null);
        topWinsPanel.setBounds(15, 230, 910, 350);

        longestWordsPanel = new JPanel();
        longestWordsPanel.setBackground(new Color(220, 220, 220));
        longestWordsPanel.setLayout(null);
        longestWordsPanel.setBounds(15, 235, 910, 350);
        longestWordsPanel.setVisible(false);

        mainContainer.add(longestWordsPanel);
        mainContainer.add(topWinsPanel);

        TopWinsButton.setFont(new Font("Arial", Font.PLAIN, 14));
        TopWinsButton.setBounds(50, 200, 80, 25);
        TopWinsButton.setBackground(new Color(30, 30, 30));
        TopWinsButton.setForeground(new Color(200, 200, 200));
        TopWinsButton.setFocusPainted(false);
        TopWinsButton.setBorder(BorderFactory.createEmptyBorder());
        TopWinsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        TopWinsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                topWinsPanel.setVisible(true);
                longestWordsPanel.setVisible(false);
                TopWinsButton.setBackground(new Color(30, 30, 30));
                TopWinsButton.setForeground(new Color(200, 200, 200));
                LongestWordButton.setBackground(new Color(200, 200, 200));
                LongestWordButton.setForeground(new Color(30, 30, 30));
            }
        });
        mainContainer.add(TopWinsButton);

        LongestWordButton.setFont(new Font("Arial", Font.PLAIN, 14));
        LongestWordButton.setBounds(150, 200, 100, 25);
        LongestWordButton.setBackground(new Color(200, 200, 200));
        LongestWordButton.setForeground(new Color(30, 30, 30));
        LongestWordButton.setFocusPainted(false);
        LongestWordButton.setBorder(BorderFactory.createEmptyBorder());
        LongestWordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LongestWordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                longestWordsPanel.setVisible(true);
                topWinsPanel.setVisible(false);
                LongestWordButton.setBackground(new Color(30, 30, 30));
                LongestWordButton.setForeground(new Color(200, 200, 200));
                TopWinsButton.setBackground(new Color(200, 200, 200));
                TopWinsButton.setForeground(new Color(30, 30, 30));
            }
        });
        mainContainer.add(LongestWordButton);

        // Fetch leaderboard data from backend
        fetchAndDisplayLeaderboard();
    }

    private void fetchAndDisplayLeaderboard() {
        SwingUtilities.invokeLater(() -> {
            GameStateManager manager = GameStateManager.getInstance();
            leaderboardData = manager.getLeaderboard();

            // Clear existing panels
            topWinsPanel.removeAll();
            longestWordsPanel.removeAll();

            // Populate Top Wins Rankings
            List<PlayerStats> topPlayers = leaderboardData.getTopPlayers();
            for (int i = 0; i < topPlayers.size() && i < 6; i++) {
                PlayerStats player = topPlayers.get(i);
                int y = 10 + i * 50;

                JLabel numLabel = new JLabel(String.valueOf(i + 1));
                numLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                numLabel.setForeground(new Color(80, 80, 80));
                numLabel.setBounds(20, y, 30, 40);
                topWinsPanel.add(numLabel);

                JLabel nameLabel = new JLabel(player.getUsername());
                nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                nameLabel.setForeground(new Color(80, 80, 80));
                nameLabel.setBounds(350, y, 200, 40);
                topWinsPanel.add(nameLabel);

                JLabel scoreLabel = new JLabel(player.getWins() + " Wins");
                scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                scoreLabel.setForeground(new Color(80, 80, 80));
                scoreLabel.setBounds(750, y, 120, 40);
                topWinsPanel.add(scoreLabel);

                // Row divider line
                if (i < topPlayers.size() - 1 && i < 5) {
                    JSeparator sep = new JSeparator();
                    sep.setBounds(10, y + 44, 890, 1);
                    sep.setForeground(new Color(180, 180, 180));
                    topWinsPanel.add(sep);
                }
            }

            // Populate Longest Words
            List<WordRecord> longestWords = leaderboardData.getLongestWords();
            for (int i = 0; i < longestWords.size() && i < 6; i++) {
                WordRecord wordRecord = longestWords.get(i);
                int y = 10 + i * 50;

                JLabel num = new JLabel(String.valueOf(i + 1));
                num.setFont(new Font("Arial", Font.PLAIN, 16));
                num.setForeground(new Color(80, 80, 80));
                num.setBounds(20, y, 30, 40);
                longestWordsPanel.add(num);

                JLabel word = new JLabel(wordRecord.getWord());
                word.setFont(new Font("Arial", Font.PLAIN, 16));
                word.setForeground(new Color(80, 80, 80));
                word.setBounds(350, y, 200, 40);
                longestWordsPanel.add(word);

                JLabel letters = new JLabel(wordRecord.getLength() + " Letters");
                letters.setFont(new Font("Arial", Font.PLAIN, 16));
                letters.setForeground(new Color(80, 80, 80));
                letters.setBounds(750, y, 120, 40);
                longestWordsPanel.add(letters);

                if (i < longestWords.size() - 1 && i < 5) {
                    JSeparator sep = new JSeparator();
                    sep.setBounds(10, y + 44, 890, 1);
                    sep.setForeground(new Color(180, 180, 180));
                    longestWordsPanel.add(sep);
                }
            }

            topWinsPanel.revalidate();
            topWinsPanel.repaint();
            longestWordsPanel.revalidate();
            longestWordsPanel.repaint();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WordyLeaderBoard().setVisible(true));
    }
}

