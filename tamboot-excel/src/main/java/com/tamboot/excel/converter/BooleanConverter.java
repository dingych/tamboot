package com.tamboot.excel.converter;

import com.tamboot.excel.core.CellValueType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BooleanConverter implements Converter {
    private static final Set<String> TRUE_VALUES = new HashSet<String>(Arrays.asList("TRUE", "YES", "Y", "1", "是"));

    private static final Set<String> FALSE_VALUES = new HashSet<String>(Arrays.asList("FALSE", "NO", "N", "0", "否"));

    @Override
    public boolean support(Field field, CellValueType cellValueType) {
        Class<?> fieldType = field.getType();
        return Boolean.class == fieldType || boolean.class == fieldType;
    }

    @Override
    public Object fromCellContent(String cellContent, Field field, String format, boolean use1904DateWindowing) {
        Class<?> fieldType = field.getType();

        if (TRUE_VALUES.contains(cellContent.toUpperCase())) {
            return true;
        }

        if (FALSE_VALUES.contains(cellContent.toUpperCase())) {
            return false;
        }

        return Boolean.class == fieldType ? null : false;
    }

    @Override
    public void setCellContent(Workbook workbook, Cell cell, Object cellValue, String format) {
        Boolean value = (Boolean) cellValue;
        cell.setCellType(CellType.BOOLEAN);
        cell.setCellValue(value);
    }
}
