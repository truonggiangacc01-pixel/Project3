package com.horseracing.project3.dto.request;

import com.horseracing.project3.enums.UserRole;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class RegisterRequest {

    // --- THÔNG TIN CHUNG (TRANG 1) ---
    @NotBlank(message = "fullName is required")
    @Size(min = 4, max = 255, message = "fullName must be between 4 and 20 characters long.")
    private String fullName;

    @NotBlank(message = "userName is required")
    @Size(min = 4, max = 255, message = "userName must be between 4 and 20 characters long.")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not in the correct format")
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^0\\d{9,10}$", message = "The phone number must contain 10-11 digits")
    private String phone;

    @NotBlank(message = "Password is required")
    @Size(min = 4, message = "The password must be at least 4 character long")
    private String password;

    @NotNull(message = "Birth date is required")
    @Past(message = "The date of birth must be in the past")
    private LocalDate birthDate;

    @NotNull(message = "role is required")
    private UserRole role;

    // --- THÔNG TIN RIÊNG (TRANG 2) ---

    // Dành cho HorseOwner
    private String address;

    // Dành cho Jockey & RaceReferee
    @Positive(message = "The experience year must be positive")
    private Integer experienceYears;

    // Dành cho Jockey
    private String licenseNumber;

    // Dành cho RaceReferee
    private String certificateLevel;

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR RỖNG

    public RegisterRequest() {
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR FULL

    public RegisterRequest(String fullName, String userName, String email, String phone, String password, LocalDate birthDate, UserRole role, String address, Integer experienceYears, String licenseNumber, String certificateLevel) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.birthDate = birthDate;
        this.role = role;
        this.address = address;
        this.experienceYears = experienceYears;
        this.licenseNumber = licenseNumber;
        this.certificateLevel = certificateLevel;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  GETTER, SETTER

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getCertificateLevel() {
        return certificateLevel;
    }

    public void setCertificateLevel(String certificateLevel) {
        this.certificateLevel = certificateLevel;
    }
}
