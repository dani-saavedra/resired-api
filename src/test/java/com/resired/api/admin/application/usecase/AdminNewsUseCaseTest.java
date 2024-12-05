package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.Attachment;
import com.resired.api.admin.application.dto.CreateNewsDto;
import com.resired.api.admin.application.repository.AdminNewsPort;
import com.resired.api.admin.domain.repository.FilePort;
import com.resired.api.shared.notification.application.dto.NotificationNeighborhoodRequest;
import com.resired.api.shared.notification.application.usecase.PushAppUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminNewsUseCaseTest {

    @InjectMocks
    private AdminNewsUseCase adminNewsUseCase;

    @Mock
    private PushAppUseCase pushAppUseCase;

    @Mock
    private AdminNewsPort adminNewsPort;

    @Mock
    private FilePort fileBucket;


    @Test
    void createNewsTest() throws IOException {
        Integer neighborhoodId = 1;
        String expectedImageUrl = "http://bucket.com/image.jpg";
        String expectedDetailUrl = "http://bucket.com/detail.pdf";


        CreateNewsDto newsRequest = new CreateNewsDto("Test News", "texto", "General",
            new Attachment("image.jpg",null), new Attachment("detail.pdf",null));

        when(fileBucket.uploadFileToBucket(eq("cover_image_resired"), anyString(), any())).thenReturn(expectedImageUrl);
        when(fileBucket.uploadFileToBucket(eq("attachment_resired"), anyString(), any())).thenReturn(expectedDetailUrl);

        adminNewsUseCase.createNews(newsRequest, neighborhoodId);

        verify(fileBucket).uploadFileToBucket(eq("cover_image_resired"), contains("TestNews"), any());
        verify(fileBucket).uploadFileToBucket(eq("attachment_resired"), contains("TestNews"), any());

        verify(adminNewsPort).createNews(newsRequest, neighborhoodId, expectedImageUrl, expectedDetailUrl);

        ArgumentCaptor<NotificationNeighborhoodRequest> notificationCaptor = ArgumentCaptor.forClass(NotificationNeighborhoodRequest.class);
        verify(pushAppUseCase).notifyNeighborhood(notificationCaptor.capture());

        NotificationNeighborhoodRequest capturedNotification = notificationCaptor.getValue();
        assertEquals("¡Novedad en tu conjunto!", capturedNotification.title());
        assertEquals("Test News", capturedNotification.message());
        assertEquals(neighborhoodId, capturedNotification.neighborhoodID());
    }
}
