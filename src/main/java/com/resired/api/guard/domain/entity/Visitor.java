package com.resired.api.guard.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Visitor {
    @JsonProperty("visitor_name")
    private String visitorName;
    @JsonProperty("visitor_document")
    private String visitorDocument;
    private final String home;
    private boolean available;
    private String visitorQr;

    public Visitor(String visitorName, String visitorDocument, String home, boolean available) {
        this.visitorName = visitorName;
        this.visitorDocument = visitorDocument;
        this.home = home;
        this.available = available;
    }

    public Visitor(String visitorName, String visitorDocument, String home, String visitorQr) {
        this.visitorName = visitorName;
        this.visitorDocument = visitorDocument;
        this.home = home;
        this.visitorQr = visitorQr;
    }

    public static Visitor createVisitor(String visitorName, String visitorDocument, String home,
                                        boolean availableQR, boolean favorite, boolean visitorDeleted) {
        boolean available = (availableQR || favorite) && !visitorDeleted;
        return new Visitor(visitorName, visitorDocument, home, available);
    }

}
