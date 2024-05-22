package com.resired.api.guard.domain.repository;

import com.resired.api.guard.domain.entity.Visitor;

public interface GuardPort {

    Visitor obtainInfoQR(String qr);
}
