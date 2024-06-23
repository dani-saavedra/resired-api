package com.resired.api.admin.application.port;

import com.resired.api.resident.domain.entity.Home;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface HomeExcelPort {
    List<Home> findAndGetHomes(InputStream rawExcel) throws IOException;

}
