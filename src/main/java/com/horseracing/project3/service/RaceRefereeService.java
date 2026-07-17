package com.horseracing.project3.service;

import com.horseracing.project3.entity.RaceReferee;
import com.horseracing.project3.repository.RaceRefereeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaceRefereeService {

    @Autowired
    private RaceRefereeRepo raceRefereeRepo;

    public void saveRaceReferee(RaceReferee racereferee) {
        raceRefereeRepo.save(racereferee);
    }

    public com.horseracing.project3.dto.response.ApiResponse<RaceReferee> updateRefereeProfile(Integer refereeId, com.horseracing.project3.dto.request.RefereeProfileUpdateRequest request) {
        RaceReferee referee = raceRefereeRepo.findById(refereeId).orElseThrow(() -> new RuntimeException("Race Referee not found"));
        
        if (request.getFullName() == null || request.getFullName().trim().length() < 4) {
            throw new RuntimeException("Full name phải có ít nhất 4 ký tự");
        }
        if (request.getPhone() == null || !request.getPhone().matches("^0\\d{9}$")) {
            throw new RuntimeException("số điện thoại chưa đúng định dạng");
        }

        referee.setFullName(request.getFullName());
        referee.setPhone(request.getPhone());
        referee.setBirthDate(request.getBirthDate());
        
        if (request.getExperienceYears() != null) {
            if (request.getExperienceYears() < 1) {
                throw new RuntimeException("số năm kinh nghiệm phải ít nhất là 1 năm");
            }
            if (request.getBirthDate() != null) {
                int age = java.time.LocalDate.now().getYear() - request.getBirthDate().getYear();
                if (request.getExperienceYears() > age) {
                    throw new RuntimeException("số năm kinh nghiệm không được lớn hơn số tuổi");
                }
            }
            referee.setExperienceYears(request.getExperienceYears());
        }

        raceRefereeRepo.save(referee);
        return new com.horseracing.project3.dto.response.ApiResponse<>(true, "Cập nhật thông tin trọng tài thành công!", referee);
    }

    public com.horseracing.project3.dto.response.ApiResponse<RaceReferee> updateCertificate(Integer refereeId, String certificateLevel) {
        RaceReferee referee = raceRefereeRepo.findById(refereeId).orElseThrow(() -> new RuntimeException("Race Referee not found"));
        
        if (certificateLevel == null || certificateLevel.trim().isEmpty()) {
            throw new RuntimeException("Certificate level cannot be empty");
        }

        referee.setCertificateLevel(certificateLevel);
        raceRefereeRepo.save(referee);
        return new com.horseracing.project3.dto.response.ApiResponse<>(true, "Cập nhật chứng chỉ thành công!", referee);
    }
}
