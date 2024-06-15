package com.resired.api.guard.domain.service;

import com.resired.api.resident.domain.vo.QrVisitor;
import com.resired.api.resident.domain.vo.RegisteredVisitor;

import java.util.List;

public interface ManageVisitor {

    QrVisitor obtainVisitorById(Integer idVisitor);

    List<RegisteredVisitor> obtainVisitors(String emailResident);

    String allowVisitorToEnterAgain(Integer idVisitor);

    void deleteVisitor(Integer idVisitor);
}
