package com.horseracing.project3.service;

import com.horseracing.project3.dto.request.LoginRequest;
import com.horseracing.project3.dto.response.LoginResponse;
import com.horseracing.project3.entity.*;
import com.horseracing.project3.enums.AccountStatus;
import com.horseracing.project3.enums.UserRole;
import com.horseracing.project3.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

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
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordResetTokenRepo tokenRepo;
    @Autowired
    private EmailService emailService;

    public LoginResponse authenticate(LoginRequest request) {
        String email = request.getEmail();
        String rawPassword = request.getPassword();

        // 1. Check Admin
        Optional<Admin> adminOpt = adminRepo.findByEmail(email);
        if (adminOpt.isPresent()) {
            if (passwordEncoder.matches(rawPassword, adminOpt.get().getPassword())) {
                String token = jwtService.generateToken(email, UserRole.ADMIN);
                return new LoginResponse(true, "Đăng nhập Admin thành công", adminOpt.get().getFullName(), UserRole.ADMIN, token);
            }
            throw new RuntimeException("Mật khẩu không chính xác!");
        }

        // 2. Check Spectator (Khán giả không cần kiểm tra trạng thái phê duyệt)
        Optional<Spectator> specOpt = spectatorRepo.findByEmail(email);
        if (specOpt.isPresent()) {
            if (passwordEncoder.matches(rawPassword, specOpt.get().getPassword())) {
                String token = jwtService.generateToken(email, UserRole.SPECTATOR);
                return new LoginResponse(true, "Đăng nhập Khán giả thành công", specOpt.get().getFullName(), UserRole.SPECTATOR, token);
            }
            throw new RuntimeException("Mật khẩu không chính xác!");
        }

        // 3. Check HorseOwner
        Optional<HorseOwner> ownerOpt = horseOwnerRepo.findByEmail(email);
        if (ownerOpt.isPresent()) {
            verifyStatus(ownerOpt.get().getAccountStatus()); // Ghi chú: Nếu hàm của bạn tên là getAccountStatus(), hãy sửa lại nhé
            if (passwordEncoder.matches(rawPassword, ownerOpt.get().getPassword())) {
                String token = jwtService.generateToken(email, UserRole.HORSE_OWNER);
                return new LoginResponse(true, "Đăng nhập Chủ ngựa thành công", ownerOpt.get().getFullName(), UserRole.HORSE_OWNER, token);
            }
            throw new RuntimeException("Mật khẩu không chính xác!");
        }

        // 4. Check Jockey
        Optional<Jockey> jockeyOpt = jockeyRepo.findByEmail(email);
        if (jockeyOpt.isPresent()) {
            verifyStatus(jockeyOpt.get().getAccountStatus());
            if (passwordEncoder.matches(rawPassword, jockeyOpt.get().getPassword())) {
                String token = jwtService.generateToken(email, UserRole.JOCKEY);
                return new LoginResponse(true, "Đăng nhập Nài ngựa thành công", jockeyOpt.get().getFullName(), UserRole.JOCKEY, token);
            }
            throw new RuntimeException("Mật khẩu không chính xác!");
        }

        // 5. Check RaceReferee
        Optional<RaceReferee> refOpt = raceRefereeRepo.findByEmail(email);
        if (refOpt.isPresent()) {
            verifyStatus(refOpt.get().getAccountStatus());
            if (passwordEncoder.matches(rawPassword, refOpt.get().getPassword())) {
                String token = jwtService.generateToken(email, UserRole.RACE_REFEREE);
                return new LoginResponse(true, "Đăng nhập Trọng tài thành công", refOpt.get().getFullName(), UserRole.RACE_REFEREE, token);
            }
            throw new RuntimeException("Mật khẩu không chính xác!");
        }

        // Nếu quét qua cả 5 bảng không thấy email
        throw new RuntimeException("Không tìm thấy tài khoản với email này!");
    }

    // Hàm phụ trợ kiểm tra trạng thái (Đáp ứng quy tắc BR-30 / FK-08)
    private void verifyStatus(AccountStatus status) {
        if (status == AccountStatus.PENDING) {
            throw new RuntimeException("Tài khoản của bạn đang chờ Admin phê duyệt, chưa thể đăng nhập!");
        }
        // Nếu sau này bạn có thêm status LOCKED thì thêm điều kiện:
        // if (status == AccountStatus.LOCKED) { throw new RuntimeException("Tài khoản đã bị khóa!"); }
    }

    private boolean isEmailExists(String email) {
        return adminRepo.findByEmail(email).isPresent() ||
               spectatorRepo.findByEmail(email).isPresent() ||
               horseOwnerRepo.findByEmail(email).isPresent() ||
               jockeyRepo.findByEmail(email).isPresent() ||
               raceRefereeRepo.findByEmail(email).isPresent();
    }

    public void forgotPassword(String email) {
        if (!isEmailExists(email)) {
            throw new RuntimeException("Không tìm thấy tài khoản với email này!");
        }

        String otp = String.format("%06d", new java.util.Random().nextInt(999999));
        
        Optional<PasswordResetToken> existingToken = tokenRepo.findByEmail(email);
        PasswordResetToken token;
        if (existingToken.isPresent()) {
            token = existingToken.get();
            token.setOtp(otp);
            token.setExpiryDate(java.time.LocalDateTime.now().plusMinutes(15));
        } else {
            token = new PasswordResetToken(email, otp, java.time.LocalDateTime.now().plusMinutes(15));
        }
        tokenRepo.save(token);

        emailService.sendEmail(email, "Reset Password OTP", "Your OTP is: " + otp + ". It is valid for 15 minutes.");
    }

    public void resetPassword(com.horseracing.project3.dto.request.ResetPasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Mật khẩu xác nhận không khớp!");
        }

        PasswordResetToken token = tokenRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email không có yêu cầu reset mật khẩu!"));

        if (!token.getOtp().equals(request.getOtp())) {
            throw new RuntimeException("OTP không chính xác!");
        }

        if (token.isExpired()) {
            throw new RuntimeException("OTP đã hết hạn!");
        }

        String encodedPassword = passwordEncoder.encode(request.getNewPassword());
        updatePasswordForUser(request.getEmail(), encodedPassword);

        tokenRepo.delete(token);
    }

    private void updatePasswordForUser(String email, String newPassword) {
        Optional<Admin> adminOpt = adminRepo.findByEmail(email);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            admin.setPassword(newPassword);
            adminRepo.save(admin);
            return;
        }

        Optional<Spectator> specOpt = spectatorRepo.findByEmail(email);
        if (specOpt.isPresent()) {
            Spectator spec = specOpt.get();
            spec.setPassword(newPassword);
            spectatorRepo.save(spec);
            return;
        }

        Optional<HorseOwner> ownerOpt = horseOwnerRepo.findByEmail(email);
        if (ownerOpt.isPresent()) {
            HorseOwner owner = ownerOpt.get();
            owner.setPassword(newPassword);
            horseOwnerRepo.save(owner);
            return;
        }

        Optional<Jockey> jockeyOpt = jockeyRepo.findByEmail(email);
        if (jockeyOpt.isPresent()) {
            Jockey jockey = jockeyOpt.get();
            jockey.setPassword(newPassword);
            jockeyRepo.save(jockey);
            return;
        }

        Optional<RaceReferee> refOpt = raceRefereeRepo.findByEmail(email);
        if (refOpt.isPresent()) {
            RaceReferee ref = refOpt.get();
            ref.setPassword(newPassword);
            raceRefereeRepo.save(ref);
            return;
        }

        throw new RuntimeException("Không tìm thấy tài khoản để cập nhật!");
    }

    public Object getUserInfoByEmail(String email) {
        Optional<Admin> adminOpt = adminRepo.findByEmail(email);
        if (adminOpt.isPresent()) {
            return adminOpt.get();
        }

        Optional<Spectator> specOpt = spectatorRepo.findByEmail(email);
        if (specOpt.isPresent()) {
            return specOpt.get();
        }

        Optional<HorseOwner> ownerOpt = horseOwnerRepo.findByEmail(email);
        if (ownerOpt.isPresent()) {
            return ownerOpt.get();
        }

        Optional<Jockey> jockeyOpt = jockeyRepo.findByEmail(email);
        if (jockeyOpt.isPresent()) {
            return jockeyOpt.get();
        }

        Optional<RaceReferee> refOpt = raceRefereeRepo.findByEmail(email);
        if (refOpt.isPresent()) {
            return refOpt.get();
        }

        throw new RuntimeException("User not found!");
    }

}
