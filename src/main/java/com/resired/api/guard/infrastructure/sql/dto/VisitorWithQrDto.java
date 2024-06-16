package com.resired.api.guard.infrastructure.sql.dto;

import com.resired.api.resident.infraestructure.sql.orm.QrOrm;
import com.resired.api.resident.infraestructure.sql.orm.VisitorOrm;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VisitorWithQrDto {
    private VisitorOrm visitor;
    private QrOrm qr;
}
