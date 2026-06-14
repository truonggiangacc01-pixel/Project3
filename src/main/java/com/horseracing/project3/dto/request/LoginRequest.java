package com.horseracing.project3.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not in the correct format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    /*___________________________________________________________________________________________________________ */
    //                                                  GETTER, SETTER

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
