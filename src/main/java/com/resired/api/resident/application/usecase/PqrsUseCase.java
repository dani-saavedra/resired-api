package com.resired.api.resident.application.usecase;

import com.resired.api.admin.application.dto.Attachment;
import com.resired.api.admin.domain.repository.FilePort;
import com.resired.api.resident.application.dto.PqrsDetailDTO;
import com.resired.api.resident.application.dto.PqrsResponseDTO;
import com.resired.api.resident.application.dto.RegisterPqrs;
import com.resired.api.resident.application.port.PqrsPort;
import com.resired.api.resident.domain.enums.CategoryPQRS;
import com.resired.api.resident.domain.enums.StatePQRS;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class PqrsUseCase {

    public static final String PQRS_BUCKET = "pqrs_resired";
    private final PqrsPort port;
    private final FilePort fileBucket;

    public List<PqrsResponseDTO> obtainPQRSByHome(Integer homeId) {
        return port.obtainPQRSByHomeId(homeId);
    }

    public String registerPQRSByResident(RegisterPqrs registerPqrs) throws IOException {
        String url = null;
        if (registerPqrs.attachment() != null) {
            Attachment attachment = new Attachment(registerPqrs.neighbor() + "-" + registerPqrs.homeId() + "-"
                + registerPqrs.attachment().getOriginalFilename().trim().replace(" ", ""), registerPqrs.attachment().getInputStream());
            url = fileBucket.uploadFileToBucket(PQRS_BUCKET, attachment.name(), attachment.inputStream());
        }
        String ticketNumber = generateTicketNumber(registerPqrs.category(), registerPqrs.neighbor());
        port.registerPQRr(registerPqrs, ticketNumber, StatePQRS.RADICADA, url);
        return ticketNumber;
    }

    public PqrsDetailDTO obtainDetailInformationPqrs(String ticketNumber) {
        return port.obtainPqrByTicketNumber(ticketNumber);
    }

    private String generateTicketNumber(CategoryPQRS category, Integer neighborhood) {
        Integer totalPqrs = port.totalPqrByNeighborhood(neighborhood);
        String format = String.format("%06d", totalPqrs + 1);
        return category.getCode() + neighborhood + format;
    }
}
