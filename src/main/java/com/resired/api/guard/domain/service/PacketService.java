package com.resired.api.guard.domain.service;

import com.resired.api.guard.domain.entity.Packet;
import com.resired.api.guard.domain.enums.PacketStatus;
import org.springframework.stereotype.Service;

@Service
public class PacketService {
    public Packet registerPacket(String receiverName, String block, int homeNumber, String description, String packageTransporter, Long homeId) {
        String trackingNumber = generateTrackingNumber(receiverName, block, homeNumber);

        return new Packet(
            receiverName,
            trackingNumber,
            PacketStatus.TO_COLLECT,
            description,
            packageTransporter,
            homeId
        );
    }

    public String generateTrackingNumber(String receiverName, String block, int homeNumber) {
        // TODO: Update logic to generate Tracking Number
        return block + homeNumber + receiverName.substring(0, 2);
    }
}
