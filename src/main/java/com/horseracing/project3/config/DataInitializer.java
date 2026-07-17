package com.horseracing.project3.config;

import com.horseracing.project3.entity.*;
import com.horseracing.project3.enums.*;
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
import java.time.LocalTime;
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
    private RaceTrackRepo raceTrackRepo;
    @Autowired
    private RaceParticipationRepo raceParticipationRepo;
    @Autowired
    private RaceResultRepo raceResultRepo;
    @Autowired
    private RankingEntryRepo rankingEntryRepo;
    @Autowired
    private PredictionRepo predictionRepo;
    @Autowired
    private NotificationRepo notificationRepo;
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
        Spectator spec10= new Spectator("Lê Thị Lan", "lelan_smile", "lelan@gmail.com", "0912345680", "spec123", LocalDate.of(2005, 6, 30));

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
        RaceReferee ref1 = new RaceReferee("Lương Công Bằng", "BangLCB", "congbang.ref@mail.com", "0911112233", "ref123", LocalDate.of(1972, 3, 14), 4, "Quốc tế");
        RaceReferee ref2 = new RaceReferee("Trịnh Chính Trực", "TrucTCT", "chinhtruc.ref@mail.com", "0912223344", "ref123", LocalDate.of(1975, 8, 28), 8, "Quốc tế");
        RaceReferee ref3 = new RaceReferee("Tạ Khách Quan", "QuanTKQ", "khachquan.ref@mail.com", "0913334455", "ref123", LocalDate.of(1979, 11, 5), 3, "Quốc tế");
        RaceReferee ref4 = new RaceReferee("Võ Minh Bạch", "AchVMB", "minhbach.ref@mail.com", "0914445566", "ref123", LocalDate.of(1981, 1, 22), 10, "Quốc tế");
        RaceReferee ref5 = new RaceReferee("Đào Công Tâm", "AmĐCT", "congtam.ref@mail.com", "0915556677", "ref123", LocalDate.of(1984, 10, 9), 6, "Quốc tế");
        RaceReferee ref6 = new RaceReferee("Phan Trung Lập", "LapPTL", "trunglap.ref@mail.com", "0916667788", "ref123", LocalDate.of(1986, 12, 31), 5, "Quốc tế");
        RaceReferee ref7 = new RaceReferee("Bùi Đức Minh", "InhBĐM", "duicminh.ref@mail.com", "0917778899", "ref123", LocalDate.of(1988, 4, 12), 9, "Quốc tế");
        RaceReferee ref8 = new RaceReferee("Đoàn Quang Sang", "SangĐQS", "quangsang.ref@mail.com", "0918889900", "ref123", LocalDate.of(1989, 6, 3), 2, "Quốc tế");
        RaceReferee ref9 = new RaceReferee("Chu Uy Nghiêm", "IemCUN", "uynghiem.ref@mail.com", "0919990011", "ref123", LocalDate.of(1990, 9, 25), 4, "Quốc tế");
        RaceReferee ref10 = new RaceReferee("Lâm Quyết Đoán", "DoanLQĐ", "quyetdoan.ref@mail.com", "0911001122", "ref123", LocalDate.of(1991, 9, 25), 8, "Quốc tế");

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
        //                                                  TOURNAMENT
        Tournament tour1 = new Tournament("Giải Vô Địch Quốc Gia 2025", "Trường đua Phú Thọ",
                LocalDate.of(2025, 12, 20),
                LocalDate.of(2025, 12, 25)
                , TournamentStatus.COMPLETED, null,
                LocalDateTime.of(2025, 12, 10, 7, 0, 0),
                LocalDateTime.of(2025, 12, 19, 23, 0, 0), Boolean.FALSE);

        Tournament tour2 = new Tournament("Giải Đua Ngựa Khai Xuân 2026", "Trường đua Đại Nam",
                LocalDate.of(2026, 2, 10),
                LocalDate.of(2026, 2, 15)
                , TournamentStatus.COMPLETED, null,
                LocalDateTime.of(2026, 2, 1, 7, 0, 0),
                LocalDateTime.of(2026, 2, 9, 23, 0, 0), Boolean.FALSE);

        Tournament tour3 = new Tournament("Giải Đua Ngựa Đón Hè 2026", "Trường đua Phú Thọ",
                LocalDate.of(2026, 5, 15),
                LocalDate.of(2026, 5, 20)
                , TournamentStatus.COMPLETED, null,
                LocalDateTime.of(2026, 5, 5, 7, 0, 0),
                LocalDateTime.of(2026, 5, 14, 23, 0, 0), Boolean.FALSE);

        Tournament tour4 = new Tournament("Giải Đua Ngựa Vượt Thu 2026", "Trường đua Sóc Sơn",
                LocalDate.of(2026, 8, 20),
                LocalDate.of(2026, 8, 25)
                , TournamentStatus.ACTIVE, null,
                LocalDateTime.of(2026, 8, 10, 7, 0, 0),
                LocalDateTime.of(2026, 8, 19, 23, 0, 0), Boolean.FALSE);

        Tournament tour5 = new Tournament("Giải Đua Ngựa Chinh Đông 2026", "Trường đua Đại Nam",
                LocalDate.of(2026, 11, 5),
                LocalDate.of(2026, 11, 10)
                , TournamentStatus.ACTIVE, null,
                LocalDateTime.of(2026, 11, 1, 7, 0, 0),
                LocalDateTime.of(2026, 11, 4, 23, 0, 0), Boolean.TRUE);

        Tournament tour6 = new Tournament("Giải Vô Địch Quốc Gia 2026", "Trường đua Phú Thọ",
                LocalDate.of(2026, 12, 20),
                LocalDate.of(2026, 12, 25)
                , TournamentStatus.ACTIVE, null,
                LocalDateTime.of(2026, 12, 10, 7, 0, 0),
                LocalDateTime.of(2026, 12, 19, 23, 0, 0), Boolean.TRUE);

        /*___________________________________________________________________________________________________________ */
        //                                                  RaceSchedule
        RaceSchedule rs1 = new RaceSchedule("Bán kết Quốc gia 2025 lần 1",
                LocalDate.of(2025, 12, 22), "Trường đua Phú Thọ",
                null, RaceScheduleStatus.COMPLETED,
                LocalDateTime.of(2025, 12, 22, 15, 0, 0),
                LocalDateTime.of(2025, 12, 22, 17, 0, 0));
        
        RaceSchedule rs2 = new RaceSchedule("Bán kết Quốc gia 2025 lần 2",
                LocalDate.of(2025, 12, 23), "Trường đua Phú Thọ",
                null, RaceScheduleStatus.COMPLETED,
                LocalDateTime.of(2025, 12, 23, 15, 0, 0),
                LocalDateTime.of(2025, 12, 23, 17, 0, 0));
        
        RaceSchedule rs3 = new RaceSchedule("Chung kết Quốc gia 2025",
                LocalDate.of(2025, 12, 24), "Trường đua Phú Thọ",
                null, RaceScheduleStatus.COMPLETED,
                LocalDateTime.of(2025, 12, 24, 15, 0, 0),
                LocalDateTime.of(2025, 12, 24, 17, 0, 0));
        
        RaceSchedule rs4 = new RaceSchedule("Chung kết Xuân 2026",
                LocalDate.of(2026, 2, 14), "Trường đua Đại Nam",
                null, RaceScheduleStatus.COMPLETED,
                LocalDateTime.of(2026, 2, 14, 15, 0, 0),
                LocalDateTime.of(2026, 2, 14, 17, 0, 0));
        
        RaceSchedule rs5 = new RaceSchedule("Chung kết Hè 2026",
                LocalDate.of(2026, 5, 19), "Trường đua Phú Thọ",
                null, RaceScheduleStatus.COMPLETED,
                LocalDateTime.of(2026, 5, 19, 15, 0, 0),
                LocalDateTime.of(2026, 5, 19, 17, 0, 0));
        
        RaceSchedule rs6 = new RaceSchedule("Chung kết Thu 2026",
                LocalDate.of(2026, 8, 24), "Trường đua Sóc Sơn",
                null, RaceScheduleStatus.SCHEDULED,
                LocalDateTime.of(2026, 8, 24, 15, 0, 0),
                LocalDateTime.of(2026, 8, 24, 17, 0, 0));
        
        RaceSchedule rs7 = new RaceSchedule("Chung kết Đông 2026",
                LocalDate.of(2026, 11, 9), "Trường đua Đại Nam",
                null, RaceScheduleStatus.SCHEDULED,
                LocalDateTime.of(2026, 11, 9, 15, 0, 0),
                LocalDateTime.of(2026, 11, 9, 17, 0, 0));
        
        RaceSchedule rs8 = new RaceSchedule("Bán kết Quốc gia 2026 lần 1",
                LocalDate.of(2026, 12, 22), "Trường đua Phú Thọ",
                null, RaceScheduleStatus.SCHEDULED,
                LocalDateTime.of(2026, 12, 22, 15, 0, 0),
                LocalDateTime.of(2026, 12, 22, 17, 0, 0));
        
        RaceSchedule rs9 = new RaceSchedule("Bán kết Quốc gia 2026 lần 2",
                LocalDate.of(2026, 12, 23), "Trường đua Phú Thọ",
                null, RaceScheduleStatus.SCHEDULED,
                LocalDateTime.of(2026, 12, 23, 15, 0, 0),
                LocalDateTime.of(2026, 12, 23, 17, 0, 0));
        
        RaceSchedule rs10 = new RaceSchedule("Chung kết Quốc gia 2026",
                LocalDate.of(2026, 12, 24), "Trường đua Phú Thọ",
                null, RaceScheduleStatus.SCHEDULED,
                LocalDateTime.of(2026, 12, 24, 15, 0, 0),
                LocalDateTime.of(2026, 12, 24, 17, 0, 0));

        /*___________________________________________________________________________________________________________ */
        //                                                  Ticket
        Ticket t1 = new Ticket(BigDecimal.valueOf(200000),
                LocalDateTime.of(2025, 12, 16, 11, 45, 0),
                TicketStatus.USED);
        t1.setTicketCode(java.util.UUID.randomUUID().toString());
        
        Ticket t2 = new Ticket(BigDecimal.valueOf(200000),
                LocalDateTime.of(2025, 12, 17, 11, 45, 0),
                TicketStatus.USED);
        t2.setTicketCode(java.util.UUID.randomUUID().toString());
        
        Ticket t3 = new Ticket(BigDecimal.valueOf(100000),
                LocalDateTime.of(2026, 2, 1, 9, 0, 0),
                TicketStatus.USED);
        t3.setTicketCode(java.util.UUID.randomUUID().toString());
        
        Ticket t4 = new Ticket(BigDecimal.valueOf(100000),
                LocalDateTime.of(2026, 2, 2, 9, 0, 0),
                TicketStatus.CANCELLED);
        t4.setTicketCode(java.util.UUID.randomUUID().toString());
        
        Ticket t5 = new Ticket(BigDecimal.valueOf(150000),
                LocalDateTime.of(2026, 5, 12, 14, 30, 0),
                TicketStatus.USED);
        t5.setTicketCode(java.util.UUID.randomUUID().toString());
        
        /*___________________________________________________________________________________________________________ */
        //                                                  RaceParticipation
        RaceParticipation rp1 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 1);
        RaceParticipation rp2 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 2);
        RaceParticipation rp3 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 3);
        RaceParticipation rp4 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 4);

        RaceParticipation rp5 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 1);
        RaceParticipation rp6 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 2);
        RaceParticipation rp7 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 3);
        RaceParticipation rp8 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 4);

        RaceParticipation rp9 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 1);
        RaceParticipation rp10 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 2);
        RaceParticipation rp11 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 3);
        RaceParticipation rp12 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 4);

        RaceParticipation rp13 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 1);
        RaceParticipation rp14 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 2);
        RaceParticipation rp15 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 3);
        RaceParticipation rp16 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 4);

        RaceParticipation rp17 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 1);
        RaceParticipation rp18 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 2);
        RaceParticipation rp19 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 3);
        RaceParticipation rp20 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 4);

        RaceParticipation rp21 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 1);
        RaceParticipation rp22 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 2);
        RaceParticipation rp23 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 3);
        RaceParticipation rp24 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 4);

        RaceParticipation rp25 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 1);
        RaceParticipation rp26 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 2);
        RaceParticipation rp27 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 3);
        RaceParticipation rp28 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 4);

        RaceParticipation rp29 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 1);
        RaceParticipation rp30 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 2);
        RaceParticipation rp31 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 3);
        RaceParticipation rp32 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 4);

        RaceParticipation rp33 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 1);
        RaceParticipation rp34 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 2);
        RaceParticipation rp35 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 3);
        RaceParticipation rp36 = new RaceParticipation(RaceParticipationStatus.CONFIRMED, 4);

        /*___________________________________________________________________________________________________________ */
        //                                                  RaceResult
        RaceResult rr1 = new RaceResult(1, LocalTime.of(0, 4, 50, 120_000_000), RaceResultStatus.OFFICIAL);
        RaceResult rr2 = new RaceResult(2, LocalTime.of(0, 4, 50, 350_000_000), RaceResultStatus.OFFICIAL);
        RaceResult rr3 = new RaceResult(3, LocalTime.of(0, 4, 51, 140_000_000), RaceResultStatus.OFFICIAL);
        RaceResult rr4 = new RaceResult(4, LocalTime.of(0, 4, 55, 150_000_000), RaceResultStatus.OFFICIAL);

        RaceResult rr5 = new RaceResult(4, LocalTime.of(0, 5, 55, 120_000_000), RaceResultStatus.OFFICIAL);
        RaceResult rr6 = new RaceResult(3, LocalTime.of(0, 5, 51, 140_000_000), RaceResultStatus.OFFICIAL);
        RaceResult rr7 = new RaceResult(2, LocalTime.of(0, 5, 50, 350_000_000), RaceResultStatus.OFFICIAL);
        RaceResult rr8 = new RaceResult(1, LocalTime.of(0, 5, 50, 120_000_000), RaceResultStatus.OFFICIAL);

        RaceResult rr9 = new RaceResult(2, LocalTime.of(0, 6, 50, 350_000_000), RaceResultStatus.OFFICIAL);
        RaceResult rr10 = new RaceResult(3, LocalTime.of(0, 6, 51, 140_000_000), RaceResultStatus.OFFICIAL);
        RaceResult rr11 = new RaceResult(4, LocalTime.of(0, 6, 55, 120_000_000), RaceResultStatus.OFFICIAL);
        RaceResult rr12 = new RaceResult(1, LocalTime.of(0, 6, 50, 120_000_000), RaceResultStatus.OFFICIAL);

        RaceResult rr13 = new RaceResult(3, LocalTime.of(0, 5, 51, 140_000_000), RaceResultStatus.OFFICIAL);
        RaceResult rr14 = new RaceResult(4, LocalTime.of(0, 5, 55, 120_000_000), RaceResultStatus.OFFICIAL);
        RaceResult rr15 = new RaceResult(1, LocalTime.of(0, 5, 50, 120_000_000), RaceResultStatus.OFFICIAL);
        RaceResult rr16 = new RaceResult(2, LocalTime.of(0, 5, 50, 350_000_000), RaceResultStatus.OFFICIAL);

        RaceResult rr17 = new RaceResult(4, LocalTime.of(0, 6, 55, 120_000_000), RaceResultStatus.OFFICIAL);
        RaceResult rr18 = new RaceResult(1, LocalTime.of(0, 6, 50, 120_000_000), RaceResultStatus.OFFICIAL);
        RaceResult rr19 = new RaceResult(2, LocalTime.of(0, 6, 50, 350_000_000), RaceResultStatus.OFFICIAL);
        RaceResult rr20 = new RaceResult(3, LocalTime.of(0, 6, 51, 140_000_000), RaceResultStatus.OFFICIAL);

        RaceResult rr21 = new RaceResult(null, null, RaceResultStatus.UNOFFICIAL);
        RaceResult rr22 = new RaceResult(null, null, RaceResultStatus.UNOFFICIAL);
        RaceResult rr23 = new RaceResult(null, null, RaceResultStatus.UNOFFICIAL);
        RaceResult rr24 = new RaceResult(null, null, RaceResultStatus.UNOFFICIAL);

        RaceResult rr25 = new RaceResult(null, null, RaceResultStatus.UNOFFICIAL);
        RaceResult rr26 = new RaceResult(null, null, RaceResultStatus.UNOFFICIAL);
        RaceResult rr27 = new RaceResult(null, null, RaceResultStatus.UNOFFICIAL);
        RaceResult rr28 = new RaceResult(null, null, RaceResultStatus.UNOFFICIAL);

        RaceResult rr29 = new RaceResult(null, null, RaceResultStatus.UNOFFICIAL);
        RaceResult rr30 = new RaceResult(null, null, RaceResultStatus.UNOFFICIAL);
        RaceResult rr31 = new RaceResult(null, null, RaceResultStatus.UNOFFICIAL);
        RaceResult rr32 = new RaceResult(null, null, RaceResultStatus.UNOFFICIAL);

        RaceResult rr33 = new RaceResult(null, null, RaceResultStatus.UNOFFICIAL);
        RaceResult rr34 = new RaceResult(null, null, RaceResultStatus.UNOFFICIAL);
        RaceResult rr35 = new RaceResult(null, null, RaceResultStatus.UNOFFICIAL);
        RaceResult rr36 = new RaceResult(null, null, RaceResultStatus.UNOFFICIAL);
        /*___________________________________________________________________________________________________________ */
        //                                                  Prediction
        Prediction pr1 = new Prediction(1, LocalDateTime.of(2025, 12, 16, 11, 45, 0), PredictionStatus.WON);
        Prediction pr2 = new Prediction(2, LocalDateTime.of(2025, 12, 17, 11, 45, 0), PredictionStatus.LOST);
        Prediction pr3 = new Prediction(3, LocalDateTime.of(2026, 2, 1, 9, 0, 0), PredictionStatus.WON);
        Prediction pr4 = new Prediction(4, LocalDateTime.of(2026, 2, 2, 9, 0, 0), PredictionStatus.WON);
        Prediction pr5 = new Prediction(5, LocalDateTime.of(2026, 5, 12, 14, 30, 0), PredictionStatus.WON);

        /*___________________________________________________________________________________________________________ */
        //                                                  Lưu save admin
        List<Admin> admins = List.of(ad1);
        admins.forEach(u -> u.setPassword(passwordEncoder.encode(u.getPassword())));
        adminRepo.saveAll(admins);
        List<Admin> savedAdmins = adminRepo.saveAll(admins);

        /*___________________________________________________________________________________________________________ */
        //                                                  Lưu save Spectator
        List<Spectator> spectators = List.of(spec1, spec2, spec3, spec4, spec5, spec6, spec7, spec8, spec9, spec10);
        spectators.forEach(u -> u.setPassword(passwordEncoder.encode(u.getPassword())));
        spectatorRepo.saveAll(spectators);
        List<Spectator> savedSpectators = spectatorRepo.saveAll(spectators);

        /*___________________________________________________________________________________________________________ */
        //                                                  Lưu save horse owner
        List<HorseOwner> horseOwners = List.of(ho1, ho2, ho3, ho4);
        horseOwners.forEach(u -> {
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            u.setAccountStatus(AccountStatus.APPROVED);
        });
        List<HorseOwner> savedOwners = horseOwnerRepo.saveAll(horseOwners);

        /*___________________________________________________________________________________________________________ */
        //                                                  Lưu save jockey
        List<Jockey> jockeys = List.of(joc1, joc2, joc3, joc4, joc5, joc6, joc7, joc8);
        jockeys.forEach(u -> {
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            u.setAccountStatus(AccountStatus.APPROVED);
        });
        jockeyRepo.saveAll(jockeys);
        List<Jockey> savedJockeys = jockeyRepo.saveAll(jockeys);

        /*___________________________________________________________________________________________________________ */
        //                                                  Lưu save race referee
        List<RaceReferee> referees = List.of(ref1, ref2, ref3, ref4, ref5, ref6, ref7, ref8, ref9, ref10);
        referees.forEach(u -> {
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            u.setAccountStatus(AccountStatus.APPROVED);
        });
        raceRefereeRepo.saveAll(referees);
        List<RaceReferee> savedReferees = raceRefereeRepo.saveAll(referees);

        /*___________________________________________________________________________________________________________ */
        //                                              Gán savedHorseOwners vào Horse
        List<Horse> horses = List.of(h1, h2, h3, h4, h5, h6, h7, h8);
        List<Horse> savedHorses = new ArrayList<>();

        for (int i = 0; i < horses.size(); i++) {
            Horse currentHorse = horses.get(i);

            // Tính toán Index
            int ownerIndex = i / 2;
            // Sửa lại thành savedOwners.size()
            ownerIndex = ownerIndex % savedOwners.size();

            // Lấy owner từ danh sách ĐÃ ĐƯỢC LƯU VÀ CÓ ID
            HorseOwner assignedOwner = savedOwners.get(ownerIndex);

            // Thực hiện gán hai chiều
            currentHorse.setHorseOwner(assignedOwner);
            assignedOwner.addHorse(currentHorse);

            // Lưu Horse xuống database
            horseService.saveHorse(currentHorse);
            savedHorses.add(currentHorse);
        }

        /*___________________________________________________________________________________________________________ */
        //                                              Gán savedAdmins vào Tournament
        List<Tournament> tournaments = List.of(tour1, tour2, tour3, tour4, tour5, tour6);
        List<Tournament> savedTournaments = new ArrayList<>();

        for (int i = 0; i < tournaments.size(); i++) {
            Tournament currentTournament = tournaments.get(i);

            // Lấy admin từ danh sách ĐÃ ĐƯỢC LƯU VÀ CÓ ID
            int adminIndex = i % savedAdmins.size();
            Admin assignedAdmin = savedAdmins.get(adminIndex);

            // Thực hiện gán hai chiều
            currentTournament.setAdmin(assignedAdmin);
            assignedAdmin.addTournament(currentTournament);

            // Lưu Tournament xuống database
            tournamentService.saveTournament(currentTournament);
            savedTournaments.add(currentTournament);

        }

        /*___________________________________________________________________________________________________________ */
        //                                              Gán savedTournaments vào RaceSchedule
        
        List<RaceSchedule> raceSchedules = List.of(rs1, rs2, rs3, rs4, rs5, rs6, rs7, rs8, rs9, rs10);
        List<RaceSchedule> savedRaceSchedules = new ArrayList<>();
        
        int[] tournamentIndices = {0, 0, 0, 1, 2, 3, 4, 5, 5, 5};

        for (int i = 0; i < raceSchedules.size(); i++) {
            RaceSchedule currentRace = raceSchedules.get(i);
            Tournament assignedTournament = savedTournaments.get(tournamentIndices[i]);

            // Thực hiện gán hai chiều
            currentRace.setTournament(assignedTournament);
            assignedTournament.addRaceSchedule(currentRace);

            // Lưu RaceSchedule xuống database
            raceScheduleService.saveRaceSchedule(currentRace);
            savedRaceSchedules.add(currentRace);
        }

        /*___________________________________________________________________________________________________________ */
        //                                              Gán savedTournaments vào Ticket
        List<Ticket> tickets = List.of(t1, t2, t3, t4, t5);
        int[] ticketTournamentIndices = {0, 0, 1, 1, 2};

        for (int i = 0; i < tickets.size(); i++) {
            Ticket currentTicket = tickets.get(i);
            Tournament assignedTournament = savedTournaments.get(ticketTournamentIndices[i]);

            currentTicket.setTournament(assignedTournament);
            assignedTournament.addTicket(currentTicket);
        }

        /*___________________________________________________________________________________________________________ */
        //                                              Gán savedSpectators vào Ticket
        int[] ticketSpectatorIndices = {0, 1, 2, 3, 4};
        List<Ticket> savedTickets = new ArrayList<>();

        for (int i = 0; i < tickets.size(); i++) {
            Ticket currentTicket = tickets.get(i);
            Spectator assignedSpectator = savedSpectators.get(ticketSpectatorIndices[i]);

            currentTicket.setSpectator(assignedSpectator);
            assignedSpectator.addTicket(currentTicket);

            // Lưu Ticket xuống database
            ticketService.saveTicket(currentTicket);
            savedTickets.add(currentTicket);
        }

        /*___________________________________________________________________________________________________________ */
        //                                              Gán savedRaceSchedules vào RaceParticipation
        List<RaceParticipation> raceParticipations = List.of(
            rp1, rp2, rp3, rp4, rp5, rp6, rp7, rp8, rp9, rp10,
            rp11, rp12, rp13, rp14, rp15, rp16, rp17, rp18, rp19, rp20,
            rp21, rp22, rp23, rp24, rp25, rp26, rp27, rp28, rp29, rp30,
            rp31, rp32, rp33, rp34, rp35, rp36
        );
        int[] rpScheduleIndices = {
            0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8
        };
        for (int i = 0; i < raceParticipations.size(); i++) {
            RaceParticipation rp = raceParticipations.get(i);
            RaceSchedule schedule = savedRaceSchedules.get(rpScheduleIndices[i]);
            rp.setRaceSchedule(schedule);
            schedule.addRaceParticipation(rp);
        }

        /*___________________________________________________________________________________________________________ */
        //                                              Gán savedHorses vào RaceParticipation
        int[] rpHorsesIndices = {
            0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 4, 5, 0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7
        };
        for (int i = 0; i < raceParticipations.size(); i++) {
            RaceParticipation rp = raceParticipations.get(i);
            Horse horse = savedHorses.get(rpHorsesIndices[i]);
            rp.setHorse(horse);
            horse.addRaceParticipation(rp);
        }

        /*___________________________________________________________________________________________________________ */
        //                                              Gán savedJockeys vào RaceParticipation
        int[] rpJockeysIndices = {
            0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 4, 5, 0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7
        };
        for (int i = 0; i < raceParticipations.size(); i++) {
            RaceParticipation rp = raceParticipations.get(i);
            Jockey jockey = savedJockeys.get(rpJockeysIndices[i]);
            rp.setJockey(jockey);
            jockey.addRaceParticipation(rp);
        }

        // Lưu tất cả RaceParticipation xuống database
        raceParticipationRepo.saveAll(raceParticipations);

        /*___________________________________________________________________________________________________________ */
        //                                              Gán savedRaceSchedules vào RaceResult
        List<RaceResult> raceResults = List.of(
            rr1, rr2, rr3, rr4, rr5, rr6, rr7, rr8, rr9, rr10,
            rr11, rr12, rr13, rr14, rr15, rr16, rr17, rr18, rr19, rr20,
            rr21, rr22, rr23, rr24, rr25, rr26, rr27, rr28, rr29, rr30,
            rr31, rr32, rr33, rr34, rr35, rr36
        );
        int[] rrScheduleIndices = {
            0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8
        };
        for (int i = 0; i < raceResults.size(); i++) {
            RaceResult rr = raceResults.get(i);
            RaceSchedule schedule = savedRaceSchedules.get(rrScheduleIndices[i]);
            rr.setRaceSchedule(schedule);
            schedule.addRaceResult(rr);
        }

        /*___________________________________________________________________________________________________________ */
        //                                              Gán savedReferees vào RaceResult
        int[] rrRefereeIndices = {
            0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8
        };
        for (int i = 0; i < raceResults.size(); i++) {
            RaceResult rr = raceResults.get(i);
            RaceReferee referee = savedReferees.get(rrRefereeIndices[i]);
            rr.setRaceReferee(referee);
            referee.addRaceResult(rr);
        }

        /*___________________________________________________________________________________________________________ */
        //                                              Gán saveRaceParticipation vào RaceResult
        for (int i = 0; i < raceResults.size(); i++) {
            RaceResult rr = raceResults.get(i);
            RaceParticipation rp = raceParticipations.get(i);
            rr.setRaceParticipation(rp);
            rp.addRaceResult(rr);
        }

        // Lưu tất cả RaceResult xuống database
        raceResultRepo.saveAll(raceResults);

        /*___________________________________________________________________________________________________________ */
        //                                              Gán savedRaceSchedules vào Prediction
        List<Prediction> predictions = List.of(pr1, pr2, pr3, pr4, pr5);
        int[] prScheduleIndices = {2, 2, 3, 3, 4};
        for (int i = 0; i < predictions.size(); i++) {
            Prediction p = predictions.get(i);
            RaceSchedule schedule = savedRaceSchedules.get(prScheduleIndices[i]);
            p.setRaceSchedule(schedule);
            schedule.addPrediction(p);
        }

        /*___________________________________________________________________________________________________________ */
        //                                              Gán savedSpectators vào Prediction
        int[] prSpectatorIndices = {0, 1, 2, 3, 4};
        for (int i = 0; i < predictions.size(); i++) {
            Prediction p = predictions.get(i);
            Spectator spectator = savedSpectators.get(prSpectatorIndices[i]);
            p.setSpectator(spectator);
            spectator.addPrediction(p);
        }

        /*___________________________________________________________________________________________________________ */
        //                                              Gán savedHorses vào Prediction
        int[] prHorseIndices = {0, 1, 2, 3, 6};
        for (int i = 0; i < predictions.size(); i++) {
            Prediction p = predictions.get(i);
            Horse horse = savedHorses.get(prHorseIndices[i]);
            p.setHorse(horse);
            horse.addPrediction(p);
        }

        // Lưu tất cả Prediction xuống database
        predictionRepo.saveAll(predictions);
    }
}
