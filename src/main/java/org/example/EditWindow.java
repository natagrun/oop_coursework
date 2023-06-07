package org.example;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import static org.example.NewMainWindow.service;

public class EditWindow {
    private final JFrame editWindow = new JFrame("Editing..");

    private final int id;
    private final NewMainWindow mainWindow;

    ArrayList<JTextField> listOfFields = new ArrayList<>();

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

    private void checkNULL(JTextField b) throws NullString {
        String word = b.getText();
        if (word.length() == 0) throw new NullString();
    }

    public void setEditWindowDoc(int code) {
        editWindow.setSize(500, 600);
        editWindow.setLocation(400, 150);
        editWindow.setLayout(new GridLayout(9, 2, 1, 1));
        if (code == 1) {

            Doctor doctor = service.getDoctor(id);
            JLabel nameBar = new JLabel("Name");
            JTextField namefield = new JTextField(doctor.getName());
            editWindow.add(nameBar);
            editWindow.add(namefield);
            listOfFields.add(namefield);
            JLabel lnameBar = new JLabel("Last name");
            JTextField lnamefield = new JTextField(doctor.getLastName());
            editWindow.add(lnameBar);
            editWindow.add(lnamefield);
            listOfFields.add(lnamefield);

            JLabel ageBar = new JLabel("Age");
            JTextField ageField = new JTextField(String.valueOf(doctor.getAge()));
            editWindow.add(ageBar);
            editWindow.add(ageField);
            listOfFields.add(ageField);

            JLabel phoneBar = new JLabel("Phone number");
            JTextField phoneField = new JTextField(doctor.getPhoneNumber());
            editWindow.add(phoneBar);
            editWindow.add(phoneField);
            listOfFields.add(phoneField);

            JLabel numberBar = new JLabel();
            JTextField numberField = new JTextField();

            JLabel specBar = new JLabel("Specialization");
            JTextField specfield = new JTextField(doctor.getSpecialization());
            editWindow.add(specBar);
            editWindow.add(specfield);
            listOfFields.add(specfield);

            JLabel wdBar = new JLabel("Work days");
            JTextField wdfield = new JTextField(doctor.getWork_days());
            editWindow.add(wdBar);
            editWindow.add(wdfield);
            listOfFields.add(wdfield);


            JLabel wtBAr = new JLabel("Work time");
            JTextField wtfield = new JTextField(doctor.getWork_time());
            editWindow.add(wtBAr);
            editWindow.add(wtfield);
            listOfFields.add(wtfield);


            numberBar.setText("Cabinet");
            numberField.setText(String.valueOf(doctor.getCabinet()));
            editWindow.add(numberBar);
            editWindow.add(numberField);
            listOfFields.add(numberField);


            JButton exit = new JButton("Exit");
            JButton confirm = new JButton("Confirm");

            exit.addActionListener(event -> {
                mainWindow.SetVisible(true);
                editWindow.setVisible(false);
            });

            confirm.addActionListener(event -> {

                try {
                    for (JTextField b : listOfFields) {
                        checkNULL(b);
                    }
                    checkString(ageField);
                    checkString(numberField);
                    service.editDoctor(doctor, namefield.getText(), lnamefield.getText(), Integer.parseInt(ageField.getText()), phoneField.getText(), specfield.getText(), Integer.parseInt(numberField.getText()), wdfield.getText(), wtfield.getText());
                    mainWindow.updateTables();
                } catch (LetterInNumber | NullString e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            });

            editWindow.add(exit);
            editWindow.add(confirm);

        } else {
            Patient patient = service.getPatient(id);
            JLabel nameBar = new JLabel("Name");
            JTextField namefield = new JTextField(patient.getName());
            editWindow.add(nameBar);
            editWindow.add(namefield);
            listOfFields.add(namefield);
            JLabel lnameBar = new JLabel("Last name");
            JTextField lnamefield = new JTextField(patient.getLastName());
            editWindow.add(lnameBar);
            editWindow.add(lnamefield);
            listOfFields.add(lnamefield);
            JLabel ageBar = new JLabel("Age");
            JTextField ageField = new JTextField(String.valueOf(patient.getAge()));
            editWindow.add(ageBar);
            editWindow.add(ageField);
            listOfFields.add(ageField);
            JLabel phoneBar = new JLabel("Phone number");
            JTextField phoneField = new JTextField(patient.getPhoneNumber());
            editWindow.add(phoneBar);
            editWindow.add(phoneField);
            listOfFields.add(phoneField);
            JLabel numberBar = new JLabel();
            JTextField numberField = new JTextField();

            numberBar.setText("Blood type");
            numberField.setText(String.valueOf(patient.getBloodType()));
            editWindow.add(numberBar);
            editWindow.add(numberField);
            listOfFields.add(numberField);

            JButton exit = new JButton("Exit");
            JButton confirm = new JButton("Confirm");

            exit.addActionListener(event -> {
                mainWindow.SetVisible(true);
                editWindow.setVisible(false);
            });

            confirm.addActionListener(event -> {

                try {
                    for (JTextField b : listOfFields) {
                        checkNULL(b);
                    }
                    checkString(phoneField);
                    checkString(ageField);
                    checkString(numberField);
                    service.editPatient(patient,namefield.getText(),lnamefield.getText(),Integer.parseInt(ageField.getText()),phoneField.getText(),Integer.parseInt(numberField.getText()));
                    mainWindow.updateTables();
                } catch (LetterInNumber | NullString e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            });
            editWindow.add(exit);
            editWindow.add(confirm);
        }
    }

    public void SetVisible(boolean flag) {
        editWindow.setVisible(flag);
    }
}
