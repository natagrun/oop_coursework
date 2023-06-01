package org.example;
import javax.persistence.Query;
import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.example.NewMainWindow.em;

public class CardBox extends JPanel {
    String[] data;

    ArrayList<JTextArea> listAreas = new ArrayList<>();
    private final NewMainWindow newMainWindows;
    private Patient patient;

    private void deleteOrNah(int id) {
        if (!em.getTransaction().isActive()) em.getTransaction().begin();
        Person person = em.find(Person.class, id);
        JFrame youReallyWantIt = new JFrame("Commit delete");
        youReallyWantIt.setSize(300, 100);
        youReallyWantIt.setLocation(500, 250);
        youReallyWantIt.setLayout(new BorderLayout());

        String text = "Do you really want to delete " + person.getName() + " " + person.getLastName();
        JLabel question = new JLabel();
        question.setText(text);
        JPanel buttonpanel = new JPanel();
        JButton yes = new JButton("YES");
        JButton no = new JButton("NO");
        buttonpanel.add(yes);
        buttonpanel.add(no);

        youReallyWantIt.add(question, BorderLayout.NORTH);
        youReallyWantIt.add(buttonpanel, BorderLayout.CENTER);


        yes.addActionListener(event -> {
            youReallyWantIt.setVisible(false);
            deleteCard();
            newMainWindows.updateTables();
        });

        no.addActionListener(event -> youReallyWantIt.setVisible(false));
        youReallyWantIt.setVisible(true);
    }

    private void deleteCard() {
        if (!em.getTransaction().isActive()) em.getTransaction().begin();
        Person person = em.find(Person.class, Integer.parseInt(data[0]));
        Query queryd = em.createNativeQuery("DELETE FROM doctor_to_patient where doctor_id =?1");
        queryd.setParameter(1, person.id);
        queryd.executeUpdate();
        queryd = em.createNativeQuery("DELETE FROM doctor_to_patient where patient_id =?1");
        queryd.setParameter(1, person.id);
        queryd.executeUpdate();
        queryd = em.createNativeQuery("DELETE FROM patient_to_disease where patient_id =?1");
        queryd.setParameter(1, person.id);
        queryd.executeUpdate();
        queryd = em.createNativeQuery("DELETE FROM Person where id =?1");
        queryd.setParameter(1, person.id);
        queryd.executeUpdate();
        em.getTransaction().commit();


    }

    public CardBox(String[] columns, NewMainWindow newMainWindow) {
        newMainWindows = newMainWindow;
        this.setLayout(new GridLayout(columns.length, 1, 0, 0));

        JTextArea nameArea = new JTextArea();
        listAreas.add(nameArea);
        JTextArea lastnameArea = new JTextArea();
        listAreas.add(lastnameArea);

        nameArea.setEditable(false);
        lastnameArea.setEditable(false);
        nameArea.setFont(new Font("Dialog", Font.PLAIN, 16));
        lastnameArea.setFont(new Font("Dialog", Font.PLAIN, 16));

        this.add(nameArea);
        this.add(lastnameArea);


        for (int i = 3; i < columns.length; i++) {
            JTextArea area1 = new JTextArea();
            listAreas.add(area1);
            area1.setLineWrap(true);
            area1.setEditable(false);
            area1.setWrapStyleWord(true);
            JTextArea label2 = new JTextArea();
            label2.setText(columns[i] + ": " + " ");
            this.add(label2);
            this.add(area1);
        }
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 1, 1));
        JButton editButton = new JButton("Edit");
        editButton.setToolTipText("Edit card");
        buttonPanel.add(editButton);

        editButton.addActionListener(event -> {
            try {
                int code = 17;
                if (columns.length != 6) {
                    code = 1;
                }
                newMainWindow.openEditWin(Integer.parseInt(data[0]), code);

            } catch (NullPointerException n) {
                System.out.println();
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.setToolTipText("Delete card");
        buttonPanel.add(deleteButton);

        deleteButton.addActionListener(event -> {

            try {
                deleteOrNah(Integer.parseInt(data[0]));
            } catch (NullPointerException n) {
                System.out.println();
            }
        });

        JButton connectionButton = new JButton("Make connection");
        buttonPanel.add(connectionButton);

        connectionButton.addActionListener(event -> {

            try {
                if (!em.getTransaction().isActive()) em.getTransaction().begin();
                Doctor doctor = em.find(Doctor.class, Integer.parseInt(data[0]));
                Patient patient = em.find(Patient.class, Integer.parseInt(data[0]));
                if (patient == null) {
                    DocConnectionWindow docConnectionWindow = new DocConnectionWindow(newMainWindow, doctor);
                    docConnectionWindow.setWin();
                    docConnectionWindow.SetVisible(true);
                } else {
                    PatientConnectionWindow patientConnectionWindow = new PatientConnectionWindow(newMainWindow, patient);
                    patientConnectionWindow.setWin();
                    patientConnectionWindow.SetVisible(true);
                }

                em.getTransaction().commit();
            } catch (NullPointerException n) {
                System.out.println();
            }
        });

        if (columns.length == 6) {
            if (!em.getTransaction().isActive()) em.getTransaction().begin();

            JButton certificateButton = new JButton("Make certificate");
            buttonPanel.add(certificateButton);

            certificateButton.addActionListener(event -> {

                try {
                    System.out.println(Arrays.toString(data));
                    patient = em.find(Patient.class, Integer.parseInt(data[0]));
                    System.out.println(patient);
                    CertificateWindow c = new CertificateWindow(patient);
                    c.setCertifWin();
                    c.SetVisible(true);
                } catch (NullPointerException | ParseException ignored) {
                }

            });
        }


        this.add(buttonPanel);
        this.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        this.setPreferredSize(new Dimension(300, 500));
    }

    public void updateCard() {
        if (data != null) {
            for (int i = 0; i < this.data.length; i++) {
                JTextArea l = listAreas.get(i);
                l.setText(data[i]);
            }
        }
    }
}

