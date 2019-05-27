package com.jakubfilipiak.restthymeleaf.extras;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub Filipiak on 09.05.2019.
 */
public class CreatorXls<T> {

    private Class clazz;

    public CreatorXls(Class clazz) {
        this.clazz = clazz;
    }

    public void createFile(List<T> series, String path, String filename) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {

        // tworzenie obiektu reprezentującego cały plik excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(filename);

        // TODO: dodać style arkusza

        // lista kolumn (pól klasy)
        List<String> columns = new ArrayList<>();

        // teraz zaczyna sę refleksja (field z lang.reflect)
        for (Field field : clazz.getDeclaredFields()) {
            columns.add(field.getName());
        }

        // tworzenie nagłówka
        Row header = sheet.createRow(0);

        // tworzenie komórek w nagłówki (nazwy kolumn)
        for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
            Cell cell = header.createCell(columnIndex);
            cell.setCellValue(columns.get(columnIndex));
            // TODO: uzupełnić styl komórek
        }

        for (int i = 0; i < series.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);

            for (int j = 0; j < columns.size(); j++) {
                HSSFCell cell = row.createCell(j);

                Method method = series.get(i)
                        .getClass()
                        .getMethod("get" + columns.get(j)
                                .substring(0, 1)
                                .toUpperCase() + columns.get(j)
                                .substring(1));

                Object result = method.invoke(series.get(i));
                cell.setCellValue(String.valueOf(result));
            }
        }

        long millis = System.currentTimeMillis();
        String uniqueFilename = path + filename + millis + ".xls";

        workbook.write(new File(uniqueFilename));
        workbook.close();
    }
}
