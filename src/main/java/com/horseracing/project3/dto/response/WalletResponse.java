package com.horseracing.project3.dto.response;

import java.math.BigDecimal;

public record WalletResponse(
        Integer spectatorId,
        String spectatorName,
        BigDecimal balance
) {
}
