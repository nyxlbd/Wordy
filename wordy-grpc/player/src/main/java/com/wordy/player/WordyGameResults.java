package com.wordy.player;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Game Results / Game End UI
 * Responsible Team Members:
 *   - Game End UI: JAN VON KRISTOFF RASALAN (overall screen layout)
 *   - Round Result UI: ADELYN JOY TELA (round score table display)
 * Displays final game results, round-by-round breakdown, and allows replaying or returning to dashboard
 */
public class WordyGameResults extends JFrame {
    /**
     * Displays the final game results and round-by-round score breakdown.
     * Responsible Team Members:
     *   - JAN VON KRISTOFF RASALAN (overall layout and result display)
     *   - ADELYN JOY TELA (round score table)
     * 
     * @param yourScore final score for the player
     * @param opponentScore final score for the opponent
     * @param playerRoundScores list of scores for each round for the player
     * @param opponentRoundScores list of scores for each round for the opponent
     */
    public WordyGameResults(int yourScore, int opponentScore, List<Integer> playerRoundScores, List<Integer> opponentRoundScores) {
        setTitle("Game Results");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Dynamically size based on number of rounds
        int numRounds = playerRoundScores.size();
        int tableHeight = Math.min(250, numRounds * 20 + 30);
        int windowHeight = 180 + tableHeight + 70;  // Title(80) + Score(60) + Table + Buttons(70)
        
        setSize(400, windowHeight);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(null);

        // Result
        String result = yourScore > opponentScore ? "YOU WIN!" : yourScore < opponentScore ? "YOU LOST" : "TIE";
        JLabel resultLabel = new JLabel(result);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 32));
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setBounds(0, 20, 400, 60);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(resultLabel);

        // Scores
        JLabel scoreLabel = new JLabel("Your Score: " + yourScore + "  |  Opponent: " + opponentScore);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(0, 80, 400, 60);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(scoreLabel);

        // ===== ROUND SCORE TABLE (DISPLAYS ALL ROUNDS) =====
        String[] columns = {"Round", "You", "Opponent"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (int i = 0; i < numRounds; i++) {
            model.addRow(new Object[]{
                    "Round " + (i + 1),
                    playerRoundScores.get(i),
                    opponentRoundScores.get(i)
            });
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 140, 300, tableHeight);
        add(scrollPane);

        // Dashboard Button
        JButton dashboardBtn = new JButton("Dashboard");
        dashboardBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        dashboardBtn.setBounds(40, windowHeight - 55, 150, 40);
        dashboardBtn.setBackground(new Color(50,50,50));
        dashboardBtn.setForeground(Color.WHITE);
        dashboardBtn.setBorderPainted(false);
        dashboardBtn.setFocusPainted(false);
        dashboardBtn.addActionListener(e -> {
            new WordyDashboard().setVisible(true);
            dispose();
        });
        add(dashboardBtn);

        // Play Again Button
        JButton playAgainBtn = new JButton("Play Again");
        playAgainBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        playAgainBtn.setBounds(200, windowHeight - 55, 150, 40);
        playAgainBtn.setBackground(new Color(50, 50, 50));
        playAgainBtn.setForeground(Color.WHITE);
        playAgainBtn.setBorderPainted(false);
        playAgainBtn.setFocusPainted(false);
        playAgainBtn.addActionListener(e -> {
            new WordyWaitingScreen(WordyGameResults.this).setVisible(true);
            dispose();
        });
        add(playAgainBtn);
    }

    public static void main(String[] args) {
        java.util.List<Integer> playerScores = java.util.Arrays.asList(1, 0, 1);
        java.util.List<Integer> opponentScores = java.util.Arrays.asList(0, 1, 0);
        new WordyGameResults(2, 1, playerScores, opponentScores).setVisible(true); // example with all rounds
    }
}