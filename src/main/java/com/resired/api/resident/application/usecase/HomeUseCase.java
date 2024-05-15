package com.resired.api.resident.application.usecase;

import com.resired.api.resident.application.dto.PackagesResponse;
import com.resired.api.resident.domain.entity.Package;
import com.resired.api.resident.domain.repository.HomePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HomeUseCase {

    private final HomePort port;


    public PackagesResponse getPackages(Long homeId) {
        List<Package> packages = port.getPackages(homeId).getPackages();
        return new PackagesResponse(packages);
    }
}
