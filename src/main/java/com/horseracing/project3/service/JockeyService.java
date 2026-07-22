package com.horseracing.project3.service;

import com.horseracing.project3.entity.Jockey;
import com.horseracing.project3.enums.AccountStatus;
import com.horseracing.project3.repository.JockeyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import com.horseracing.project3.dto.request.JockeyProfileUpdateRequest;
import com.horseracing.project3.dto.response.ApiResponse;

@Service
public class JockeyService {

    @Autowired
    private JockeyRepo jockeyRepo;

    public void saveJockey(Jockey jockey) {
        jockeyRepo.save(jockey);
    }

    public List<Jockey> getAllJockeys() {
        return jockeyRepo.findAll();
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

    public Jockey addJockeyAdmin(Jockey newJockey) {
        if (newJockey.getFullName() == null || newJockey.getFullName().trim().length() < 4) {
            throw new RuntimeException("Tên Jockey phải có ít nhất 4 ký tự");
        }
        if (newJockey.getLicenseNumber() == null || newJockey.getLicenseNumber().trim().isEmpty()) {
            throw new RuntimeException("Giấy phép không được để trống");
        }
        if (newJockey.getUserName() == null || newJockey.getUserName().trim().length() < 4) {
            throw new RuntimeException("Tên đăng nhập phải có ít nhất 4 ký tự");
        }
        if (jockeyRepo.existsByUserName(newJockey.getUserName())) {
            throw new RuntimeException("Username đã tồn tại");
        }
        if (newJockey.getEmail() == null || !newJockey.getEmail().contains("@")) {
            throw new RuntimeException("Email không hợp lệ");
        }
        if (newJockey.getPhone() == null || !newJockey.getPhone().matches("^0\\d{9}$")) {
            throw new RuntimeException("Số điện thoại không hợp lệ");
        }
        if (newJockey.getPassword() == null || newJockey.getPassword().trim().length() < 6) {
            throw new RuntimeException("Mật khẩu phải có ít nhất 6 ký tự");
        }
        if (newJockey.getBirthDate() == null) {
            throw new RuntimeException("Ngày sinh không được để trống");
        }
        if (newJockey.getExperienceYears() == null || newJockey.getExperienceYears() < 0) {
            throw new RuntimeException("Số năm kinh nghiệm không hợp lệ");
        }
        
        // Force status to APPROVED on creation
        newJockey.setAccountStatus(com.horseracing.project3.enums.AccountStatus.APPROVED);
        
        return jockeyRepo.save(newJockey);
    }

    public Jockey updateJockeyAdmin(Integer jockeyId, Jockey updatedData) {
        Jockey existingJockey = jockeyRepo.findById(jockeyId)
                .orElseThrow(() -> new RuntimeException("Jockey not found"));

        if (updatedData.getFullName() == null || updatedData.getFullName().trim().length() < 4) {
            throw new RuntimeException("Tên Jockey phải có ít nhất 4 ký tự");
        }
        if (updatedData.getLicenseNumber() == null || updatedData.getLicenseNumber().trim().isEmpty()) {
            throw new RuntimeException("Giấy phép không được để trống");
        }
        if (updatedData.getEmail() == null || !updatedData.getEmail().contains("@")) {
            throw new RuntimeException("Email không hợp lệ");
        }
        if (updatedData.getPhone() == null || !updatedData.getPhone().matches("^0\\d{9}$")) {
            throw new RuntimeException("Số điện thoại không hợp lệ");
        }
        if (updatedData.getBirthDate() == null) {
            throw new RuntimeException("Ngày sinh không được để trống");
        }
        if (updatedData.getExperienceYears() == null || updatedData.getExperienceYears() < 0) {
            throw new RuntimeException("Số năm kinh nghiệm không hợp lệ");
        }

        existingJockey.setFullName(updatedData.getFullName());
        existingJockey.setLicenseNumber(updatedData.getLicenseNumber());
        existingJockey.setEmail(updatedData.getEmail());
        existingJockey.setPhone(updatedData.getPhone());
        existingJockey.setBirthDate(updatedData.getBirthDate());
        existingJockey.setExperienceYears(updatedData.getExperienceYears());
        
        if (updatedData.getPassword() != null && !updatedData.getPassword().trim().isEmpty()) {
            if (updatedData.getPassword().trim().length() < 6) {
                throw new RuntimeException("Mật khẩu phải có ít nhất 6 ký tự");
            }
            existingJockey.setPassword(updatedData.getPassword());
        }
        
        if (updatedData.getAccountStatus() != null) {
            existingJockey.setAccountStatus(updatedData.getAccountStatus());
        }

        return jockeyRepo.save(existingJockey);
    }

    public void deleteJockeyAdmin(Integer jockeyId) {
        if (!jockeyRepo.existsById(jockeyId)) {
            throw new RuntimeException("Jockey not found");
        }
        jockeyRepo.deleteById(jockeyId);
    }
}
