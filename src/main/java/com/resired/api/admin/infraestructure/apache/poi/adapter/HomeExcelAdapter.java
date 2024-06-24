package com.resired.api.admin.infraestructure.apache.poi.adapter;

import com.resired.api.admin.application.exception.InvalidHomesTemplateException;
import com.resired.api.admin.application.port.HomeExcelPort;
import com.resired.api.admin.domain.vo.GroupingType;
import com.resired.api.resident.domain.entity.Home;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class HomeExcelAdapter implements HomeExcelPort {
    @Override
    public List<Home> findAndGetHomes(InputStream rawExcel, Integer neighborhood) throws IOException {
        List<Home> homes = new ArrayList<>();
        int startRowHomes = 5;
        String cellAddressGroupingType = "E3";
        Workbook workbook = new XSSFWorkbook(rawExcel);

        Sheet sheet = workbook.getSheetAt(0);

        if (!isTemplateValid(sheet)) throw new InvalidHomesTemplateException("HOME02");

        Cell groupingTypeCell = getCellByAddress(sheet, cellAddressGroupingType);
        GroupingType blockType = getGroupingTypeByCell(groupingTypeCell);

        for (Row row : sheet) {
            if (row.getRowNum() < startRowHomes) {
                continue;
            }

            DataFormatter formatter = new DataFormatter();
            Cell homeNumberCell = row.getCell(2);
            String homeNumber = formatter.formatCellValue(homeNumberCell);

            if (homeNumber == null || homeNumber.trim().isEmpty()) {
                break;
            }

            String nameGrouping = row.getCell(3).getStringCellValue();
            Double squareMeters = row.getCell(4).getNumericCellValue();

            Home home = new Home(homeNumber, nameGrouping, blockType, neighborhood, squareMeters);
            homes.add(home);
        }

        if (homes.isEmpty()) throw new InvalidHomesTemplateException("HOME03");

        return homes;
    }

    private Cell getCellByAddress(Sheet sheet, String cellAddress) {
        int rowNumber = Integer.parseInt(cellAddress.replaceAll("[^0-9]", "")) - 1;
        int columnNumber = CellReference.convertColStringToIndex(cellAddress.replaceAll("[^A-Z]", ""));
        return sheet.getRow(rowNumber).getCell(columnNumber);
    }

    private boolean checkIsNotValidExpectedCell(Cell cell, String expectedValue) {
        return cell == null || !cell.getStringCellValue()
            .equalsIgnoreCase(expectedValue);
    }

    private boolean isTemplateValid(Sheet sheet) {
        String expectedGroupingTypeHeader = "Seleccionar tipo de agrupación residencial:";
        String[] expectedRowHeaders = {"Número de casa", "Agrupación", "Metros cuadrados"};
        String groupingTypeHeaderCellAddress = "C3";
        int rowHeaders = 4;
        int initialColumnHeader = 2;

        Cell groupingType = getCellByAddress(sheet, groupingTypeHeaderCellAddress);
        if (checkIsNotValidExpectedCell(groupingType, expectedGroupingTypeHeader)) {
            return false;
        }

        Row headerRow = sheet.getRow(rowHeaders);
        if (headerRow == null) return false;

        for (int i = 0; i < expectedRowHeaders.length; i++) {
            Cell cell = headerRow.getCell(initialColumnHeader + i);
            if (checkIsNotValidExpectedCell(cell, expectedRowHeaders[i])) {
                return false;
            }
        }

        return true;
    }

    private GroupingType getGroupingTypeByCell(Cell groupingTypeCell) {
        String groupingTypeText = groupingTypeCell.getStringCellValue();
        for (GroupingType groupingType : GroupingType.values()) {
            if (groupingType.name().equalsIgnoreCase(groupingTypeText)) {
                return groupingType;
            }
        }

        throw new InvalidHomesTemplateException("HOME04");
    }
}
