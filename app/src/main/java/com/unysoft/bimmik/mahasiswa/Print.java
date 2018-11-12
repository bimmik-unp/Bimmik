package com.unysoft.bimmik.mahasiswa;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

class Print implements PdfPCellEvent {

    private Paragraph paragraph;

    public void cellLayout(PdfPCell cell, Rectangle position,
                           PdfContentByte[] canvases) {
        PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
        canvas.setLineDash(3f, 3f);
        canvas.moveTo(position.getLeft(), position.getTop());
        canvas.lineTo(position.getRight(), position.getTop());
        canvas.moveTo(position.getLeft(), position.getBottom());
        canvas.lineTo(position.getRight(), position.getBottom());
        canvas.stroke();
    }

    private void cetakLaporan(Document document) throws DocumentException {

        paragraph = new Paragraph("Nama kampus");
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph("Email kampus");
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph("Alamat kampus");
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph("No hp kampus");
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        DottedLineSeparator separator = new DottedLineSeparator();
        separator.setPercentage(59500f / 523f);
        Chunk linebreak = new Chunk(separator);
        document.add(linebreak);

        paragraph = new Paragraph("Nama mahasiwa :");
        paragraph.setAlignment(Element.ALIGN_LEFT);
        document.add(paragraph);

        paragraph = new Paragraph("NIM :");
        paragraph.setAlignment(Element.ALIGN_LEFT);
        document.add(paragraph);

        paragraph = new Paragraph("Program Studi :");
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(paragraph);

        paragraph = new Paragraph("Semester :");
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(paragraph);

//        Report_Page app = new Report_Page();
//        float[] columnWidths = { 1.5f, 5f, 2f, 1.5f, 2f };
//        table = new PdfPTable(columnWidths);
//        table.setTotalWidth(300f);
//        table.setHorizontalAlignment(Element.ALIGN_CENTER);
//
//        cell = new PdfPCell(new Phrase("P.No"));
//        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//        cell.setCellEvent(app.new DottedCell());
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase("Item Name"));
//        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//        cell.setCellEvent(app.new DottedCell());
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase("Price"));
//        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        cell.setCellEvent(app.new DottedCell());
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase("Qty"));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setCellEvent(app.new DottedCell());
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase("Ext Price"));
//        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        cell.setCellEvent(app.new DottedCell());
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//        table.setHeaderRows(1);
    }
}