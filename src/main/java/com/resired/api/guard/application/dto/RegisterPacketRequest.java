package com.resired.api.guard.application.dto;

public record RegisterPacketRequest(String receiverName, String block, int homeNumber, String description, String packageTransporter) {
}
