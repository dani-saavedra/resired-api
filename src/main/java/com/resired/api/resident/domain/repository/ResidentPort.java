package com.resired.api.resident.domain.repository;

import com.resired.api.resident.domain.vo.RegisteredVisitor;

import java.util.List;

public interface ResidentPort {

    String registerVisit(Integer userId, Integer homeId, String homeName, String name, String visitorDocument, String telephone);

    List<RegisteredVisitor> obtainVisitors(String emailResident);
}
