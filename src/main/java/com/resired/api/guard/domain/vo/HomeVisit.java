package com.resired.api.guard.domain.vo;

public record HomeVisit(String nameVisitor, String documentVisitor, String telephoneVisitor,
                        Integer homeAuthorizer, String guardAuthorizer) {
}
