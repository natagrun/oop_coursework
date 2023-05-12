package org.example;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
        PdfWriter.getInstance(this, new FileOutputStream("NewLIFEJKFKKKE.pdf"));
        this.open();
        String para = "CERTIFICATE";
        ArrayList<Paragraph> parrs = new ArrayList<>();
        Paragraph paragraph = new Paragraph (para);
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.UNDERLINE, BaseColor.CYAN);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setFont(f);
        this.add(paragraph);
        String namelast = "Name and Surname: "+this.patientName;;
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
