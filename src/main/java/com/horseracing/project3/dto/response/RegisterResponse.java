package com.horseracing.project3.dto.response;

public class RegisterResponse {

    private boolean success;
    private String message;

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR RỖNG

    public RegisterResponse() {
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR FULL

    public RegisterResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
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
}
