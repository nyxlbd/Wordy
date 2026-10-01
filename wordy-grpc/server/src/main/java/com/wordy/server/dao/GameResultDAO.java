package com.wordy.server.dao;

import com.wordy.server.db.DBConnection;

import java.sql.*;

/**
 * Team Member: Adelyn Joy Tela
 * Data Access Object for game results.
 * Manages recording of completed games.
 */
public class GameResultDAO {

    /**
     * Records a game result to the database.
     *
     * @param winnerUsername the username of the winner
     * @param totalRounds the number of rounds played
     * @return true if recorded successfully
     */
    public boolean recordGameResult(String winnerUsername, int totalRounds) {
        // First, get the winner's user_id
        String userLookupSql = "SELECT id FROM users WHERE username = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement userStmt = conn.prepareStatement(userLookupSql)) {

            userStmt.setString(1, winnerUsername);
            ResultSet rs = userStmt.executeQuery();

            if (!rs.next()) {
                System.err.println("User not found: " + winnerUsername);
                return false;
            }

            int winnerId = rs.getInt("id");

            // Now insert the game result
            String sql = "INSERT INTO game_results (winner_id, total_rounds) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, winnerId);
                stmt.setInt(2, totalRounds);

                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Gets the total number of games won by a player.
     *
     * @param username the player's username
     * @return number of games won
     */
    public int getGameWinCount(String username) {
        String sql = "SELECT COUNT(*) as count FROM game_results gr " +
                     "JOIN users u ON gr.winner_id = u.id " +
                     "WHERE u.username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
