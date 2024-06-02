package com.resired.api.guard.domain.vo;

public record VisitVO(String nameVisitor, String documentVisitor, String telephoneVisitor,
                      Integer homeAuthorizer, String authorizer, boolean favorite) {
}
