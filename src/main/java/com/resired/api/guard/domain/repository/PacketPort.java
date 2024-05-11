package com.resired.api.guard.domain.repository;

import com.resired.api.guard.domain.entity.Packet;

public interface PacketPort {
    void registerPacket(Packet packet);
}
