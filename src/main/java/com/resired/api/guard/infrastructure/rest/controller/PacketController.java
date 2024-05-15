package com.resired.api.guard.infrastructure.rest.controller;

import com.resired.api.guard.application.dto.RegisterPacketRequest;
import com.resired.api.guard.application.dto.RegisterPacketResponse;
import com.resired.api.guard.application.usecase.PacketUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/guard/package")
@AllArgsConstructor
public class PacketController {
    private final PacketUseCase packetService;

    @PostMapping(path = "")
    public RegisterPacketResponse registerPacket(@RequestBody RegisterPacketRequest packetRequest) {
        return packetService.registerPacket(packetRequest);
    }
}
