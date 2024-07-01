package com.resired.api.resident.domain.repository;

import com.resired.api.resident.domain.vo.QrVisitor;
import com.resired.api.resident.domain.vo.RegisteredVisitor;

import java.util.Date;
import java.util.List;

public interface ResidentPort {

    Integer registerVisit(Integer userId, Integer homeId, String homeName, String name, String visitorDocument,
                          String telephone, boolean favorite, Date expirationDate);

    List<RegisteredVisitor> obtainVisitors(String emailResident);

    QrVisitor obtainQRVisitor(Integer idVisitor);

    RegisteredVisitor obtainVisitorById(Integer idVisitor);

    String reactiveVisitor(Integer idVisitor, Date expirationDate);

    void deactivateVisitor(Integer idVisitor);
}
