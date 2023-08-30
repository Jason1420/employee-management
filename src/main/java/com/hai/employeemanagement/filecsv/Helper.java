package com.hai.employeemanagement.filecsv;

import com.hai.employeemanagement.dto.help.CountAttendanceDTO;
import com.hai.employeemanagement.entity.Attendance;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class Helper {
    /**
     * Export file excel
     */
    public static String[] HEADERS = {
            "date",
            "employeeId",
            "employeeName",
            "checkInTime",
            "lateMinutes",
            "checkOutTime",
            "earlyMinutes",
            "status",
            "punchHour"
    };
    public static String[] HEADERS2 = {
            "employeeID",
            "employeeName",
            "workDaysTotal"
    };
    public static String SHEET_NAME = "attendance_data";
    public static String SHEET_NAME2 = "attendance_count_data";

    // Check File Format
    public boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public ByteArrayInputStream dataToExcel(List<Attendance> list) throws IOException {
        //Create workbook
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {

            //Create sheet
            Sheet sheet = workbook.createSheet(SHEET_NAME);

            //Create header row
            Row row = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }

            // Value rows
            int rowIndex = 1;
            for (Attendance attendance : list) {
                Row dataRow = sheet.createRow(rowIndex);
                rowIndex++;
                dataRow.createCell(0).setCellValue(attendance.getDate() != null ? attendance.getDate().toString() : "");
                dataRow.createCell(1).setCellValue(attendance.getEmployeeId());
                dataRow.createCell(2).setCellValue(attendance.getEmployeeName() != null ? attendance.getEmployeeName() : "");
                dataRow.createCell(3).setCellValue(attendance.getCheckInTime() != null ? attendance.getCheckInTime().toString() : "");
                dataRow.createCell(4).setCellValue(attendance.getLateMinutes());
                dataRow.createCell(5).setCellValue(attendance.getCheckOutTime() != null ? attendance.getCheckOutTime().toString() : "");
                dataRow.createCell(6).setCellValue(attendance.getEarlyMinutes());
                dataRow.createCell(7).setCellValue(attendance.getStatus() != null ? attendance.getStatus().toString() : "");
                dataRow.createCell(8).setCellValue(attendance.getPunchHour() != null ? attendance.getPunchHour() : 0);
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("fail to import data to excel");
            return null;
        } finally {
            workbook.close();
            out.close();
        }

    }

    public ByteArrayInputStream dataCountToExcel(List<CountAttendanceDTO> list) throws IOException {
        //Create workbook
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {

            //Create sheet
            Sheet sheet = workbook.createSheet(SHEET_NAME2);

            //Create header row
            Row row = sheet.createRow(0);
            for (int i = 0; i < HEADERS2.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERS2[i]);
            }

            // Value rows
            int rowIndex = 1;
            for (CountAttendanceDTO count : list) {
                Row dataRow = sheet.createRow(rowIndex);
                rowIndex++;
                dataRow.createCell(0).setCellValue(count.getEmployeeId());
                dataRow.createCell(1).setCellValue(count.getEmployeeName() != null ? count.getEmployeeName().toString() : "");
                dataRow.createCell(2).setCellValue(count.getTotal());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("fail to import data to excel");
            return null;
        } finally {
            workbook.close();
            out.close();
        }
    }

}
