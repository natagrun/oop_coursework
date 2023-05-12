package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AddObject {
    private String Name, LastName, Age, PhoneNum, Spec, Cabinet, Work_days, Work_time, BloodType;

    private final String[] doctorStringFields = {Name, LastName, PhoneNum, Spec, Work_days, Work_time};
    private final String[] doctorNumberFields = {Age, Cabinet};
    private final String[] patientStringFields = {Name, LastName, PhoneNum};
    private final String[] patientNumberFields = {Age, BloodType};
    private final String[] diseaseFields = {Name};

    private final JFrame AddObj = new JFrame("Adding");
    private final NewMainWindow mainWindow;

    public AddObject(NewMainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    private void checkString(JTextField b) throws LetterInNumber {
        String word = b.getText();
        boolean isOnlyDigits = true;
        for (int i = 0; i < word.length() && isOnlyDigits; i++) {
            if (!Character.isDigit(word.charAt(i))) {
                isOnlyDigits = false;
            }
        }
        if (!isOnlyDigits) throw new LetterInNumber(b.getName());

    }

    private void fieldsUp(JPanel panel, ArrayList<JTextField> arr, String[] fieldMassiv, int code){
        String[] names = new String[0];
        if (code == 2){names= new String[]{"Name", "LastName", "Phone number", "Specialization", "Work days", "Work time"};}
        if (code == 3){names= new String[]{"Age (number field)", "Cabinet(number field)"};}
        if (code == 5){names= new String[]{"Name", "LastName","Phone number"};}
        if (code == 6){names= new String[]{"Age (number field)", "Blood type (number field)"};}
        if (code == 8){names= new String[]{"Name"};}
        for (int i = 0; i < fieldMassiv.length; i++) {
            String f = fieldMassiv[i];
            JLabel fLabel = new JLabel("Enter " + names[i]);
            panel.add(fLabel);
            JTextField fInsert = new JTextField();
            panel.add(fInsert);
            arr.add(fInsert);
            int finalI = i;
            DocumentListener nameInsertlistener = new DocumentListener() {
                public void removeUpdate(DocumentEvent event) {
                    fieldMassiv[finalI] = fInsert.getText();
                }

                public void insertUpdate(DocumentEvent event) {
                    fieldMassiv[finalI] = fInsert.getText();
                }

                public void changedUpdate(DocumentEvent event) {
                }
            };
            fInsert.getDocument().addDocumentListener(nameInsertlistener);
        }
    }

    private JPanel fillWithFields(String[] strFields, String[] numberFields,int code) {
        ArrayList<JTextField> notCheckField = new ArrayList<>();
        ArrayList<JTextField> checkField = new ArrayList<>();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2, 3, 3));
        fieldsUp(panel,notCheckField,strFields, code+1);
        fieldsUp(panel,checkField,numberFields,code+2);


        JButton exit = new JButton("Exit");
        JButton confirm = new JButton("Confirm");

        exit.addActionListener(event -> {
            mainWindow.SetVisible(true);
            AddObj.setVisible(false);
            for (String n : numberFields) {
                n = null;
            }
            for (String s : strFields) {
                s = null;
            }
        });

        confirm.addActionListener(event -> {

            try {
                for (JTextField b : checkField) {
                    checkString(b);
                }
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_persistence");
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();
                if (strFields.length == 6) {
                    Doctor doctor = new Doctor(doctorStringFields[0], doctorStringFields[1], Integer.parseInt(doctorNumberFields[0]), doctorStringFields[2], doctorStringFields[3], Integer.parseInt(doctorNumberFields[1]), doctorStringFields[4], doctorStringFields[5]);
                    em.persist(doctor);
                }
                if (strFields.length == 3) {
                    Patient patient = new Patient(patientStringFields[0], patientStringFields[1], Integer.parseInt(patientNumberFields[0]), patientStringFields[2], Integer.parseInt(patientNumberFields[1]));
                    em.persist(patient);
                }
                if (strFields.length == 1) {
                    Disease disease = new Disease(diseaseFields[0]);
                    disease.setCount(0);
                    em.persist(disease);
                }
                em.getTransaction().commit();
                for (String n : numberFields) {
                    n = null;
                }
                for (String s : strFields) {
                    s = null;
                }
                for (JTextField b : checkField) {
                    b.setText("");
                }
                for (JTextField b : notCheckField) {
                    b.setText("");
                }
                mainWindow.updateTables();
            } catch (LetterInNumber e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        });
        panel.add(exit);
        panel.add(confirm);
        return panel;
    }

    public void setAddWindow() {
        AddObj.setSize(500, 600);
        AddObj.setLocation(400, 150);
        JTabbedPane tabsFolder = new JTabbedPane(JTabbedPane.TOP);
        AddObj.add(tabsFolder, BorderLayout.CENTER);
        JPanel doctorPanel = new JPanel();
        doctorPanel = fillWithFields(doctorStringFields, doctorNumberFields,1);
        JPanel patientPanel = new JPanel();
        patientPanel = fillWithFields(patientStringFields, patientNumberFields,4);
        JPanel diseasePanel = new JPanel();
        diseasePanel = fillWithFields(diseaseFields, new String[0],7);
        tabsFolder.addTab("Doctor", new ImageIcon("/Users/natalagrunskaa/Downloads/doctor.png"), doctorPanel, "Открыть таблицу докторов");
        tabsFolder.addTab("Patient", new ImageIcon("/Users/natalagrunskaa/Downloads/doctor.png"), patientPanel, "Открыть таблицу докторов");
        tabsFolder.addTab("Disease", new ImageIcon("/Users/natalagrunskaa/Downloads/doctor.png"), diseasePanel, "Открыть таблицу докторов");
    }

    public void setVisible(boolean flag) {
        AddObj.setVisible(flag);
    }
}

