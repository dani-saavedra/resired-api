package com.resired.api.guard.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.guard.domain.service.CreateVisitor;
import com.resired.api.guard.domain.vo.VisitVO;
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
    boolean available;

    public static Visitor createVisitor(String visitorName, String visitorDocument, String home,
                                        String authorizer, boolean availableQR, boolean visitorDeleted) {
        Visitor visitor = new Visitor();
        visitor.visitorName = visitorName;
        visitor.visitorDocument = visitorDocument;
        visitor.home = home;
        visitor.authorizer = authorizer;
        visitor.available = (availableQR && !visitorDeleted);
        return visitor;
    }

    public String createVisit(VisitVO residentVisit, CreateVisitor visitorService) {
        return visitorService.createVisitor(residentVisit);
    }
}
