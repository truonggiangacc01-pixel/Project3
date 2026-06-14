package com.horseracing.project3.config;

import com.horseracing.project3.entity.*;
import com.horseracing.project3.enums.RaceScheduleStatus;
import com.horseracing.project3.enums.UserRole;
import com.horseracing.project3.enums.HorseHealthStatus;
import com.horseracing.project3.repository.*;
import com.horseracing.project3.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminService adminService;
    @Autowired
    private HorseService horseService;
    @Autowired
    private HorseOwnerService horseOwnerService;
    @Autowired
    private JockeyService jockeyService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private PredictionService predictionService;
    @Autowired
    private PrizeService prizeService;
    @Autowired
    private RaceParticipationService raceParticipationService;
    @Autowired
    private RaceRefereeService raceRefereeService;
    @Autowired
    private RaceResultService raceResultService;
    @Autowired
    private RaceScheduleService raceScheduleService;
    @Autowired
    private SpectatorService spectatorService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private TournamentService tournamentService;

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
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;


//    private UserRole userRole;
//
//    private String fullName;
//    private String userName;
//    private String email;
//    private String phone;
//    private String password;
//    private LocalDate birthDate;
//
//    private String address;         //Horse Owner
//    private String experienceYears; //Jockey, RaceReferee
//    private String licenseNumber;   //Jockey
//    private String certificateLevel;//RaceReferee


    @Override
    public void run(String... args) throws Exception {
        /*___________________________________________________________________________________________________________ */
        //                                                  ADMIN
        Admin ad1 = new Admin("Nguyễn Trần Văn", "tranvan_admin", "tranavan.admin@duangua.vn", "0912345678", "ad123", LocalDate.of(1995, 10, 14));

        /*___________________________________________________________________________________________________________ */
        //                                                  Spectator
        Spectator spec1 = new Spectator("Nguyễn Văn Tuấn", "khangia_tuan", "tuankh@gmail.com", "0912345671", "spec123", LocalDate.of(1984, 3, 14));
        Spectator spec2 = new Spectator("Trần Thế Thao", "yeuthethao99", "thethao99@gmail.com", "0912345672", "spec123", LocalDate.of(1989, 8, 28));
        Spectator spec3 = new Spectator("Phạm Ngọc Linh", "ngoclinh_fan", "ngoclinh@gmail.com", "0912345673", "spec123", LocalDate.of(1993, 11, 2));
        Spectator spec4 = new Spectator("Lê Huy Hoàng", "huyhoang_bet", "huyhoang@gmail.com", "0912345674", "spec123", LocalDate.of(1997, 5, 19));
        Spectator spec5 = new Spectator("Mai Phương Thảo", "mai_phuong22", "phuongmai@gmail.com", "0912345675", "spec123", LocalDate.of(2002, 1, 7));
        Spectator spec6 = new Spectator("Đặng Thần Tài", "thantai_den", "thantai@gmail.com", "0912345676", "spec123", LocalDate.of(1991, 11, 22));
        Spectator spec7 = new Spectator("Phan Quốc Bảo", "quocbao_88", "quocbao88@gmail.com", "0912345677", "spec123", LocalDate.of(2003, 8, 29));
        Spectator spec8 = new Spectator("Vũ Minh Hằng", "minhhang_vip", "minhhang_vip@gmail.com", "0912345678", "spec123", LocalDate.of(1999, 7, 3));
        Spectator spec9 = new Spectator("Trần Tiến Dũng", "trandung_win", "trandung_win@gmail.com", "0912345679", "spec123", LocalDate.of(1986, 2, 9));
        Spectator spec10 = new Spectator("Lê Thị Lan", "lelan_smile", "lelan@gmail.com", "0912345680", "spec123", LocalDate.of(2005, 6, 30));

        /*___________________________________________________________________________________________________________ */
        //                                                  Horse Owner
        HorseOwner ho1 = new HorseOwner("Lý Gia Thành", "ThanhLGT", "giathanh.owner@gmail.com", "0901112233", "ho123", LocalDate.of(1985, 3, 14), "Quận 9, TP. Hồ Chí Minh");
        HorseOwner ho2 = new HorseOwner("Trương Đại Lộc", "LocTĐL", "dailoc.owner@gmail.com", "0902223344", "ho123", LocalDate.of(1992, 8, 22), "Quận 9, TP. Hồ Chí Minh");
        HorseOwner ho3 = new HorseOwner("Trần Phú Qúy", "QuyTPQ", "phuquy.owner@gmail.com", "0903334455", "ho123", LocalDate.of(1999, 11, 7), "Quận 9, TP. Hồ Chí Minh");
        HorseOwner ho4 = new HorseOwner("Nguyễn Tài Phát", "PhatNTP", "taiphat.owner@gmail.com", "0904445566", "ho123", LocalDate.of(2004, 5, 29), "Quận 9, TP. Hồ Chí Minh");

        /*___________________________________________________________________________________________________________ */
        //                                                  JOCKEY
        Jockey joc1 = new Jockey("Trần Kỵ Thương", "ThuongTKT", "kythuong.jockey@gmail.com", "0981112233", "Jock123", LocalDate.of(1984, 8, 24), 4, "LC-2849", LocalDate.of(2026, 8, 30));
        Jockey joc2 = new Jockey("Lê Bá Vũ", "VuLBV", "bavu.jockey@gmail.com", "0982223344", "Jock123", LocalDate.of(1987, 2, 11), 8, "LC-7105", LocalDate.of(2026, 10, 15));
        Jockey joc3 = new Jockey("Nguyễn Phi Trường", "TruongNPT", "phitruong.jockey@gmail.com", "0983334455", "Jock123", LocalDate.of(1972, 11, 5), 3, "LC-4392", LocalDate.of(2027, 1, 20));
        Jockey joc4 = new Jockey("Phạm Dũng Cảm", "CamPDC", "dungcam.jockey@gmail.com", "0984445566", "Jock123", LocalDate.of(1980, 5, 19), 10, "LC-0516", LocalDate.of(2027, 3, 10));
        Jockey joc5 = new Jockey("Đinh Thần Tốc", "TocĐTT", "thantoc.jockey@gmail.com", "0985556677", "Jock123", LocalDate.of(1961, 12, 30), 6, "LC-8271", LocalDate.of(2027, 6, 5));
        Jockey joc6 = new Jockey("Vũ Phong Nhã", "NhaVPN", "phongnha.jockey@gmail.com", "0986667788", "Jock123", LocalDate.of(1978, 7, 14), 5, "LC-3954", LocalDate.of(2027, 8, 22));
        Jockey joc7 = new Jockey("Hồ Quang Uy", "UyHQU", "quanguy.jockey@gmail.com", "0987778899", "Jock123", LocalDate.of(1985, 3, 3), 9, "LC-6028", LocalDate.of(2027, 11, 11));
        Jockey joc8 = new Jockey("Lý Phi Long", "LongLPL", "philong.jockey@gmail.com", "0988889900", "Jock123", LocalDate.of(1969, 10, 22), 2, "LC-0000", LocalDate.of(2027, 12, 31)); // Đã bổ sung giá trị mặc định cho phần bị thiếu cuối câu

        /*___________________________________________________________________________________________________________ */
        //                                                  RACE REFEREE
        RaceReferee ref1 = new RaceReferee("Lương Công Bằng", "BangLCB", "congbang.ref@mail.com", "0911112233", "RaRe123", LocalDate.of(1972, 3, 14), 4, "Quốc tế");
        RaceReferee ref2 = new RaceReferee("Trịnh Chính Trực", "TrucTCT", "chinhtruc.ref@mail.com", "0912223344", "RaRe123", LocalDate.of(1975, 8, 28), 8, "Quốc tế");
        RaceReferee ref3 = new RaceReferee("Tạ Khách Quan", "QuanTKQ", "khachquan.ref@mail.com", "0913334455", "RaRe123", LocalDate.of(1979, 11, 5), 3, "Quốc tế");
        RaceReferee ref4 = new RaceReferee("Võ Minh Bạch", "AchVMB", "minhbach.ref@mail.com", "0914445566", "RaRe123", LocalDate.of(1981, 1, 22), 10, "Quốc tế");
        RaceReferee ref5 = new RaceReferee("Đào Công Tâm", "AmĐCT", "congtam.ref@mail.com", "0915556677", "RaRe123", LocalDate.of(1984, 10, 9), 6, "Quốc tế");
        RaceReferee ref6 = new RaceReferee("Phan Trung Lập", "LapPTL", "trunglap.ref@mail.com", "0916667788", "RaRe123", LocalDate.of(1986, 12, 31), 5, "Quốc tế");
        RaceReferee ref7 = new RaceReferee("Bùi Đức Minh", "InhBĐM", "duicminh.ref@mail.com", "0917778899", "RaRe123", LocalDate.of(1988, 4, 12), 9, "Quốc tế");
        RaceReferee ref8 = new RaceReferee("Đoàn Quang Sang", "SangĐQS", "quangsang.ref@mail.com", "0918889900", "RaRe123", LocalDate.of(1989, 6, 3), 2, "Quốc tế");
        RaceReferee ref9 = new RaceReferee("Chu Uy Nghiêm", "IemCUN", "uynghiem.ref@mail.com", "0919990011", "RaRe123", LocalDate.of(1990, 9, 25), 4, "Quốc tế");
        RaceReferee ref10 = new RaceReferee("Lâm Quyết Đoán", "DoanLQĐ", "quyetdoan.ref@mail.com", "0911001122", "RaRe123", LocalDate.of(1991, 9, 25), 8, "Quốc tế");


        /*___________________________________________________________________________________________________________ */
        //                                                  TOURNAMENT

        Tournament tour1 = new Tournament("Giải Vô Địch Quốc Gia 2025", "Trường đua Phú Thọ", LocalDate.of(2025, 12, 20), LocalDate.of(2025, 12, 25));
        Tournament tour2 = new Tournament("Giải Đua Ngựa Khai Xuân 2026", "Trường đua Đại Nam", LocalDate.of(2026, 2, 10), LocalDate.of(2026, 2, 15));
        Tournament tour3 = new Tournament("Giải Đua Ngựa Đón Hè 2026", "Trường đua Phú Thọ", LocalDate.of(2026, 5, 15), LocalDate.of(2026, 5, 20));
        Tournament tour4 = new Tournament("Giải Đua Ngựa Vượt Thu 2026", "Trường đua Sóc Sơn", LocalDate.of(2026, 8, 20), LocalDate.of(2026, 8, 25));
        Tournament tour5 = new Tournament("Giải Đua Ngựa Chinh Đông 2026", "Trường đua Đại Nam", LocalDate.of(2026, 11, 5), LocalDate.of(2026, 11, 10));
        Tournament tour6 = new Tournament("Giải Vô Địch Quốc Gia 2026", "Trường đua Phú Thọ", LocalDate.of(2026, 12, 20), LocalDate.of(2026, 12, 25));
        Tournament tour7 = new Tournament("Giải Đua Ngựa Demo Trống 2027", "Trường đua Sóc Sơn", LocalDate.of(2027, 1, 10), LocalDate.of(2027, 1, 15));

        /*___________________________________________________________________________________________________________ */
        //                                                  RACE SCHEDULE

        RaceSchedule rasc1 = new RaceSchedule(
                "Bán kết Quốc gia 2025 lần 1",
                LocalDate.of(2025, 12, 22),
                "Trường đua Phú Thọ",
                RaceScheduleStatus.COMPLETED,
                LocalDateTime.of(2025, 12, 22, 15, 0, 0),
                LocalDateTime.of(2025, 12, 22, 17, 0, 0)
        );


        RaceSchedule rasc2 = new RaceSchedule(
                "Bán kết Quốc gia 2025 lần 2",
                LocalDate.of(2025, 12, 23),
                "Trường đua Phú Thọ",
                RaceScheduleStatus.COMPLETED,
                LocalDateTime.of(2025, 12, 23, 15, 0, 0),
                LocalDateTime.of(2025, 12, 23, 17, 0, 0)
        );


        RaceSchedule rasc3 = new RaceSchedule(
                "Chung kết Quốc gia 2025",
                LocalDate.of(2025, 12, 24),
                "Trường đua Phú Thọ",
                RaceScheduleStatus.COMPLETED,
                LocalDateTime.of(2025, 12, 24, 15, 0, 0),
                LocalDateTime.of(2025, 12, 24, 17, 0, 0)
        );


        RaceSchedule rasc4 = new RaceSchedule(
                "Chung kết Xuân 2026",
                LocalDate.of(2026, 2, 14),
                "Trường đua Đại Nam",
                RaceScheduleStatus.COMPLETED,
                LocalDateTime.of(2026, 2, 14, 15, 0, 0),
                LocalDateTime.of(2026, 2, 14, 17, 0, 0)
        );


        RaceSchedule rasc5 = new RaceSchedule(
                "Chung kết Hè 2026",
                LocalDate.of(2026, 5, 19),
                "Trường đua Phú Thọ",
                RaceScheduleStatus.COMPLETED,
                LocalDateTime.of(2026, 5, 19, 15, 0, 0),
                LocalDateTime.of(2026, 5, 19, 17, 0, 0)
        );


        RaceSchedule rasc6 = new RaceSchedule(
                "Chung kết Thu 2026",
                LocalDate.of(2026, 8, 24),
                "Trường đua Sóc Sơn",
                RaceScheduleStatus.PENDING,
                LocalDateTime.of(2026, 8, 24, 15, 0, 0),
                LocalDateTime.of(2026, 8, 24, 17, 0, 0)
        );


        RaceSchedule rasc7 = new RaceSchedule(
                "Chung kết Đông 2026",
                LocalDate.of(2026, 11, 9),
                "Trường đua Đại Nam",
                RaceScheduleStatus.PENDING,
                LocalDateTime.of(2026, 11, 9, 15, 0, 0),
                LocalDateTime.of(2026, 11, 9, 17, 0, 0)
        );


        RaceSchedule rasc8 = new RaceSchedule(
                "Bán kết Quốc gia 2026 lần 1",
                LocalDate.of(2026, 12, 22),
                "Trường đua Phú Thọ",
                RaceScheduleStatus.PENDING,
                LocalDateTime.of(2026, 12, 22, 15, 0, 0),
                LocalDateTime.of(2026, 12, 22, 17, 0, 0)
        );


        RaceSchedule rasc9 = new RaceSchedule(
                "Bán kết Quốc gia 2026 lần 2",
                LocalDate.of(2026, 12, 23),
                "Trường đua Phú Thọ",
                RaceScheduleStatus.PENDING,
                LocalDateTime.of(2026, 12, 23, 15, 0, 0),
                LocalDateTime.of(2026, 12, 23, 17, 0, 0)
        );


        RaceSchedule rasc10 = new RaceSchedule(
                "Chung kết Quốc gia 2026",
                LocalDate.of(2026, 12, 24),
                "Trường đua Phú Thọ",
                RaceScheduleStatus.PENDING,
                LocalDateTime.of(2026, 12, 24, 15, 0, 0),
                LocalDateTime.of(2026, 12, 24, 17, 0, 0)
        );


        /*___________________________________________________________________________________________________________ */
        //                                                  PRIZE
        Prize p1 = new Prize("Giải Nhất", BigDecimal.valueOf(2500000000.00), 1);
        Prize p2 = new Prize("Giải Nhì", BigDecimal.valueOf(1375000000.00), 2);
        Prize p3 = new Prize("Giải Ba", BigDecimal.valueOf(937500000.00), 3);
        Prize p4 = new Prize("Giải Tư", BigDecimal.valueOf(562500000.00), 4);
        Prize p5 = new Prize("Giải Năm", BigDecimal.valueOf(375000000.00), 5);
        Prize p6 = new Prize("Giải Sáu", BigDecimal.valueOf(375000000.00), 6);
        Prize p7 = new Prize("Giải Bảy", BigDecimal.valueOf(375000000.00), 7);
        Prize p8 = new Prize("Giải Tám", BigDecimal.valueOf(375000000.00), 8);

        /*___________________________________________________________________________________________________________ */
        //                                                  HORSE
        Horse h1 = new Horse("Bão Táp", 4, "Thoroughbred", HorseHealthStatus.ELIGIBLE);
        Horse h2 = new Horse("Sấm Sét", 5, "Quarter Horse", HorseHealthStatus.ELIGIBLE);
        Horse h3 = new Horse("Xích Thố", 3, "Arabian", HorseHealthStatus.ELIGIBLE);
        Horse h4 = new Horse("Bạch Mã", 6, "Thoroughbred", HorseHealthStatus.ELIGIBLE); // Previously: Đang hồi phục
        Horse h5 = new Horse("Phi Yến", 4, "Appaloosa", HorseHealthStatus.ELIGIBLE);
        Horse h6 = new Horse("Hắc Ám", 5, "Thoroughbred", HorseHealthStatus.INJURED); // Previously: Chấn thương nhẹ
        Horse h7 = new Horse("Tia Chớp", 3, "Quarter Horse", HorseHealthStatus.ELIGIBLE);
        Horse h8 = new Horse("Đại Bàng", 7, "Arabian", HorseHealthStatus.ELIGIBLE);


        // Hash passwords
        java.util.List<Admin> admins = java.util.Arrays.asList(ad1);
        admins.forEach(u -> u.setPassword(passwordEncoder.encode(u.getPassword())));
        adminRepo.saveAll(admins);

        java.util.List<Spectator> spectators = java.util.Arrays.asList(spec1, spec2, spec3, spec4, spec5, spec6, spec7, spec8, spec9, spec10);
        spectators.forEach(u -> u.setPassword(passwordEncoder.encode(u.getPassword())));
        spectatorRepo.saveAll(spectators);

        java.util.List<HorseOwner> owners = java.util.Arrays.asList(ho1, ho2, ho3, ho4);
        owners.forEach(u -> {
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            u.setAccountStatus(com.horseracing.project3.enums.AccountStatus.APPROVED);
        });
        horseOwnerRepo.saveAll(owners);

        java.util.List<Jockey> jockeys = java.util.Arrays.asList(joc1, joc2, joc3, joc4, joc5, joc6, joc7, joc8);
        jockeys.forEach(u -> {
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            u.setAccountStatus(com.horseracing.project3.enums.AccountStatus.APPROVED);
        });
        jockeyRepo.saveAll(jockeys);

        java.util.List<RaceReferee> refs = java.util.Arrays.asList(ref1, ref2, ref3, ref4, ref5, ref6, ref7, ref8, ref9, ref10);
        refs.forEach(u -> {
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            u.setAccountStatus(com.horseracing.project3.enums.AccountStatus.APPROVED);
        });
        raceRefereeRepo.saveAll(refs);

        /*___________________________________________________________________________________________________________ */
        //                                              Gán savedHorseOwners vào Horse

        List<Horse> horses = List.of(h1, h2, h3, h4, h5, h6, h7, h8);

        List<Horse> savedHorses = new ArrayList<>();

        for (int i = 0; i < horses.size(); i++) {
            Horse currentHorse = horses.get(i);
            int ownerIndex = i / 2;
            ownerIndex = ownerIndex % owners.size();
            HorseOwner assignedOwner = owners.get(ownerIndex);
            currentHorse.setHorseOwner(assignedOwner);
            assignedOwner.addHorse(currentHorse);
            horseService.saveHorse(currentHorse);
            savedHorses.add(currentHorse);
        }

//        /*___________________________________________________________________________________________________________ */
//        //                                              Gán admin vào Tournament
//
//        List<Tournament> tournaments = List.of(tour1, tour2, tour3, tour4, tour5, tour6, tour7);
//
//        List<Tournament> savedTournaments = new ArrayList<>();
//
//        for (int i = 0; i < tournaments.size(); i++) {
//            Tournament currentTour = tournaments.get(i);
//            ad1.addTournament(currentTour);
//            tournamentService.saveTournament(currentTour);
//            savedTournaments.add(currentTour);
//        }
//
//
//        /*___________________________________________________________________________________________________________ */
//        //                                              Gán savedTournaments vào RaceSchedule
//
//        List<RaceSchedule> raceSchedules = List.of(rasc1, rasc2, rasc3, rasc4, rasc5, rasc6, rasc7, rasc8, rasc9, rasc10);
//
//        List<RaceSchedule> savedRaceSchedules = new ArrayList<>();
//
//        // tour1 (index 0) ôm rasc1, rasc2, rasc3
//        savedTournaments.get(0).addRaceSchedule(rasc1);
//        rasc1.setTournament(savedTournaments.get(0));
//        savedTournaments.get(0).addRaceSchedule(rasc2);
//        rasc2.setTournament(savedTournaments.get(0));
//        savedTournaments.get(0).addRaceSchedule(rasc3);
//        rasc3.setTournament(savedTournaments.get(0));
//
//        // tour2 (index 1) ôm rasc4
//        savedTournaments.get(1).addRaceSchedule(rasc4);
//        rasc4.setTournament(savedTournaments.get(1));
//
//        // tour3 (index 2) ôm rasc5
//        savedTournaments.get(2).addRaceSchedule(rasc5);
//        rasc5.setTournament(savedTournaments.get(2));
//
//        // tour4 (index 3) ôm rasc6
//        savedTournaments.get(3).addRaceSchedule(rasc6);
//        rasc6.setTournament(savedTournaments.get(3));
//
//        // tour5 (index 4) ôm rasc7
//        savedTournaments.get(4).addRaceSchedule(rasc7);
//        rasc7.setTournament(savedTournaments.get(4));
//
//        // tour6 (index 5) ôm rasc8, rasc9, rasc10
//        savedTournaments.get(5).addRaceSchedule(rasc8);
//        rasc8.setTournament(savedTournaments.get(5));
//        savedTournaments.get(5).addRaceSchedule(rasc9);
//        rasc9.setTournament(savedTournaments.get(5));
//        savedTournaments.get(5).addRaceSchedule(rasc10);
//        rasc10.setTournament(savedTournaments.get(5));
//
//        for (RaceSchedule raceSchedule : raceSchedules) {
//            raceScheduleService.saveRaceSchedule(raceSchedule);
//            savedRaceSchedules.add(raceSchedule);
//        }

    }
}
