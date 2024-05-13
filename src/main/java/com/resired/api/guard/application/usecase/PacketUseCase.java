package com.resired.api.guard.application.usecase;

import com.resired.api.guard.application.dto.RegisterPacketRequest;
import com.resired.api.guard.application.dto.RegisterPacketResponse;
import com.resired.api.guard.domain.entity.Packet;
import com.resired.api.guard.domain.enums.PacketStatus;
import com.resired.api.guard.domain.repository.PacketPort;
import com.resired.api.guard.domain.service.PacketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PacketUseCase {
    private final PacketService packetService;
    private final PacketPort packetPort;

    public RegisterPacketResponse registerPacket(RegisterPacketRequest request) {
        String trackingNumber = packetService.generateTrackingNumber(request.receiverName(), request.block(), request.homeNumber());

        Packet packet = new Packet(
            request.receiverName(),
            trackingNumber,
            PacketStatus.TO_COLLECT,
            request.description()
        );

        packetPort.registerPacket(packet);

        return new RegisterPacketResponse("Package registered successfully");
    }
}
