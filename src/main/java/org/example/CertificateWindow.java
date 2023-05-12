package org.example;

import com.itextpdf.text.DocumentException;
import org.w3c.dom.Document;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

public class CertificateWindow {
    private JFrame certifWin = new JFrame("Make certificate");


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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_persistence");
        EntityManager em = emf.createEntityManager();
        String[] l1 = new String[1];
        if (code == 1) {
            em.getTransaction().begin();
            Query queryd = em.createNativeQuery("SELECT * FROM Person where bd_type ='D'", Doctor.class);
            List<Doctor> list = queryd.getResultList();
            String[] l = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                l[i] = list.get(i).getName() + " " + list.get(i).getLastName();
            }
            return l;
        }
        if (code == 2) {
            em.getTransaction().begin();
            Query queryd = em.createNativeQuery("SELECT * FROM Person where bd_type ='P'", Patient.class);
            List<Patient> list = queryd.getResultList();
            String[] l = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                l[i] = list.get(i).getName() + " " + list.get(i).getLastName();
            }
            return l;
        }
        if (code == 3) {
            em.getTransaction().begin();
            Query queryd = em.createNativeQuery("SELECT * FROM Disease", Disease.class);
            List<Disease> list = queryd.getResultList();
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
        String doctorName;

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        };
        comboBoxDoctor.addActionListener(actionListener);


        String[] itemsP = fillItems(2);
        JComboBox comboBoxPatient = new JComboBox(itemsP);

        String[] itemsDis = fillItems(3);
        JComboBox comboBoxDisease = new JComboBox(itemsDis);


        // Определение маски и содание поля ввода мобильного телефона
        MaskFormatter dataFormatter = new MaskFormatter("##.##.####-##.##.####");
        dataFormatter.setPlaceholderCharacter('0');
        JFormattedTextField dataField = new JFormattedTextField(dataFormatter);
        dataField.setColumns(16);
        certifWin.add(data);
        certifWin.add(dataField);


        certifWin.add(patient);
        certifWin.add(comboBoxPatient);
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
                Certificate doc = new Certificate(comboBoxPatient.getSelectedItem().toString(),
                        comboBoxDoctor.getSelectedItem().toString(), comboBoxDisease.getSelectedItem().toString(),
                        dataField.getText());
                doc.createCertificate();

            } catch (LetterInNumber e) {
                throw new RuntimeException(e);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        });

    }

    public void SetVisible(boolean b) {
        certifWin.setVisible(b);
    }
}
