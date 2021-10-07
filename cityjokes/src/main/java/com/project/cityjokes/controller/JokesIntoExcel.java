/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.cityjokes.controller;

import com.project.cityjokes.model.Joke;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author suzy
 */
@Component
@Slf4j
public class JokesIntoExcel {

    public byte[] buildExcelDocument(List<Joke> jokes) {
        try {
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Sheet sheet = workbook.createSheet("jokes");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("created_at");
            header.createCell(1).setCellValue("icon_url");
            header.createCell(2).setCellValue("id");
            header.createCell(3).setCellValue("updatedAt");
            header.createCell(4).setCellValue("url");
            header.createCell(5).setCellValue("value");

            int rowCount = 1;
            for (Joke joke : jokes) {
                Row courseRow = sheet.createRow(rowCount++);
                courseRow.createCell(0).setCellValue(joke.getCreatedAt());
                courseRow.createCell(1).setCellValue(joke.getIconUrl());
                courseRow.createCell(2).setCellValue(joke.getId());
                courseRow.createCell(3).setCellValue(joke.getUpdatedAt());
                courseRow.createCell(4).setCellValue(joke.getUrl());
                courseRow.createCell(5).setCellValue(joke.getValue());
            }
            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            log.info("exception");
            throw new RuntimeException();
        }
    }
}
