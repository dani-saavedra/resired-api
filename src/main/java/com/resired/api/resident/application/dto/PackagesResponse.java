package com.resired.api.resident.application.dto;

import com.resired.api.guard.domain.entity.Package;

import java.util.List;

public record PackagesResponse(List<Package> packages) {
}
