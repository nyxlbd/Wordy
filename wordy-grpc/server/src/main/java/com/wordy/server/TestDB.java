package com.wordy.server;

import com.wordy.server.db.DBConnection;

import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Connected to MySQL Database");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}