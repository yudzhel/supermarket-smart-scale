package com.smartscale.util;


import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DottedBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetReceipt {


    public static void generate(Button btnChosenProduct, TextField txtKg, Label labelDollarKg, Label labelTotal){

        String fileName = "receipt_" + getTimeAndDateForFileName();

        try{
            Document document = new Document(new PdfDocument(new PdfWriter("src\\main\\resources\\com\\smartscale\\receipts\\" +fileName+".pdf")), PageSize.A6);


            String FONT_FILENAME = "src/main/resources/com/smartscale/fonts/LucidaConsoleRegular.ttf";
            PdfFont font = PdfFontFactory.createFont(FONT_FILENAME);

            document.setFont(font);
            document.setMargins(10,10,0,10);
            addParagraphs(document,btnChosenProduct.getText(), txtKg.getText(),labelDollarKg.getText(),labelTotal.getText());
            document.close();
            ShowMessage.displayInformationDialog("File \""+ fileName + "\" was created successfully!");
        }
        catch (Exception e){
            ShowMessage.displayErrorDialog(e.getMessage());
        }


    }

    private static void addParagraphs(Document document, String itemName, String kg, String dollarPerKg, String total){
        String paraReceipt= "RECEIPT";
        String paraSupermarketName = "SUPERMARKET";
        String nextLine = "";
        String paraAddress = "Address: Lopen Inpun St 21";
        String paraPhone = "Tel: 0929 3244 4552";
        String paraSeperator = "---------------------------------------------------";
        String paraDate = getTimeAndDateForDocument();
        String thankYou = "THANK YOU FOR CHOOSING US!";

        String[] paragraphs = {paraReceipt, paraSupermarketName, nextLine, paraAddress,paraPhone, nextLine, paraSeperator, paraDate,
        nextLine,nextLine};

        for(String paraText: paragraphs){
            Paragraph paragraph = new Paragraph(paraText);
            paragraph.setTextAlignment(TextAlignment.CENTER);
            paragraph.setFixedLeading(4);
            document.setFontSize(9);
            document.add(paragraph);
        }

        addTable(document, itemName, kg, dollarPerKg);
        Paragraph lineBreak = new Paragraph(nextLine);
        document.add(lineBreak);
        document.add(lineBreak);
        document.add(lineBreak);

        Paragraph paraTotal = new Paragraph("Total: $" + total);
        paraTotal.setFontSize(12);
        paraTotal.setBold();
        paraTotal.setTextAlignment(TextAlignment.RIGHT);
        document.add(paraTotal);

        for(int i=0; i<5; i++){
            document.add(lineBreak);
        }

        Paragraph paraThankYou = new Paragraph(thankYou);
        paraThankYou.setFontSize(10);
        paraThankYou.setTextAlignment(TextAlignment.CENTER);

        document.add(paraThankYou);


    }

    private static String getTimeAndDateForFileName(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hhmmss_a_ddMMyyyy");
        return LocalDateTime.now().format(formatter);
    }

    private static String getTimeAndDateForDocument(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a   dd.MM.yyyy");
        return LocalDateTime.now().format(formatter);
    }

    private static void addTable(Document document, String itemName, String kg, String dollarPerKg) {
        Table table = new Table(3);
        table.setWidth(275F);

        Cell cellItemName = new Cell();
        styleCell(cellItemName, "ITEM");

        Cell cellKg = new Cell();
        styleCell(cellKg, "KG");

        Cell cellDollarPerKg = new Cell();
        styleCell(cellDollarPerKg, "$/KG");

        Cell cellItemNameText = new Cell();
        styleCell(cellItemNameText, itemName);

        Cell cellKgText = new Cell();
        styleCell(cellKgText, kg);

        Cell cellIDollarPerKgText = new Cell();
        styleCell(cellIDollarPerKgText, dollarPerKg);

        table.addCell(cellItemName);
        table.addCell(cellKg);
        table.addCell(cellDollarPerKg);
        table.addCell(cellItemNameText);
        table.addCell(cellKgText);
        table.addCell(cellIDollarPerKgText);

        document.add(table);
    }

    private static void styleCell(Cell cell, String cellText) {
        cell.add(cellText);
        cell.setTextAlignment(TextAlignment.LEFT);
        cell.setFontSize(8);
        cell.setCharacterSpacing(0.5F);
        cell.setBorder(Border.NO_BORDER);
        cell.setBorderBottom(new DottedBorder(1));
    }
}
