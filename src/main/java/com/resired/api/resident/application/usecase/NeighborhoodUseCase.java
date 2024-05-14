package com.resired.api.resident.application.usecase;

import com.resired.api.resident.application.dto.NewsResponse;
import com.resired.api.resident.domain.repository.NeighborhoodPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NeighborhoodUseCase {

    private final NeighborhoodPort port;

    public List<NewsResponse> getNewsFromNeighborhood(Long neighborhood) {
        return port.getNews(neighborhood).stream().map(newsOrm ->
            new NewsResponse(newsOrm.getId(), newsOrm.getTitle(), newsOrm.getContent(), newsOrm.getImage(),
                newsOrm.getCreatedDate(), newsOrm.getCategory())).toList();
    }
}
