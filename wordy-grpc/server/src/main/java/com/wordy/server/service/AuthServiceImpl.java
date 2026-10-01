package com.wordy.server.service;

import com.wordy.proto.AuthServiceGrpc;
import com.wordy.proto.LoginRequest;
import com.wordy.proto.LoginResponse;
import com.wordy.proto.LogoutRequest;
import com.wordy.proto.LogoutResponse;

import com.wordy.server.dao.UserDAO;

import io.grpc.stub.StreamObserver;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Authentication Service Backend Implementation
 * Responsible Team Member: NICOLE DEOCALES
 * Handles user login and logout operations, session management
 */
public class AuthServiceImpl extends AuthServiceGrpc.AuthServiceImplBase {

    private final UserDAO userDAO = new UserDAO();

    // Tracks active sessions (username to sessionId)
    private static final ConcurrentHashMap<String, String> activeSessions = new ConcurrentHashMap<>();

    /**
     * Authenticates user credentials and creates a login session.
     * Responsible Team Member: NICOLE DEOCALES
     * 
     * Validates username/password, terminates previous sessions, and returns user role
     * 
     * @param request Contains username and password
     * @param responseObserver Observer for sending login response with success status and role
     */
    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {

        String username = request.getUsername();
        String password = request.getPassword();
        String newSessionId = generateSessionId();

        LoginResponse.Builder response = LoginResponse.newBuilder();

        try {
            // 1. Validate credentials
            if (!userDAO.validateUser(username, password)) {
                response.setSuccess(false)
                        .setMessage("Invalid username or password");
            } else {

                // 2. Check existing session
                if (activeSessions.containsKey(username)) {

                    // Force logout previous session
                    activeSessions.remove(username);
                    userDAO.setLoggedIn(username, false);

                    System.out.println("Previous session for " + username + " was terminated.");
                }

                // 3. Register new session
                activeSessions.put(username, newSessionId);
                userDAO.setLoggedIn(username, true);

                // 4. Get user role
                String userRole = userDAO.getUserRole(username);

                response.setSuccess(true)
                        .setMessage("Login successful")
                        .setUserRole(userRole != null ? userRole : "PLAYER");
            }

        } catch (Exception e) {
            e.printStackTrace();

            response.setSuccess(false)
                    .setMessage("Server error during login");
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    /**
     * Terminates a user session and clears session data.
     * Responsible Team Member: NICOLE DEOCALES
     * 
     * @param request Contains username of user logging out
     * @param responseObserver Observer for sending logout response
     */
    @Override
    public void logout(LogoutRequest request, StreamObserver<LogoutResponse> responseObserver) {

        String username = request.getUsername();

        try {
            // Remove session
            activeSessions.remove(username);

            // Update DB
            userDAO.setLoggedIn(username, false);

            LogoutResponse response = LogoutResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Logout successful")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            e.printStackTrace();

            LogoutResponse response = LogoutResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Logout failed")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    // Simple session ID generator
    private String generateSessionId() {
        return java.util.UUID.randomUUID().toString();
    }
}