package com.intranet.mailingsystem.fileconvertors;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.intranet.mailingsystem.models.User;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;


public class UserPDFExporter {
    private List<User> listUsers;

    public UserPDFExporter(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        Color color=new Color(255,178,43);

        cell.setBackgroundColor(color);

        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Contact", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Corporations", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        for (User user : listUsers) {
            table.addCell(user.getFirstName() + " " + user.getLastName());
            table.addCell(user.getEmail());
            table.addCell(String.valueOf(user.getPhone()));
            table.addCell(user.getCorporationName());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("List of Users", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}