package com.resired.api.guard.infrastructure.sql.adapter;

import com.resired.api.guard.domain.entity.Packet;
import com.resired.api.guard.domain.repository.PacketPort;
import com.resired.api.guard.infrastructure.sql.jpa.PacketJpaRepository;
import com.resired.api.guard.infrastructure.sql.orm.PacketOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PacketAdapter implements PacketPort {
    private PacketJpaRepository packetJpaRepository;

    @Override
    public void registerPacket(Packet packet) {
        PacketOrm packetOrm = new PacketOrm();

        packetJpaRepository.save(packetOrm);
    }
}
