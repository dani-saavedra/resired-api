package com.resired.api.admin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DefaultNotificationCategory {

    GENERAL("Hay una nueva novedad", "Ingresé el mensaje de la novedad"),
    PAQUETE("Novedad en paqueteria", "Ingresé el mensaje relacionadp con paqueteria"),
    VISITA("Novedad en visitas", "Ingresé el mensaje relacionado con visitas");

    private final String defaultMessage;
    private final String defaultTitle;
}
