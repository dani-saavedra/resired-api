package com.resired.api.guard.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.guard.domain.service.CreateVisitor;
import com.resired.api.guard.domain.vo.Visit;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class Visitor {
    @JsonProperty("visitor_name")
    String visitorName;
    @JsonProperty("visitor_document")
    String visitorDocument;
    String home;
    String authorizer;
    boolean availableToEnter;

    public static Visitor createVisitor(String visitorName, String visitorDocument, String home, String authorizer, boolean availableToEnter) {
        Visitor visitor = new Visitor();
        visitor.visitorName = visitorName;
        visitor.visitorDocument = visitorDocument;
        visitor.home = home;
        visitor.authorizer = authorizer;
        visitor.availableToEnter = availableToEnter;
        return visitor;
    }

    public String createVisit(Visit residentVisit, CreateVisitor visitorService) {
        //TODO Creacion de QR para enviarlo por wp que diga "presente este QR en porteria"
        return visitorService.createVisitor(residentVisit);
    }
}
