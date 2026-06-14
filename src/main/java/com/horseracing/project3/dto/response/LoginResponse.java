package com.horseracing.project3.dto.response;

import com.horseracing.project3.enums.UserRole;

public class LoginResponse {

    private boolean success;
    private String message;
    private String fullName;
    private UserRole role;
    private String token;

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR RỖNG

    public LoginResponse() {
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR FULL

    public LoginResponse(boolean success, String message, String fullName, UserRole role, String token) {
        this.success = success;
        this.message = message;
        this.fullName = fullName;
        this.role = role;
        this.token = token;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  GETTER, SETTER

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
