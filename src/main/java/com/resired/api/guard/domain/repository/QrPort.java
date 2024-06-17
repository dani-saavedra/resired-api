package com.resired.api.guard.domain.repository;

import com.resired.api.guard.domain.entity.Visitor;

public interface QrPort {

    Visitor obtainInfoQR(String qr);

    boolean isAvailableQR(String qr);

    void disableVisitorQrByIdVisitor(Integer idVisitor);
}
