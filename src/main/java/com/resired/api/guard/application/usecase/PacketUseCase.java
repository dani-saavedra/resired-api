package com.resired.api.guard.application.usecase;

import com.resired.api.guard.application.dto.RegisterPacketRequest;
import com.resired.api.guard.application.dto.RegisterPacketResponse;
import com.resired.api.guard.domain.entity.Packet;
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
        Long homeId = resolveHomeId(request.block(), request.homeNumber());
        Packet packet = packetService.registerPacket(
            request.receiverName(),
            request.block(),
            request.homeNumber(),
            request.description(),
            request.packageTransporter(),
            homeId
        );
        packetPort.registerPacket(packet);
        return new RegisterPacketResponse("Package registered successfully");
    }

    private Long resolveHomeId(String block, int homeNumber) {
        // TODO: Update logic to get home id
        return 1L;
    }
}
