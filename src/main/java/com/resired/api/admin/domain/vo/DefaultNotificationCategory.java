package com.resired.api.admin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DefaultNotificationCategory {

    GENERAL("Hay una nueva novedad", "Ingresé el mensaje de la novedad"),
    PAQUETE("Tiene un paquete en porteria listo para ser reclamado", "Llegada de un paquete"),
    VISITA("Novedad en visitas", "Llegada de visitante");

    private final String defaultMessage;
    private final String defaultTitle;
}
