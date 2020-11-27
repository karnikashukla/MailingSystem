package com.intranet.mailingsystem.fileconvertors;

import com.intranet.mailingsystem.models.Corporation;
import com.intranet.mailingsystem.models.User;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class CorporationPDFExporter {

    private List<Corporation> corporationList;

    public CorporationPDFExporter(List<Corporation> listUsers) {
        this.corporationList = listUsers;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        Color color=new Color(255,178,43);

        cell.setBackgroundColor(color);

        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("Corporation Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Corporation Domain", font));
        table.addCell(cell);;

    }

    private void writeTableData(PdfPTable table) {
        for (Corporation corporation : corporationList) {
            table.addCell(corporation.getName());
            table.addCell(corporation.getDomain());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("List of Domains", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
