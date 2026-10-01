package com.wordy.server.dao;

import com.wordy.server.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for word records.
 * Manages queries related to words submitted by players.
 */
public class WordDAO {

    /**
     * Saves a word submitted by a player.
     *
     * @param username the player who submitted the word
     * @param word the word submitted
     * @return true if saved successfully
     */
    public boolean saveWord(String username, String word) {
        // First, get the user_id from username
        String userLookupSql = "SELECT id FROM users WHERE username = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement userStmt = conn.prepareStatement(userLookupSql)) {

            userStmt.setString(1, username);
            ResultSet rs = userStmt.executeQuery();

            if (!rs.next()) {
                System.err.println("User not found: " + username);
                return false;
            }

            int userId = rs.getInt("id");

            // Now insert the word with user_id
            String sql = "INSERT INTO words (user_id, word, length) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.setString(2, word);
                stmt.setInt(3, word.length());

                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Gets the top longest words from all players.
     *
     * @param limit number of records to fetch (e.g., 5 for top 5)
     * @return list of String[] where [0]=username, [1]=word, [2]=length
     */
    public List<String[]> getTopLongestWords(int limit) {
        List<String[]> results = new ArrayList<>();
        String sql = "SELECT u.username, w.word, w.length FROM words w " +
                     "JOIN users u ON w.user_id = u.id " +
                     "ORDER BY w.length DESC LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String[] record = new String[3];
                record[0] = rs.getString("username");
                record[1] = rs.getString("word");
                record[2] = String.valueOf(rs.getInt("length"));
                results.add(record);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    /**
     * Gets all words submitted by a specific player.
     *
     * @param username the player
     * @return list of String[] where [0]=word, [1]=length
     */
    public List<String[]> getPlayerWords(String username) {
        List<String[]> results = new ArrayList<>();
        
        // First, get the user_id from username
        String userLookupSql = "SELECT id FROM users WHERE username = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement userStmt = conn.prepareStatement(userLookupSql)) {

            userStmt.setString(1, username);
            ResultSet userRs = userStmt.executeQuery();

            if (!userRs.next()) {
                System.err.println("User not found: " + username);
                return results;
            }

            int userId = userRs.getInt("id");

            // Now get all words for this user
            String sql = "SELECT word, length FROM words WHERE user_id = ? ORDER BY length DESC";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    String[] record = new String[2];
                    record[0] = rs.getString("word");
                    record[1] = String.valueOf(rs.getInt("length"));
                    results.add(record);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    /**
     * Deletes all words submitted by a player (for cleanup).
     *
     * @param username the player
     * @return true if deleted successfully
     */
    public boolean deletePlayerWords(String username) {
        String sql = "DELETE FROM words WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
