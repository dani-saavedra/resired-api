package com.resired.api.guard.domain.vo;

import java.time.LocalDateTime;

public record VisitMade(Integer resident, String visitor, LocalDateTime checkin) {
}
