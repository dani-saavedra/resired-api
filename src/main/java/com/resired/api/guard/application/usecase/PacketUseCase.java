package com.resired.api.guard.application.usecase;

import com.resired.api.guard.application.dto.RegisterPacketRequest;
import com.resired.api.guard.application.dto.RegisterPacketResponse;
import com.resired.api.guard.domain.entity.Packet;
import com.resired.api.guard.domain.entity.enums.PacketStatus;
import com.resired.api.guard.domain.repository.PacketPort;
import com.resired.api.guard.domain.service.PacketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PacketUseCase {
    private final PacketService packetService;
    private final PacketPort packetPort;

    public RegisterPacketResponse registerPacket(RegisterPacketRequest request) {
        String trackingNumber = packetService.generateTrackingNumber(request.receiverName(), request.block(), request.homeNumber());

        Packet packet = new Packet();
        packet.setReceiver(request.receiverName());
        packet.setTrackingNumber(trackingNumber);
        packet.setStatus(PacketStatus.TO_COLLECT);
        packet.setCollectedDate(LocalDateTime.now());
        packet.setDescription(request.description());

        packetPort.registerPacket(packet);

        return new RegisterPacketResponse("Package registered successfully");
    }
}
