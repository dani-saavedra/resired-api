package com.resired.api.resident.infraestructure.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseData<T> {
    T data;

    public ResponseData(T data) {
        this.data = data;
    }


}
