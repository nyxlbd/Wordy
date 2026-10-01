package com.wordy.server.dao;

import com.wordy.server.db.DBConnection;

import java.sql.*;

/**
 * Team Member: Charles
 * Data Access Object for game configuration.
 * Manages system-wide settings like wait_time and round_duration.
 */
public class ConfigDAO {

    /**
     * Gets a configuration value by key.
     *
     * @param key the configuration key (e.g., "wait_time", "round_duration")
     * @return the configuration value as String, or null if not found
     */
    public String getConfig(String key) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT " + key + " FROM config WHERE id = 1")) {

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return String.valueOf(rs.getInt(key));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets a configuration value as integer.
     *
     * @param key the configuration key
     * @param defaultValue the default value if key not found
     * @return the configuration value as int
     */
    public int getConfigAsInt(String key, int defaultValue) {
        String value = getConfig(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    /**
     * Updates a configuration value.
     *
     * @param key the configuration key (wait_time or round_duration)
     * @param value the new value
     * @return true if updated successfully
     */
    public boolean updateConfig(String key, String value) {
        // Validate key to prevent SQL injection
        if (!key.equals("wait_time") && !key.equals("round_duration")) {
            System.err.println("Invalid config key: " + key);
            return false;
        }

        String updateSql = "UPDATE config SET " + key + " = ? WHERE id = 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateSql)) {

            try {
                int intValue = Integer.parseInt(value);
                stmt.setInt(1, intValue);
            } catch (NumberFormatException e) {
                System.err.println("Invalid value for config: " + value);
                return false;
            }

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Deletes a configuration entry.
     *
     * @param key the configuration key
     * @return true if deleted successfully
     */
    public boolean deleteConfig(String key) {
        String sql = "DELETE FROM config WHERE key = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, key);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Gets the wait time duration in milliseconds.
     *
     * @param defaultValue the default value in seconds if not found
     * @return the wait time in milliseconds
     */
    public int getWaitTime(int defaultValue) {
        String sql = "SELECT wait_time FROM config WHERE id = 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int waitTimeSeconds = rs.getInt("wait_time");
                return waitTimeSeconds * 1000; // Convert to milliseconds
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return defaultValue * 1000; // Return default in milliseconds
    }

    /**
     * Gets the round duration in milliseconds.
     *
     * @param defaultValue the default value in seconds if not found
     * @return the round duration in milliseconds
     */
    public int getRoundDuration(int defaultValue) {
        String sql = "SELECT round_duration FROM config WHERE id = 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int roundDurationSeconds = rs.getInt("round_duration");
                return roundDurationSeconds * 1000; // Convert to milliseconds
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return defaultValue * 1000; // Return default in milliseconds
    }

    /**
     * Updates the wait time configuration.
     *
     * @param waitTimeSeconds the new wait time in seconds
     * @return true if updated successfully
     */
    public boolean updateWaitTime(int waitTimeSeconds) {
        String sql = "UPDATE config SET wait_time = ? WHERE id = 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, waitTimeSeconds);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Updates the round duration configuration.
     *
     * @param roundDurationSeconds the new round duration in seconds
     * @return true if updated successfully
     */
    public boolean updateRoundDuration(int roundDurationSeconds) {
        String sql = "UPDATE config SET round_duration = ? WHERE id = 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roundDurationSeconds);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
