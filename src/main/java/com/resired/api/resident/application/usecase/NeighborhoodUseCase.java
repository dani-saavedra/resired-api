package com.resired.api.resident.application.usecase;

import com.resired.api.resident.application.dto.NewsResponse;
import com.resired.api.resident.domain.repository.NeighborhoodPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NeighborhoodUseCase {

    private final NeighborhoodPort port;

    public NewsResponse getNewsFromNeighborhood(Long neighborhood) {
        return new NewsResponse(port.getNews(neighborhood));
    }
}
