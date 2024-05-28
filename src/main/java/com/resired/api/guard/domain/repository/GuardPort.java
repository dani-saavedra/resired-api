package com.resired.api.guard.domain.repository;

import com.resired.api.guard.domain.entity.Package;

public interface GuardPort {

    void registerVisit(String qr);

    String registerVisitFromGuard(Integer userId, Integer homeId, String homeName,
                                  String name, String visitorDocument, String telephone);

    void registerPackage(Package packet);
}
