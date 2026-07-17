package com.horseracing.project3.service;

import com.horseracing.project3.dto.request.TournamentRequestDto;
import com.horseracing.project3.dto.response.TournamentResponseDto;
import com.horseracing.project3.dto.request.UpdateTournamentRequestDto;
import com.horseracing.project3.dto.request.CancelTournamentRequestDto;
import com.horseracing.project3.dto.request.OpenRegistrationRequestDto;
import com.horseracing.project3.enums.TournamentStatus;
import com.horseracing.project3.entity.Admin;
import com.horseracing.project3.entity.Tournament;
import com.horseracing.project3.repository.AdminRepo;
import com.horseracing.project3.repository.TournamentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TournamentService {

    @Autowired
    private TournamentRepo tournamentRepo;
    
    @Autowired
    private AdminRepo adminRepo;

    @Transactional(readOnly = true)
    public List<TournamentResponseDto> getAllTournaments() {
        return tournamentRepo.findAll().stream().map(t -> {
            TournamentResponseDto dto = new TournamentResponseDto(
                    t.getId(),
                    t.getName(),
                    t.getLocation(),
                    t.getStartDate(),
                    t.getEndDate()
            );
            dto.setStatus(t.getStatus() != null ? t.getStatus().name() : null);
            int count = (t.getRaceScheduleList() != null) ? t.getRaceScheduleList().size() : 0;
            dto.setRacesCount(count);
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public TournamentResponseDto createTournament(TournamentRequestDto requestDto, String adminEmail) {
        // Validate date
        if (requestDto.getStartDate().isBefore(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Ngày bắt đầu giải đấu không được phép diễn ra trước ngày tạo mới giải đấu");
        }
        if (requestDto.getEndDate().isBefore(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Ngày kết thúc giải đấu không được phép diễn ra trước ngày tạo mới giải đấu");
        }
        if (requestDto.getStartDate().isAfter(requestDto.getEndDate())) {
            throw new IllegalArgumentException("Ngày bắt đầu không được lớn hơn ngày kết thúc");
        }

        // Get admin
        Admin admin = adminRepo.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Admin với email: " + adminEmail));

        // Create entity
        Tournament tournament = new Tournament();
        tournament.setName(requestDto.getName());
        tournament.setLocation(requestDto.getLocation());
        tournament.setStartDate(requestDto.getStartDate());
        tournament.setEndDate(requestDto.getEndDate());
        tournament.setAdmin(admin);

        // Save entity
        Tournament savedTournament = tournamentRepo.save(tournament);

        // Create response
        return new TournamentResponseDto(
                savedTournament.getId(),
                savedTournament.getName(),
                savedTournament.getLocation(),
                savedTournament.getStartDate(),
                savedTournament.getEndDate()
        );
    }
    public Tournament saveTournament(Tournament tournament) {
        return tournamentRepo.save(tournament);
    }

    @Transactional
    public TournamentResponseDto updateTournament(Integer id, UpdateTournamentRequestDto requestDto) {
        Tournament tournament = tournamentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giải đấu"));

        if (tournament.getStatus() == TournamentStatus.ONGOING) {
            // Constraint 1: restrict edit
            if (requestDto.getName() != null) {
                if (requestDto.getName().trim().isEmpty()) throw new IllegalArgumentException("Tên giải đấu không được để trống");
                tournament.setName(requestDto.getName());
            }
            if (requestDto.getLocation() != null) {
                if (requestDto.getLocation().trim().isEmpty()) throw new IllegalArgumentException("Địa điểm không được để trống");
                tournament.setLocation(requestDto.getLocation());
            }
        } else {
            // Allow full update
            if (requestDto.getStartDate() != null && requestDto.getEndDate() != null) {
                 if (requestDto.getStartDate().isBefore(java.time.LocalDate.now())) {
                     throw new IllegalArgumentException("Ngày bắt đầu giải đấu không được phép diễn ra trước ngày tạo mới giải đấu");
                 }
                 if (requestDto.getEndDate().isBefore(java.time.LocalDate.now())) {
                     throw new IllegalArgumentException("Ngày kết thúc giải đấu không được phép diễn ra trước ngày tạo mới giải đấu");
                 }
                 if (requestDto.getStartDate().isAfter(requestDto.getEndDate())) {
                     throw new IllegalArgumentException("Ngày bắt đầu không được lớn hơn ngày kết thúc");
                 }
                 tournament.setStartDate(requestDto.getStartDate());
                 tournament.setEndDate(requestDto.getEndDate());
            }
            if (requestDto.getName() != null) {
                if (requestDto.getName().trim().isEmpty()) throw new IllegalArgumentException("Tên giải đấu không được để trống");
                tournament.setName(requestDto.getName());
            }
            if (requestDto.getLocation() != null) {
                if (requestDto.getLocation().trim().isEmpty()) throw new IllegalArgumentException("Địa điểm không được để trống");
                tournament.setLocation(requestDto.getLocation());
            }
        }

        if (requestDto.getStatus() != null) {
            if (requestDto.getStatus() == TournamentStatus.ACTIVE && tournament.getStatus() == TournamentStatus.DRAFT) {
                // Constraint 2: check if there is at least one Race
                if (tournament.getRaceScheduleList() == null || tournament.getRaceScheduleList().isEmpty()) {
                    throw new RuntimeException("Không có race thì giải không hợp lệ");
                }
            }
            tournament.setStatus(requestDto.getStatus());
        }

        Tournament savedTournament = tournamentRepo.save(tournament);
        return new TournamentResponseDto(
                savedTournament.getId(),
                savedTournament.getName(),
                savedTournament.getLocation(),
                savedTournament.getStartDate(),
                savedTournament.getEndDate()
        );
    }

    @Transactional
    public void cancelTournament(Integer id, CancelTournamentRequestDto requestDto) {
        Tournament tournament = tournamentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giải đấu"));

        if (tournament.getStatus() == TournamentStatus.COMPLETED || tournament.getStatus() == TournamentStatus.CANCELLED) {
            throw new RuntimeException("Giải đấu đã kết thúc hoặc bị hủy, không thể hủy");
        }

        if (!requestDto.isForceCancel()) {
            throw new RuntimeException("Yêu cầu xác nhận đặc biệt: Vui lòng gửi forceCancel = true để xác nhận hủy.");
        }

        if (requestDto.getReason() == null || requestDto.getReason().trim().isEmpty()) {
            throw new IllegalArgumentException("Lý do hủy là bắt buộc");
        }

        tournament.setStatus(TournamentStatus.CANCELLED);
        tournament.setCancelReason(requestDto.getReason());
        tournamentRepo.save(tournament);
    }

    @Transactional
    public void openRegistration(Integer id, OpenRegistrationRequestDto requestDto) {
        Tournament tournament = tournamentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giải đấu"));

        if (requestDto.getRegistrationStartDate() == null || requestDto.getRegistrationEndDate() == null) {
            throw new IllegalArgumentException("Thời gian đăng ký không được để trống");
        }

        if (tournament.getStatus() == TournamentStatus.COMPLETED || tournament.getStatus() == TournamentStatus.CANCELLED) {
            throw new RuntimeException("Không thể mở đăng ký cho giải đấu đã kết thúc hoặc bị hủy");
        }

        if (requestDto.getRegistrationStartDate().isAfter(requestDto.getRegistrationEndDate())) {
            throw new IllegalArgumentException("Ngày đóng đăng ký không được diễn ra trước ngày mở đăng ký");
        }

        if (requestDto.getRegistrationEndDate().toLocalDate().isAfter(tournament.getStartDate())) {
            throw new IllegalArgumentException("Ngày đóng đăng ký không được vượt quá ngày bắt đầu giải đấu");
        }

        if (tournament.getRaceScheduleList() == null || tournament.getRaceScheduleList().isEmpty()) {
             throw new RuntimeException("Lịch thi đấu chưa được công bố. Vui lòng thêm lịch thi đấu trước khi mở đăng ký");
        }

        tournament.setRegistrationStartDate(requestDto.getRegistrationStartDate());
        tournament.setRegistrationEndDate(requestDto.getRegistrationEndDate());
        tournament.setRegistrationOpen(true);
        tournamentRepo.save(tournament);
    }
}
