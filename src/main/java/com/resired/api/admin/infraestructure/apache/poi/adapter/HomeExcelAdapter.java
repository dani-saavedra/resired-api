package com.resired.api.admin.infraestructure.apache.poi.adapter;

import com.resired.api.admin.application.port.HomeExcelPort;
import com.resired.api.admin.domain.vo.GroupingType;
import com.resired.api.resident.domain.entity.Home;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeExcelAdapter implements HomeExcelPort {
    @Override
    public List<Home> findAndGetHomes(InputStream rawExcel) throws IOException {
        List<Home> homes = new ArrayList<>();
        int startRowHomes = 6;
        String cellAddressGroupingType = "E3";

        Workbook workbook = new XSSFWorkbook(rawExcel);

        Sheet sheet = workbook.getSheetAt(0);

        Cell groupingTypeCell = getCellByAddress(sheet, cellAddressGroupingType);

        try {
            GroupingType blockType = GroupingType.valueOf(groupingTypeCell.getStringCellValue().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }

        for (Row row : sheet) {
            if (row.getRowNum() < startRowHomes) {
                continue; // skip rows before the startRow
            }

            int homeNumber = (int) row.getCell(2).getNumericCellValue();
            String nameGrouping = row.getCell(3).getStringCellValue();
            double squareMeters = row.getCell(4).getNumericCellValue();
        }
    }

    private Cell getCellByAddress(Sheet sheet, String cellAddress) {
        int rowNumber = Integer.parseInt(cellAddress.replaceAll("[^0-9]", "")) - 1;
        int columnNumber = CellReference.convertColStringToIndex(cellAddress.replaceAll("[^A-Z]", ""));
        return sheet.getRow(rowNumber).getCell(columnNumber);
    }
}
