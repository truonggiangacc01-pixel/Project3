package com.horseracing.project3.dto.request;

import com.horseracing.project3.enums.UserRole;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class RegisterRequest {

    // --- THÔNG TIN CHUNG (TRANG 1) ---
    @NotBlank(message = "fullName is required")
    @Size(min = 4, message = "Full Name trong khoảng 4 đến 20 ký tự. Full Name đang dưới 4 ký tự")
    @Size(max = 20, message = "Full Name trong khoảng 4 đến 20 ký tự. Full Name đang trên 20 ký tự")
    private String fullName;

    @NotBlank(message = "userName is required")
    @Size(min = 4, message = "User Name trong khoảng 4 đến 20 ký tự. User Name đang dưới 4 ký tự")
    @Size(max = 20, message = "User Name trong khoảng 4 đến 20 ký tự. User Name đang trên 20 ký tự")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "không đúng định dạng gmail")
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^0\\d{9,10}$", message = "số điện thoại không hợp lệ")
    private String phone;

    @NotBlank(message = "Password is required")
    @Size(min = 4, message = "Mật khẩu phải có ít nhất 4 ký tự")
    private String password;

    @NotNull(message = "Birth date is required")
    @Past(message = "The date of birth must be in the past")
    private LocalDate birthDate;

    @NotNull(message = "role is required")
    private UserRole role;

    // --- THÔNG TIN RIÊNG (TRANG 2) ---

    // Dành cho HorseOwner
    @Size(min = 4, message = "Địa chỉ khoảng 4 đến 20 ký tự. Địa chỉ đang dưới 4 ký tự")
    @Size(max = 20, message = "Địa chỉ khoảng 4 đến 20 ký tự. Địa chỉ đang trên 20 ký tự")
    private String address;

    @Size(min = 4, message = "Tên trang trại khoảng 4 đến 20 ký tự. Tên trang trại đang dưới 4 ký tự")
    @Size(max = 20, message = "Tên trang trại khoảng 4 đến 20 ký tự. Tên trang trại đang trên 20 ký tự")
    private String stableName;

    @Min(value = 1, message = "Phải sở hữu ít nhất 1 con ngựa mới được phép đăng ký")
    private Integer horseCount;

    // Dành cho Jockey & RaceReferee
    @Min(value = 0, message = "Số năm kinh nghiệm không được phép là số âm")
    private Integer experienceYears;

    // Dành cho Jockey
    private String licenseNumber;

    // Dành cho RaceReferee
    private String certificateLevel;

    @Size(min = 4, message = "Tên hiệp hội khoảng 4 đến 20 ký tự. Tên hiệp hội đang dưới 4 ký tự")
    @Size(max = 20, message = "Tên hiệp hội khoảng 4 đến 20 ký tự. Tên hiệp hội đang trên 20 ký tự")
    private String associationName;

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR RỖNG

    public RegisterRequest() {
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR FULL

    public RegisterRequest(String fullName, String userName, String email, String phone, String password, LocalDate birthDate, UserRole role, String address, Integer experienceYears, String licenseNumber, String certificateLevel, String stableName, Integer horseCount, String associationName) {
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
        this.stableName = stableName;
        this.horseCount = horseCount;
        this.associationName = associationName;
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

    public String getStableName() {
        return stableName;
    }

    public void setStableName(String stableName) {
        this.stableName = stableName;
    }

    public Integer getHorseCount() {
        return horseCount;
    }

    public void setHorseCount(Integer horseCount) {
        this.horseCount = horseCount;
    }

    public String getAssociationName() {
        return associationName;
    }

    public void setAssociationName(String associationName) {
        this.associationName = associationName;
    }
}
