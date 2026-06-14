package com.horseracing.project3.service;
import com.horseracing.project3.dto.request.RegisterRequest;
import com.horseracing.project3.entity.HorseOwner;
import com.horseracing.project3.entity.Jockey;
import com.horseracing.project3.entity.RaceReferee;
import com.horseracing.project3.entity.Spectator;
import com.horseracing.project3.enums.AccountStatus;
import com.horseracing.project3.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Service
public class RegisterService {

    @Autowired
    private SpectatorRepo spectatorRepo;
    @Autowired
    private HorseOwnerRepo horseOwnerRepo;
    @Autowired
    private JockeyRepo jockeyRepo;
    @Autowired
    private RaceRefereeRepo raceRefereeRepo;
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterRequest registerRequest){

        // 1. KIỂM TRA ĐỘ TUỔI (Phải >= 18 tuổi)
        LocalDate today = LocalDate.now();
        int age = Period.between(registerRequest.getBirthDate(), today).getYears();

        if (age < 18) {
            // Bắn lỗi ngay lập tức, quá trình đăng ký sẽ dừng lại
            throw new RuntimeException("Bạn phải từ 18 tuổi trở lên mới được phép tạo tài khoản!");
        }

        // 1. KIỂM TRA TRÙNG EMAIL (Quy tắc BR-02 / FK-01 / FK-02 từ file tài liệu của bạn)

        boolean emailExists = spectatorRepo.existsByEmail(registerRequest.getEmail())
                || horseOwnerRepo.existsByEmail(registerRequest.getEmail())
                || jockeyRepo.existsByEmail(registerRequest.getEmail())
                || raceRefereeRepo.existsByEmail(registerRequest.getEmail())
                || adminRepo.existsByEmail(registerRequest.getEmail());

        if (emailExists) {
            // Bắn ra ngoại lệ trùng email để Controller bắt lấy và trả về mã lỗi cho Frontend
            throw new RuntimeException("Email already exists");
        }

        // 2. KIỂM TRA TRÙNG USERNAME (Đảm bảo tính duy nhất để không bị lỗi trùng lặp DB)
        boolean usernameExists = spectatorRepo.existsByUserName(registerRequest.getUserName())
                || horseOwnerRepo.existsByUserName(registerRequest.getUserName())
                || jockeyRepo.existsByUserName(registerRequest.getUserName())
                || raceRefereeRepo.existsByUserName(registerRequest.getUserName())
                || adminRepo.existsByUserName(registerRequest.getUserName());

        if (usernameExists) {
            throw new RuntimeException("Username already exists");
        }

        // 3. PHÂN LOẠI THEO ROLE ĐỂ LƯU XUỐNG DATABASE TƯƠNG ỨNG
        switch (registerRequest.getRole()){
            case SPECTATOR:
                Spectator spectator = new Spectator(
                        registerRequest.getFullName(),
                        registerRequest.getUserName(),
                        registerRequest.getEmail(),
                        registerRequest.getPhone(),
                        passwordEncoder.encode(registerRequest.getPassword()), // Nếu có Spring Security, hãy mã hóa password tại đây
                        registerRequest.getBirthDate()
                );
                spectatorRepo.save(spectator);
                break;

            case HORSE_OWNER:
                if (registerRequest.getAddress() == null || registerRequest.getAddress().trim().isEmpty()) {
                    throw new RuntimeException("Địa chỉ không được để trống đối với chủ sở hữu ngựa!");
                }
                HorseOwner horseOwner = new HorseOwner(
                        registerRequest.getFullName(),
                        registerRequest.getUserName(),
                        registerRequest.getEmail(),
                        registerRequest.getPhone(),
                        passwordEncoder.encode(registerRequest.getPassword()),
                        registerRequest.getBirthDate(),
                        registerRequest.getAddress()
                );
                // Mẹo: Nếu bạn đã thêm trường status (AccountStatus.PENDING) như tôi gợi ý ở trên, hãy set ở đây:
                // horseOwner.setStatus(AccountStatus.PENDING);
                horseOwner.setAccountStatus(AccountStatus.PENDING);
                horseOwnerRepo.save(horseOwner);
                break;

            case JOCKEY:
                if (registerRequest.getExperienceYears() == null || registerRequest.getLicenseNumber() == null) {
                    throw new RuntimeException("Thông tin số năm kinh nghiệm và số giấy phép của Jockey là bắt buộc!");
                }

                // THÊM ĐOẠN CHECK KINH NGHIỆM VS TUỔI TẠI ĐÂY
                if (registerRequest.getExperienceYears() >= age) {
                    throw new RuntimeException("Số năm kinh nghiệm không thể lớn hơn hoặc bằng tuổi thực của bạn.");
                }

                Jockey jockey = new Jockey(
                        registerRequest.getFullName(),
                        registerRequest.getUserName(),
                        registerRequest.getEmail(),
                        registerRequest.getPhone(),
                        passwordEncoder.encode(registerRequest.getPassword()),
                        registerRequest.getBirthDate(),
                        registerRequest.getExperienceYears(),
                        registerRequest.getLicenseNumber()
                );
                // jockey.setStatus(AccountStatus.PENDING);
                jockey.setAccountStatus(AccountStatus.PENDING);
                jockeyRepo.save(jockey);
                break;

            case RACE_REFEREE:
                if (registerRequest.getExperienceYears() == null || registerRequest.getCertificateLevel() == null) {
                    throw new RuntimeException("Thông tin số năm kinh nghiệm và cấp độ chứng chỉ của Trọng tài là bắt buộc!");
                }
                RaceReferee referee = new RaceReferee(
                        registerRequest.getFullName(),
                        registerRequest.getUserName(),
                        registerRequest.getEmail(),
                        registerRequest.getPhone(),
                        passwordEncoder.encode(registerRequest.getPassword()),
                        registerRequest.getBirthDate(),
                        registerRequest.getExperienceYears(),
                        registerRequest.getCertificateLevel()
                );
                // referee.setStatus(AccountStatus.PENDING);
                referee.setAccountStatus(AccountStatus.PENDING);
                raceRefereeRepo.save(referee);
                break;

            default:
                throw new RuntimeException("Vai trò người dùng (Role) không hợp lệ!");

        }

    }
}


//passwordEncoder.encode(request.getPassword())

