package com.resired.api.resident.domain.repository;

import com.resired.api.resident.domain.vo.QrVisitor;
import com.resired.api.resident.domain.vo.RegisteredVisitor;

import java.util.Date;
import java.util.List;

public interface ResidentPort {

    String registerVisit(Integer userId, Integer homeId, String homeName, String name, String visitorDocument,
                         String telephone, boolean favorite, Date expirationDate);

    List<RegisteredVisitor> obtainVisitors(String emailResident);

    QrVisitor obtainVisitor(String emailResident, String documentVisitor);

    RegisteredVisitor obtainVisitorByDocumentAndEmailVisitor(String documentVisitor, String emailResident);

    String reactiveVisitor(String emailResident, String visitorDocument, Date expirationDate);

    void deactivateVisitor(Integer userId, String visitorDocument);
}
