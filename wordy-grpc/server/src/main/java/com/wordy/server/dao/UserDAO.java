package com.wordy.server.dao;

import com.wordy.server.db.DBConnection;

import java.sql.*;

public class UserDAO {

    public boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isLoggedIn(String username) {
        String sql = "SELECT is_logged_in FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("is_logged_in");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void setLoggedIn(String username, boolean status) {
        String sql = "UPDATE users SET is_logged_in = ? WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, status);
            stmt.setString(2, username);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUserRole(String username) {
        String sql = "SELECT role FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("role");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) as count FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("count") > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean createUser(String username, String password, String role) {
        String sql = "INSERT INTO users (username, password, role, is_logged_in, wins) VALUES (?, ?, ?, false, 0)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateUser(String username, String password, String role) {
        String sql = "UPDATE users SET password = ?, role = ? WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, password);
            stmt.setString(2, role);
            stmt.setString(3, username);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";

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

    public java.util.List<String[]> getTopPlayersByWins(int limit) {
        java.util.List<String[]> results = new java.util.ArrayList<>();
        String sql = "SELECT username, COALESCE(wins, 0) as wins FROM users ORDER BY wins DESC LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String[] record = new String[2];
                record[0] = rs.getString("username");
                record[1] = String.valueOf(rs.getInt("wins"));
                results.add(record);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public boolean incrementWins(String username) {
        String sql = "UPDATE users SET wins = COALESCE(wins, 0) + 1 WHERE username = ?";

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

    /**
     * Retrieves all players or searches for players by username
     * 
     * @param searchQuery search term (empty for all players)
     * @return List of players with username, wins, role, and id
     */
    public java.util.List<Object[]> searchPlayers(String searchQuery) {
        java.util.List<Object[]> results = new java.util.ArrayList<>();
        String sql;
        
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            // Get all players
            sql = "SELECT id, username, COALESCE(wins, 0) as wins, role FROM users WHERE role = 'player' ORDER BY username";
        } else {
            // Search by username
            sql = "SELECT id, username, COALESCE(wins, 0) as wins, role FROM users WHERE role = 'player' AND username LIKE ? ORDER BY username";
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                stmt.setString(1, "%" + searchQuery + "%");
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] record = new Object[4];
                record[0] = rs.getInt("id");                    // id
                record[1] = rs.getString("username");           // username
                record[2] = rs.getInt("wins");                  // wins
                record[3] = rs.getString("role");               // role
                results.add(record);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    /**
     * Gets username by user ID
     * 
     * @param userId the user ID
     * @return username or null if not found
     */
    public String getUsernameById(int userId) {
        String sql = "SELECT username FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("username");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}