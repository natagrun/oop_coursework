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

public class EditWindow {
    private JFrame editWindow = new JFrame("Editing..");

    private int id;
    private final NewMainWindow mainWindow;

    public EditWindow(NewMainWindow mainWindow, int id) {
        this.mainWindow = mainWindow;
        this.id = id;
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

    public void setEditWindowDoc(int code) {
        editWindow.setSize(500, 600);
        editWindow.setLocation(400, 150);
        editWindow.setLayout(new GridLayout(9, 2, 1, 1));
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_persistence");
        EntityManager em = emf.createEntityManager();

        if(code==1){

        Doctor doctor = em.find(Doctor.class, id);
        JLabel nameBar = new JLabel("Name");
        JTextField namefield = new JTextField(doctor.getName());
        editWindow.add(nameBar);
        editWindow.add(namefield);
        JLabel lnameBar = new JLabel("Last name");
        JTextField lnamefield = new JTextField(doctor.getLastName());
        editWindow.add(lnameBar);
        editWindow.add(lnamefield);

        JLabel ageBar = new JLabel("Age");
        JTextField ageField = new JTextField(String.valueOf(doctor.getAge()));
        editWindow.add(ageBar);
        editWindow.add(ageField);

        JLabel phoneBar = new JLabel("Phone number");
        JTextField phoneField = new JTextField(doctor.getPhoneNumber());
        editWindow.add(phoneBar);
        editWindow.add(phoneField);

        JLabel numberBar = new JLabel();
        JTextField numberField = new JTextField();

        JLabel specBar = new JLabel("Specialization");
        JTextField specfield = new JTextField(doctor.getSpecialization());
        editWindow.add(specBar);
        editWindow.add(specfield);

        JLabel wdBar = new JLabel("Work days");
        JTextField wdfield = new JTextField(doctor.getWork_days());
        editWindow.add(wdBar);
        editWindow.add(wdfield);


        JLabel wtBAr = new JLabel("Work time");
        JTextField wtfield = new JTextField(doctor.getWork_time());
        editWindow.add(wtBAr);
        editWindow.add(wtfield);


        numberBar.setText("Cabinet");
        numberField.setText(String.valueOf(doctor.getCabinet()));
        editWindow.add(numberBar);
        editWindow.add(numberField);


        JButton exit = new JButton("Exit");
        JButton confirm = new JButton("Confirm");

        exit.addActionListener(event -> {
            mainWindow.SetVisible(true);
            editWindow.setVisible(false);
        });

        confirm.addActionListener(event -> {

            try {
                em.getTransaction().begin();
                checkString(ageField);
                checkString(numberField);
                doctor.setName(namefield.getText());
                doctor.setLastName(lnamefield.getText());
                doctor.setAge(Integer.parseInt(ageField.getText()));
                doctor.setPhoneNumber(phoneField.getText());
                doctor.setSpecialization(specfield.getText());
                doctor.setWork_days(wdfield.getText());
                doctor.setWork_time(wtfield.getText());
                doctor.setCabinet(Integer.parseInt(numberField.getText()));
                mainWindow.updateTables();
                em.merge(doctor);
                em.getTransaction().commit();
                mainWindow.updateTables();
            } catch (LetterInNumber e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        });

        editWindow.add(exit);
        editWindow.add(confirm);

    }else{
        Patient patient = em.find(Patient.class, id);
        JLabel nameBar = new JLabel("Name");
        JTextField namefield = new JTextField(patient.getName());
        editWindow.add(nameBar);
        editWindow.add(namefield);

        JLabel lnameBar = new JLabel("Last name");
        JTextField lnamefield = new JTextField(patient.getLastName());
        editWindow.add(lnameBar);
        editWindow.add(lnamefield);

        JLabel ageBar = new JLabel("Age");
        JTextField ageField = new JTextField(String.valueOf(patient.getAge()));
        editWindow.add(ageBar);
        editWindow.add(ageField);

        JLabel phoneBar = new JLabel("Phone number");
        JTextField phoneField = new JTextField(patient.getPhoneNumber());
        editWindow.add(phoneBar);
        editWindow.add(phoneField);

        JLabel numberBar = new JLabel();
        JTextField numberField = new JTextField();

        numberBar.setText("Blood type");
        numberField.setText(String.valueOf(patient.getBloodType()));
        editWindow.add(numberBar);
        editWindow.add(numberField);


        JButton exit = new JButton("Exit");
        JButton confirm = new JButton("Confirm");

        exit.addActionListener(event -> {
            mainWindow.SetVisible(true);
            editWindow.setVisible(false);
        });

        confirm.addActionListener(event -> {

            try {
                em.getTransaction().begin();
                checkString(ageField);
                checkString(numberField);
                patient.setName(namefield.getText());
                patient.setLastName(lnamefield.getText());
                patient.setAge(Integer.parseInt(ageField.getText()));
                patient.setPhoneNumber(phoneField.getText());
                patient.setBloodType(Integer.parseInt(numberField.getText()));
                mainWindow.updateTables();
                em.merge(patient);
            } catch (LetterInNumber e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        });
        em.getTransaction().commit();
        editWindow.add(exit);
        editWindow.add(confirm);
    }}

    public void SetVisible(boolean flag) {
        editWindow.setVisible(flag);
    }
}
