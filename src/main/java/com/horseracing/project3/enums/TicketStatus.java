package com.horseracing.project3.enums;

public enum TicketStatus {
    AVAILABLE, //có vé, nhưng chưa ai mua
    SOLD,      //đã mua vé, nhưng giải đấu chưa diễn ra
    USED,      //đã mua vé, giải đấu đã diễn ra
    CANCELLED   //đã mua vé, khách hủy vé
}
