package com.horseracing.project3.enums;

public enum HorseHealthStatus {
    ELIGIBLE("Khỏe mạnh/Đủ điều kiện thi đấu"),
    SUSPENDED("Bị đình chỉ"),
    INJURED("Bị chấn thương"),
    SICK("Bị ốm");
    // Bạn có thể thêm các trạng thái khác nếu cần thiết (ví dụ: RECOVERING, RETIRED...)

    private final String description;

    HorseHealthStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
