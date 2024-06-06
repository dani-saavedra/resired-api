package com.resired.api.guard.domain.service;

import com.resired.api.resident.domain.vo.QrVisitor;
import com.resired.api.resident.domain.vo.RegisteredVisitor;

import java.util.List;

public interface ManageVisitor {

    QrVisitor obtainVisitorByDocument(String emailResident, String documentVisitor);

    List<RegisteredVisitor> obtainVisitors(String emailResident);

    String allowVisitorToEnterAgain(String emailResident, String documentVisitor);

    void deleteVisitor(Integer userId, String documentVisitor);
}
