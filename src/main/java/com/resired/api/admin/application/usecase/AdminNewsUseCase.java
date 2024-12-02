package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.CreateNewsDto;
import com.resired.api.admin.application.repository.AdminNewsPort;
import com.resired.api.admin.domain.repository.FilePort;
import com.resired.api.shared.notification.application.dto.NotificationNeighborhoodRequest;
import com.resired.api.shared.notification.application.usecase.PushAppUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class AdminNewsUseCase {

    private final PushAppUseCase pushAppUseCase;
    private final AdminNewsPort adminNewsPort;
    private final FilePort fileBucket;

    private static final String BUCKET_IMAGES_NAME = "cover_image_resired";
    private static final String BUCKET_ATTACHMENT_NAME = "attachment_resired";

    public void createNews(CreateNewsDto newsRequest, Integer neighborhoodId) throws IOException {
        String imageUrl = null;
        String detail = null;
        if (newsRequest.image() != null) {
            String name = neighborhoodId + "-" + newsRequest.title().trim().replaceAll(" ", "") + "-" + newsRequest.image().name().trim();
            imageUrl = fileBucket.uploadFileToBucket(BUCKET_IMAGES_NAME, name, newsRequest.image().inputStream());
        }
        if (newsRequest.details() != null) {
            String name = neighborhoodId + "-" + newsRequest.title().trim().replaceAll(" ", "") + "-" + newsRequest.details().name().trim();
            detail = fileBucket.uploadFileToBucket(BUCKET_ATTACHMENT_NAME, name, newsRequest.details().inputStream());
        }
        adminNewsPort.createNews(newsRequest, neighborhoodId, imageUrl, detail);

        NotificationNeighborhoodRequest requestDTO = new NotificationNeighborhoodRequest("¡Novedad en tu conjunto!",
            newsRequest.title(), neighborhoodId);
        pushAppUseCase.notifyNeighborhood(requestDTO);
    }
}
