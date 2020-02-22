package com.raul.spring.jpa.springjpav1.view.xlsx;

import com.raul.spring.jpa.springjpav1.models.entity.SaleOrder;
import com.raul.spring.jpa.springjpav1.models.entity.SaleOrderLine;
import org.apache.poi.ss.usermodel.*;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component("saleorder/show-saleorder.xlsx")
public class SaleOrderXlsxView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> map,
                                      org.apache.poi.ss.usermodel.Workbook workbook,
                                      HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse) throws Exception {

        MessageSourceAccessor message = getMessageSourceAccessor();
        httpServletResponse.setHeader("Content-Disposition","attachment; filename==\"sale_order.xlsx\"");

        SaleOrder saleOrder = (SaleOrder) map.get("saleorder");
        Sheet sheet = workbook.createSheet("Sale order ".concat(saleOrder.getId().toString()));

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(message.getMessage("text.saleorder.partner.title"));

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("Partner: ".concat(saleOrder.getPartner().getName().concat(" ").concat(saleOrder.getPartner().getSurname())));

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("emails:  ".concat(saleOrder.getPartner().getEmail()));

        sheet.createRow(4).createCell(0).setCellValue(message.getMessage("text.saleorder.title"));
        sheet.createRow(5).createCell(0).setCellValue("Number: ".concat(saleOrder.getId().toString()));
        sheet.createRow(6).createCell(0).setCellValue("Description: ".concat(saleOrder.getDescription()));
        sheet.createRow(7).createCell(0).setCellValue("Observations: ".concat(saleOrder.getObserbation()));
        sheet.createRow(8).createCell(0).setCellValue("created at: ".concat(saleOrder.getCreateAt().toString()));

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerStyle.setBorderTop(BorderStyle.MEDIUM);
        headerStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerStyle.setBorderRight(BorderStyle.MEDIUM);
        headerStyle.setFillBackgroundColor(IndexedColors.ORANGE.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle bodyStyle = workbook.createCellStyle();
        bodyStyle.setBorderBottom(BorderStyle.THIN);
        bodyStyle.setBorderTop(BorderStyle.THIN);
        bodyStyle.setBorderLeft(BorderStyle.THIN);
        bodyStyle.setBorderRight(BorderStyle.THIN);
        bodyStyle.setFillBackgroundColor(IndexedColors.BLUE.index);
        bodyStyle.setFillPattern(FillPatternType.SQUARES);

        Row header = sheet.createRow(9);
        header.createCell(0).setCellValue("Product");
        header.createCell(1).setCellValue("Price");
        header.createCell(2).setCellValue("Quantity");
        header.createCell(3).setCellValue("Total");

        header.getCell(0).setCellStyle(headerStyle);
        header.getCell(1).setCellStyle(headerStyle);
        header.getCell(2).setCellStyle(headerStyle);
        header.getCell(3).setCellStyle(headerStyle);


        int rowNum = 10;

        for(SaleOrderLine saleOrderLine : saleOrder.getLines()) {
            Row rowOrderLine = sheet.createRow(rowNum++);
            cell = rowOrderLine.createCell(0);
            cell.setCellValue(saleOrderLine.getProduct().getName());
            cell.setCellStyle(bodyStyle);

            cell = rowOrderLine.createCell(1);
            cell.setCellValue(saleOrderLine.getProduct().getPrice());
            cell.setCellStyle(bodyStyle);

            cell = rowOrderLine.createCell(2);
            cell.setCellValue(saleOrderLine.getQuantiy());
            cell.setCellStyle(bodyStyle);

            cell = rowOrderLine.createCell(3);
            cell.setCellValue(saleOrderLine.computeTotal());
            cell.setCellStyle(bodyStyle);
        }

        Row rowTotal = sheet.createRow(rowNum);
        rowTotal.createCell(2).setCellValue("Total");
        rowTotal.createCell(3).setCellValue(saleOrder.getTotal());

    }
}
