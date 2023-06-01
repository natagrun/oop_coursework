package org.example;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Certificate extends Document {
    String patientName;
    String doctorName;
    String disease;
    String data;

    public Certificate(String patientName, String doctorName, String disease, String data) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.disease = disease;
        this.data = data;
    }
    public void createCertificate() throws ParserConfigurationException, DocumentException, FileNotFoundException {
        String documentName = patientName+" Certificate.pdf";
        PdfWriter.getInstance(this, new FileOutputStream(documentName));
        this.open();
        String para = "CERTIFICATE";
        ArrayList<Paragraph> parrs = new ArrayList<>();
        Paragraph paragraph = new Paragraph (para);
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.UNDERLINE, BaseColor.CYAN);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setFont(f);
        this.add(paragraph);
        String namelast = "Name and Surname: "+ this.patientName;
        Paragraph paragraphName = new Paragraph (namelast);
        parrs.add(paragraphName);


        String dises = "Disease: "+this.disease;
        Paragraph paragraphdises = new Paragraph (dises);
        parrs.add(paragraphdises);

        String docs = "Doctor: "+this.doctorName;
        Paragraph paragraphdocs = new Paragraph (docs);
        parrs.add(paragraphdocs);


        String dat = "Data: "+this.data;
        Paragraph paragraphdat = new Paragraph (dat);
        parrs.add(paragraphdat);
        Font fonty = new Font(Font.FontFamily.HELVETICA, 20.0f, Font.UNDERLINE, BaseColor.ORANGE);
        for(Paragraph p:parrs) {
            p.setAlignment(Element.ALIGN_LEFT);
            p.setFont(fonty);
            this.add(p);
        }
        this.close();
    }}
