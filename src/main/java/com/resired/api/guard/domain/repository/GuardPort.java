package com.resired.api.guard.domain.repository;

public interface GuardPort {

    void registerVisit(String qr);

    String registerVisitFromGuard(Integer userId, Integer homeId, String homeName, String name, String visitorDocument, String telephone);

    void registerPackage(Integer guardId, Integer homeId, String receiver, String trackingNumber, String packageTransporter, String description);
}
