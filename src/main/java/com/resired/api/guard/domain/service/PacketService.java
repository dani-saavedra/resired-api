package com.resired.api.guard.domain.service;

import org.springframework.stereotype.Service;

@Service
public class PacketService {

    public String generateTrackingNumber(String receiverName, String block, int homeNumber) {
        // TODO: Update logic to generate Tracking Number
        return block + homeNumber + receiverName.substring(0, 2);
    }
}

