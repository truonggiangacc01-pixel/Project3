package com.horseracing.project3.service;

import com.horseracing.project3.dto.request.AssignRoleRequest;
import com.horseracing.project3.dto.request.CreateAccountRequest;
import com.horseracing.project3.dto.request.UpdateAccountRequest;
import com.horseracing.project3.dto.response.UserAccountResponse;
import com.horseracing.project3.entity.*;
import com.horseracing.project3.enums.AccountStatus;
import com.horseracing.project3.enums.UserRole;
import com.horseracing.project3.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountManagementService {

    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private SpectatorRepo spectatorRepo;
    @Autowired
    private HorseOwnerRepo horseOwnerRepo;
    @Autowired
    private JockeyRepo jockeyRepo;
    @Autowired
    private RaceRefereeRepo raceRefereeRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserAccountResponse> getAllAccounts() {
        List<UserAccountResponse> responses = new ArrayList<>();

        // Admin
        for (Admin admin : adminRepo.findAll()) {
            responses.add(toResponse(admin));
        }
        // Spectator
        for (Spectator spec : spectatorRepo.findAll()) {
            responses.add(toResponse(spec));
        }
        // HorseOwner
        for (HorseOwner owner : horseOwnerRepo.findAll()) {
            responses.add(toResponse(owner));
        }
        // Jockey
        for (Jockey jockey : jockeyRepo.findAll()) {
            responses.add(toResponse(jockey));
        }
        // RaceReferee
        for (RaceReferee ref : raceRefereeRepo.findAll()) {
            responses.add(toResponse(ref));
        }

        return responses;
    }

    private UserAccountResponse toResponse(Admin admin) {
        return new UserAccountResponse(admin.getId(), admin.getFullName(), admin.getUserName(), admin.getEmail(), admin.getPhone(), UserRole.ADMIN, "ACTIVE", admin.getBirthDate());
    }

    private UserAccountResponse toResponse(Spectator spec) {
        return new UserAccountResponse(spec.getId(), spec.getFullName(), spec.getUserName(), spec.getEmail(), spec.getPhone(), UserRole.SPECTATOR, "ACTIVE", spec.getBirthDate());
    }

    private UserAccountResponse toResponse(HorseOwner owner) {
        return new UserAccountResponse(owner.getId(), owner.getFullName(), owner.getUserName(), owner.getEmail(), owner.getPhone(), UserRole.HORSE_OWNER,
                owner.getAccountStatus().name(), owner.getBirthDate(), owner.getAddress(), null, null, null);
    }

    private UserAccountResponse toResponse(Jockey jockey) {
        return new UserAccountResponse(jockey.getId(), jockey.getFullName(), jockey.getUserName(), jockey.getEmail(), jockey.getPhone(), UserRole.JOCKEY,
                jockey.getAccountStatus().name(), jockey.getBirthDate(), null, jockey.getExperienceYears(), jockey.getLicenseNumber(), null);
    }

    private UserAccountResponse toResponse(RaceReferee ref) {
        return new UserAccountResponse(ref.getId(), ref.getFullName(), ref.getUserName(), ref.getEmail(), ref.getPhone(), UserRole.RACE_REFEREE,
                ref.getAccountStatus().name(), ref.getBirthDate(), null, ref.getExperienceYears(), null, ref.getCertificateLevel());
    }

    private void validateEmailAndUsername(String email, String username) {
        if (adminRepo.findByEmail(email).isPresent() || spectatorRepo.findByEmail(email).isPresent() ||
            horseOwnerRepo.findByEmail(email).isPresent() || jockeyRepo.findByEmail(email).isPresent() ||
            raceRefereeRepo.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email đã tồn tại trong hệ thống!");
        }
        // Note: Repositories need findByUserName if we validate username, assuming it exists or skip for now.
    }

    @Transactional
    public UserAccountResponse createAccount(CreateAccountRequest req) {
        validateEmailAndUsername(req.getEmail(), req.getUserName());
        String encodedPassword = passwordEncoder.encode(req.getPassword());

        switch (req.getRole()) {
            case ADMIN:
                Admin admin = new Admin(req.getFullName(), req.getUserName(), req.getEmail(), req.getPhone(), encodedPassword, req.getBirthDate());
                admin = adminRepo.save(admin);
                return toResponse(admin);

            case SPECTATOR:
                Spectator spec = new Spectator(req.getFullName(), req.getUserName(), req.getEmail(), req.getPhone(), encodedPassword, req.getBirthDate());
                spec = spectatorRepo.save(spec);
                return toResponse(spec);

            case HORSE_OWNER:
                HorseOwner owner = new HorseOwner(req.getFullName(), req.getUserName(), req.getEmail(), req.getPhone(), encodedPassword, req.getBirthDate(), req.getAddress());
                owner.setAccountStatus(AccountStatus.APPROVED); // Default for admin creation
                owner = horseOwnerRepo.save(owner);
                return toResponse(owner);

            case JOCKEY:
                Jockey jockey = new Jockey(req.getFullName(), req.getUserName(), req.getEmail(), req.getPhone(), encodedPassword, req.getBirthDate(), req.getExperienceYears(), req.getLicenseNumber());
                jockey.setAccountStatus(AccountStatus.APPROVED);
                jockey = jockeyRepo.save(jockey);
                return toResponse(jockey);

            case RACE_REFEREE:
                RaceReferee ref = new RaceReferee(req.getFullName(), req.getUserName(), req.getEmail(), req.getPhone(), encodedPassword, req.getBirthDate(), req.getExperienceYears(), req.getCertificateLevel());
                ref.setAccountStatus(AccountStatus.APPROVED);
                ref = raceRefereeRepo.save(ref);
                return toResponse(ref);

            default:
                throw new RuntimeException("Role không hợp lệ!");
        }
    }

    @Transactional
    public UserAccountResponse updateAccount(UserRole role, Integer id, UpdateAccountRequest req) {
        switch (role) {
            case ADMIN:
                Admin admin = adminRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy Admin"));
                admin.setFullName(req.getFullName());
                admin.setPhone(req.getPhone());
                admin.setBirthDate(req.getBirthDate());
                admin = adminRepo.save(admin);
                return toResponse(admin);

            case SPECTATOR:
                Spectator spec = spectatorRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy Spectator"));
                spec.setFullName(req.getFullName());
                spec.setPhone(req.getPhone());
                spec.setBirthDate(req.getBirthDate());
                spec = spectatorRepo.save(spec);
                return toResponse(spec);

            case HORSE_OWNER:
                HorseOwner owner = horseOwnerRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy HorseOwner"));
                owner.setFullName(req.getFullName());
                owner.setPhone(req.getPhone());
                owner.setBirthDate(req.getBirthDate());
                if (req.getAddress() != null) owner.setAddress(req.getAddress());
                if (req.getStatus() != null) owner.setAccountStatus(AccountStatus.valueOf(req.getStatus()));
                owner = horseOwnerRepo.save(owner);
                return toResponse(owner);

            case JOCKEY:
                Jockey jockey = jockeyRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy Jockey"));
                jockey.setFullName(req.getFullName());
                jockey.setPhone(req.getPhone());
                jockey.setBirthDate(req.getBirthDate());
                if (req.getExperienceYears() != null) jockey.setExperienceYears(req.getExperienceYears());
                if (req.getLicenseNumber() != null) jockey.setLicenseNumber(req.getLicenseNumber());
                if (req.getStatus() != null) jockey.setAccountStatus(AccountStatus.valueOf(req.getStatus()));
                jockey = jockeyRepo.save(jockey);
                return toResponse(jockey);

            case RACE_REFEREE:
                RaceReferee ref = raceRefereeRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy RaceReferee"));
                ref.setFullName(req.getFullName());
                ref.setPhone(req.getPhone());
                ref.setBirthDate(req.getBirthDate());
                if (req.getExperienceYears() != null) ref.setExperienceYears(req.getExperienceYears());
                if (req.getCertificateLevel() != null) ref.setCertificateLevel(req.getCertificateLevel());
                if (req.getStatus() != null) ref.setAccountStatus(AccountStatus.valueOf(req.getStatus()));
                ref = raceRefereeRepo.save(ref);
                return toResponse(ref);

            default:
                throw new RuntimeException("Role không hợp lệ!");
        }
    }

    @Transactional
    public UserAccountResponse assignRole(UserRole currentRole, Integer id, AssignRoleRequest req) {
        if (currentRole == req.getNewRole()) {
            throw new RuntimeException("Role mới phải khác role hiện tại!");
        }

        String fullName = null, userName = null, email = null, phone = null, password = null;
        java.time.LocalDate birthDate = null;

        // Fetch current user details
        switch (currentRole) {
            case ADMIN:
                Admin admin = adminRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy Admin"));
                fullName = admin.getFullName(); userName = admin.getUserName(); email = admin.getEmail();
                phone = admin.getPhone(); password = admin.getPassword(); birthDate = admin.getBirthDate();
                adminRepo.delete(admin);
                break;
            case SPECTATOR:
                Spectator spec = spectatorRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy Spectator"));
                fullName = spec.getFullName(); userName = spec.getUserName(); email = spec.getEmail();
                phone = spec.getPhone(); password = spec.getPassword(); birthDate = spec.getBirthDate();
                spectatorRepo.delete(spec);
                break;
            case HORSE_OWNER:
                HorseOwner owner = horseOwnerRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy HorseOwner"));
                fullName = owner.getFullName(); userName = owner.getUserName(); email = owner.getEmail();
                phone = owner.getPhone(); password = owner.getPassword(); birthDate = owner.getBirthDate();
                horseOwnerRepo.delete(owner);
                break;
            case JOCKEY:
                Jockey jockey = jockeyRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy Jockey"));
                fullName = jockey.getFullName(); userName = jockey.getUserName(); email = jockey.getEmail();
                phone = jockey.getPhone(); password = jockey.getPassword(); birthDate = jockey.getBirthDate();
                jockeyRepo.delete(jockey);
                break;
            case RACE_REFEREE:
                RaceReferee ref = raceRefereeRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy RaceReferee"));
                fullName = ref.getFullName(); userName = ref.getUserName(); email = ref.getEmail();
                phone = ref.getPhone(); password = ref.getPassword(); birthDate = ref.getBirthDate();
                raceRefereeRepo.delete(ref);
                break;
        }

        // Create new user entity based on newRole
        switch (req.getNewRole()) {
            case ADMIN:
                Admin newAdmin = new Admin(fullName, userName, email, phone, password, birthDate);
                newAdmin = adminRepo.save(newAdmin);
                return toResponse(newAdmin);
            case SPECTATOR:
                Spectator newSpec = new Spectator(fullName, userName, email, phone, password, birthDate);
                newSpec = spectatorRepo.save(newSpec);
                return toResponse(newSpec);
            case HORSE_OWNER:
                HorseOwner newOwner = new HorseOwner(fullName, userName, email, phone, password, birthDate, req.getAddress() != null ? req.getAddress() : "");
                newOwner.setAccountStatus(AccountStatus.APPROVED);
                newOwner = horseOwnerRepo.save(newOwner);
                return toResponse(newOwner);
            case JOCKEY:
                Jockey newJockey = new Jockey(fullName, userName, email, phone, password, birthDate, req.getExperienceYears() != null ? req.getExperienceYears() : 0, req.getLicenseNumber() != null ? req.getLicenseNumber() : "");
                newJockey.setAccountStatus(AccountStatus.APPROVED);
                newJockey = jockeyRepo.save(newJockey);
                return toResponse(newJockey);
            case RACE_REFEREE:
                RaceReferee newRef = new RaceReferee(fullName, userName, email, phone, password, birthDate, req.getExperienceYears() != null ? req.getExperienceYears() : 0, req.getCertificateLevel() != null ? req.getCertificateLevel() : "");
                newRef.setAccountStatus(AccountStatus.APPROVED);
                newRef = raceRefereeRepo.save(newRef);
                return toResponse(newRef);
            default:
                throw new RuntimeException("Role mới không hợp lệ!");
        }
    }

    @Transactional
    public void deleteAccount(UserRole role, Integer id) {
        switch (role) {
            case ADMIN:
                // Cannot delete active accounts
                throw new RuntimeException("Tài khoản đang hoạt động, không thể xóa!");
            case SPECTATOR:
                throw new RuntimeException("Tài khoản đang hoạt động, không thể xóa!");
            case HORSE_OWNER:
                HorseOwner owner = horseOwnerRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy"));
                if (owner.getAccountStatus() == AccountStatus.APPROVED) {
                    throw new RuntimeException("Tài khoản đang hoạt động, không thể xóa!");
                }
                horseOwnerRepo.delete(owner);
                break;
            case JOCKEY:
                Jockey jockey = jockeyRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy"));
                if (jockey.getAccountStatus() == AccountStatus.APPROVED) {
                    throw new RuntimeException("Tài khoản đang hoạt động, không thể xóa!");
                }
                jockeyRepo.delete(jockey);
                break;
            case RACE_REFEREE:
                RaceReferee ref = raceRefereeRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy"));
                if (ref.getAccountStatus() == AccountStatus.APPROVED) {
                    throw new RuntimeException("Tài khoản đang hoạt động, không thể xóa!");
                }
                raceRefereeRepo.delete(ref);
                break;
            default:
                throw new RuntimeException("Role không hợp lệ!");
        }
    }
}
