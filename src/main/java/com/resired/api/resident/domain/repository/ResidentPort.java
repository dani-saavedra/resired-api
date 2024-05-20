package com.resired.api.resident.domain.repository;

public interface ResidentPort {

    String registerVisit(Integer userId, Integer homeId, String homeName, String name, String visitorDocument, String telephone);
}
