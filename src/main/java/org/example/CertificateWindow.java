package org.example;

import com.itextpdf.text.DocumentException;

import javax.persistence.Query;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

import static org.example.NewMainWindow.service;

public class CertificateWindow {
    private final JFrame certifWin = new JFrame("Make certificate");

    public Patient patient;

    public CertificateWindow(Patient patient) {
        this.patient = patient;
    }

    private void checkString(JTextField b) throws LetterInNumber {
        String word = b.getText();
        boolean isOnlyDigits = true;
        for (int i = 0; i < word.length() && isOnlyDigits; i++) {
            if (!Character.isDigit(word.charAt(i)) && word.charAt(i) != '-' && word.charAt(i) != '.') {

                isOnlyDigits = false;
            }
        }
        if (!isOnlyDigits) throw new LetterInNumber(b.getName());

    }

    private String[] fillItems(int code) {
        String[] l1 = new String[1];
        if (code == 1) {
            List<Doctor> list = service.getAllDoctors();
            String[] l = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                l[i] = list.get(i).getName() + " " + list.get(i).getLastName();
            }

            return l;
        }

        if (code == 3) {
            List<Disease> list = service.getAllDiseases();
            String[] l = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                l[i] = list.get(i).getName();
            }
            return l;
        }
        return l1;
    }

    public void setCertifWin() throws ParseException {
        certifWin.setSize(500, 600);
        certifWin.setLocation(400, 150);
        certifWin.setLayout(new GridLayout(8, 2, 1, 1));

        JLabel patient = new JLabel("Choose patient");
        JLabel doctor = new JLabel("Choose doctor");
        JLabel disease = new JLabel("Choose diseases");
        JLabel data = new JLabel("Choose data for certificate");
        JLabel nullLabel = new JLabel("");

        String[] itemsD = fillItems(1);
        JComboBox comboBoxDoctor = new JComboBox(itemsD);

        String[] itemsDis = fillItems(3);
        JComboBox comboBoxDisease = new JComboBox(itemsDis);


        ActionListener actionListener = e -> {

        };
        comboBoxDoctor.addActionListener(actionListener);


        JLabel pat = new JLabel(this.patient.getName() + " " + this.patient.getLastName());


        // Определение маски и содание поля ввода мобильного телефона
        MaskFormatter dataFormatter = new MaskFormatter("##.##.####-##.##.####");
        dataFormatter.setPlaceholderCharacter('0');
        JFormattedTextField dataField = new JFormattedTextField(dataFormatter);
        dataField.setColumns(16);
        certifWin.add(data);
        certifWin.add(dataField);


        certifWin.add(patient);
        certifWin.add(pat);
        certifWin.add(doctor);
        certifWin.add(comboBoxDoctor);
        certifWin.add(disease);
        certifWin.add(comboBoxDisease);
        certifWin.add(nullLabel);


        JButton downloadButton = new JButton("Download certificate");
        certifWin.add(downloadButton);
        downloadButton.addActionListener(event -> {

            try {
                checkString(dataField);
                Certificate doc = new Certificate(pat.getText(),
                        comboBoxDoctor.getSelectedItem().toString(), comboBoxDisease.getSelectedItem().toString(),
                        dataField.getText());
                doc.createCertificate();

            } catch (LetterInNumber | DocumentException | ParserConfigurationException | FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        });

    }

    public void SetVisible(boolean b) {
        certifWin.setVisible(b);
    }
}
