package com.raul.spring.jpa.springjpav1.view.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.raul.spring.jpa.springjpav1.models.entity.SaleOrder;
import com.raul.spring.jpa.springjpav1.models.entity.SaleOrderLine;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Map;

@Component("saleorder/show-saleorder")
public class SaleOrderPdfView  extends AbstractPdfView {

//    /**
//     * I18N
//     */
//    @Autowired
//    private MessageSource messageSource;



    //TODO implements I18n
    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document,
                                    PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse
                                   ) throws Exception {
        MessageSourceAccessor message = getMessageSourceAccessor();

        PdfPCell cell = null;
        PdfPCell cellTotal = null;
        PdfPCell cellPartnerData = null;
        PdfPCell cellSaleOrderData = null;

        SaleOrder saleOrder = (SaleOrder) model.get("saleorder");

        PdfPTable tablePartner = new PdfPTable(1);
        PdfPTable tableSaleOrder = new PdfPTable(1);
        PdfPTable tableSaleOrderLine = new PdfPTable(4);

        tablePartner.setSpacingAfter(20);
        cellPartnerData = new PdfPCell(new Phrase(message.getMessage("text.saleorder.partner.title")));
        cellPartnerData.setBackgroundColor(new Color(0xB8DAFF));

        tablePartner.addCell(cellPartnerData);
        tablePartner.addCell(saleOrder.getPartner().getName().concat(" ").concat(saleOrder.getPartner().getSurname()));
        tablePartner.addCell(saleOrder.getPartner().getEmail());

        tableSaleOrder.setSpacingAfter(20);
        cellSaleOrderData = new PdfPCell(new Phrase("Sale order info"));
        cellSaleOrderData.setBackgroundColor(new Color(0xC3E6CB));

        tableSaleOrder.addCell(cellSaleOrderData);
        tableSaleOrder.addCell("SaleOrder Number: ".concat(String.valueOf(saleOrder.getId())));
        tableSaleOrder.addCell("Description: ".concat(saleOrder.getDescription()));
        tableSaleOrder.addCell("Created At: ".concat(String.valueOf(saleOrder.getCreateAt())));

        tableSaleOrderLine.setSpacingAfter(20);
        tableSaleOrderLine.addCell("Product");
        tableSaleOrderLine.addCell("Price");
        tableSaleOrderLine.addCell("Quantity");
        tableSaleOrderLine.addCell("Total");

        for(SaleOrderLine orderLine : saleOrder.getLines()){
            tableSaleOrderLine.addCell(orderLine.getProduct().getName());
            tableSaleOrderLine.addCell(orderLine.getProduct().getPrice().toString());
            tableSaleOrderLine.addCell(orderLine.getQuantiy().toString());
            tableSaleOrderLine.addCell(orderLine.computeTotal().toString());
        }

        cell = new PdfPCell(new Phrase("Total: "));
        cell.setBackgroundColor(new Color(0x5489B8));
        cell.setPadding(8f);
        cell.setColspan(3);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

        tableSaleOrderLine.addCell(cell);

        cellTotal = new PdfPCell(new Phrase(saleOrder.getTotal().toString()));
        cellTotal.setBackgroundColor(new Color(0x5489B8));
        cellTotal.setPadding(8f);
        cellTotal.setColspan(3);

        tableSaleOrderLine.addCell(cellTotal);
        tableSaleOrderLine.setWidths(new float[]{2.5f,1,1,1});

        document.add(tablePartner);
        document.add(tableSaleOrder);
        document.add(tableSaleOrderLine);

    }
}
