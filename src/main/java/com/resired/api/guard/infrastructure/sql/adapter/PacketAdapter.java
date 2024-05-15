package com.resired.api.guard.infrastructure.sql.adapter;

import com.resired.api.guard.domain.entity.Packet;
import com.resired.api.guard.domain.repository.PacketPort;
import com.resired.api.guard.infrastructure.sql.jpa.PacketJpaRepository;
import com.resired.api.guard.infrastructure.sql.orm.PacketOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class PacketAdapter implements PacketPort {
    private final PacketJpaRepository packetJpaRepository;

    @Override
    public void registerPacket(Packet packet) {
        LocalDateTime nowDate = LocalDateTime.now();

        PacketOrm packetOrm = new PacketOrm(
            "12345", // TODO: update
            packet.getHomeId(),
            packet.getReceiver(),
            packet.getTrackingNumber(),
            packet.getPackageTransporter(),
            packet.getDescription(),
            packet.getStatus(),
            nowDate,
            nowDate
        );

        packetJpaRepository.save(packetOrm);
    }

}
