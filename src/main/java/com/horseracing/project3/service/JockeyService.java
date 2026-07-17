package com.horseracing.project3.service;

import com.horseracing.project3.entity.Jockey;
import com.horseracing.project3.repository.JockeyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import com.horseracing.project3.dto.request.JockeyProfileUpdateRequest;
import com.horseracing.project3.dto.response.ApiResponse;

@Service
public class JockeyService {

    @Autowired
    private JockeyRepo jockeyRepo;

    public void saveJockey(Jockey jockey) {
        jockeyRepo.save(jockey);
    }

    public ApiResponse<Jockey> updateJockeyProfile(Integer jockeyId, JockeyProfileUpdateRequest request) {
        Jockey jockey = jockeyRepo.findById(jockeyId).orElseThrow(() -> new RuntimeException("Jockey not found"));
        
        if (request.getFullName() == null || request.getFullName().trim().length() < 4) {
            throw new RuntimeException("Full name phải có ít nhất 4 ký tự");
        }
        if (request.getPhone() == null || !request.getPhone().matches("^0\\d{9}$")) {
            throw new RuntimeException("số điện thoại chưa đúng định dạng");
        }
        if (request.getUserName() != null) {
            if (request.getUserName().trim().length() < 4) {
                throw new RuntimeException("User name phải có ít nhất 4 ký tự");
            }
            if (!jockey.getUserName().equals(request.getUserName()) && jockeyRepo.existsByUserName(request.getUserName())) {
                throw new RuntimeException("Username đã tồn tại");
            }
            jockey.setUserName(request.getUserName());
        }

        jockey.setFullName(request.getFullName());
        jockey.setPhone(request.getPhone());
        jockey.setBirthDate(request.getBirthDate());
        if (request.getExperienceYears() != null) {
            if (request.getExperienceYears() < 1) {
                throw new RuntimeException("số năm kinh nghiệm phải ít nhất là 1 năm");
            }
            if (request.getBirthDate() != null) {
                int age = LocalDate.now().getYear() - request.getBirthDate().getYear();
                if (request.getExperienceYears() > age) {
                    throw new RuntimeException("số năm kinh nghiệm không được lớn hơn số tuổi");
                }
            }
            jockey.setExperienceYears(request.getExperienceYears());
        }
        jockey.setLicenseNumber(request.getLicenseNumber());
        if (request.getLicenseExpiryDate() != null) {
            jockey.setLicenseExpiryDate(request.getLicenseExpiryDate());
        }

        jockeyRepo.save(jockey);

        boolean isExpired = jockey.getLicenseExpiryDate() != null && jockey.getLicenseExpiryDate().isBefore(LocalDate.now());
        String message = isExpired ? "Cập nhật hồ sơ thành công. Cảnh báo: Giấy phép của bạn đã hết hạn!" : "Cập nhật hồ sơ thành công.";

        return new ApiResponse<>(true, message, jockey);
    }
    public ApiResponse<Jockey> updateLicense(Integer jockeyId, String licenseNumber, LocalDate licenseExpiryDate) {
        if (licenseExpiryDate != null && licenseExpiryDate.isBefore(LocalDate.now())) {
            throw new RuntimeException("Không được nhập ngày trong quá khứ");
        }
        Jockey jockey = jockeyRepo.findById(jockeyId).orElseThrow(() -> new RuntimeException("Jockey not found"));
        jockey.setLicenseNumber(licenseNumber);
        jockey.setLicenseExpiryDate(licenseExpiryDate);
        jockeyRepo.save(jockey);
        return new ApiResponse<>(true, "Cập nhật giấy phép thành công", jockey);
    }
}
