package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.example.NewMainWindow.service;

public class ConnectionWindow {
    private final JFrame connectWindow = new JFrame("Making connection");
    private final DocConnectionWindow docConnectionWindow;

    private Doctor doctor;
    private Patient patient;

    public ConnectionWindow(DocConnectionWindow docConnectionWindow, Doctor doctor, Patient patient) {
        this.docConnectionWindow = docConnectionWindow;
        this.doctor = doctor;
        this.patient = patient;
    }

    public void SetVisible(boolean b) {
        connectWindow.setVisible(b);
    }

    public void setWinLayout() {
        connectWindow.setSize(500, 600);
        connectWindow.setLocation(400, 150);
        connectWindow.setLayout(new BorderLayout());

        JPanel docToPat = new JPanel();
        docToPat.setLayout(new GridLayout(3, 2, 1, 1));
        JComboBox comboBoxNotSetted;

        if (doctor != null && patient == null) {
            List<Patient> list =service.getAllPatients();
            String[] l = new String[list.size()];
            ArrayList<Integer> kostya = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                l[i] = list.get(i).getName() + " " + list.get(i).getLastName();
                kostya.add(list.get(i).getId());
            }

            comboBoxNotSetted = new JComboBox(l);
            JLabel docName = new JLabel("Connect " + doctor.getName() + " " + doctor.getLastName());
            docToPat.add(docName);
            docToPat.add(comboBoxNotSetted);
            final String[] u = new String[1];

            ActionListener actionListener = e -> {
                JComboBox box = (JComboBox) e.getSource();
                u[0] = (String) box.getSelectedItem();
            };
            comboBoxNotSetted.addActionListener(actionListener);
            JButton exitButton = new JButton("Exit");
            JButton confirmButton = new JButton("Confirm");
            JButton deleteButton = new JButton("Delete connection");
            exitButton.addActionListener(event -> {
                this.SetVisible(false);
                docConnectionWindow.SetVisible(true);
            });
            JComboBox finalComboBoxNotSetted = comboBoxNotSetted;
            confirmButton.addActionListener(event -> {
                u[0] = (String) finalComboBoxNotSetted.getSelectedItem();
                patient=service.getPatient(kostya.get(Arrays.asList(l).indexOf(u[0])));
                if (service.getDoctorPatientConnections(doctor.id,patient.id)) {
                    patient = service.getPatient(kostya.get(Arrays.asList(l).indexOf(u[0])));
                    service.addPatientToDoctor(patient,doctor);
                    docConnectionWindow.docConnectWin.add(docConnectionWindow.fillPatients(), BorderLayout.CENTER);
                }
                docConnectionWindow.docConnectWin.add(docConnectionWindow.fillPatients(), BorderLayout.CENTER);

            });
            deleteButton.addActionListener(event -> {
                patient = service.getPatient((Arrays.asList(l).indexOf(u[0])));
                service.deletePatientFromDoctor(doctor.id,patient.id);
                docConnectionWindow.fillPatients();
            });

            docToPat.add(exitButton);
            docToPat.add(confirmButton);
            docToPat.add(deleteButton);
            docConnectionWindow.fillPatients();
            connectWindow.add(docToPat);
        }
        if (doctor == null && patient != null) {
            List<Doctor> list = service.getAllDoctors();
            String[] l = new String[list.size()];
            ArrayList<Integer> kostya = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                l[i] = list.get(i).getName() + " " + list.get(i).getLastName();
                kostya.add(list.get(i).getId());
            }
            comboBoxNotSetted = new JComboBox<>(l);
            JLabel patName = new JLabel("Connect " + patient.getName() + " " + patient.getLastName());
            docToPat.add(patName);
            docToPat.add(comboBoxNotSetted);
            String u = comboBoxNotSetted.getSelectedItem().toString();
            doctor = service.getDoctor(kostya.get(Arrays.asList(l).indexOf(u)));
            JButton exitButton = new JButton("Exit");
            JButton confirmButton = new JButton("Confirm");
            JButton deleteButton = new JButton("Delete connection");
            exitButton.addActionListener(event -> {
                this.SetVisible(false);
                docConnectionWindow.SetVisible(true);
            });
            confirmButton.addActionListener(event -> {
                doctor = service.getDoctor(Arrays.asList(l).indexOf(u));
                if (service.getDoctorPatientConnections(doctor.id, patient.id)) {
                    service.addPatientToDoctor(patient,doctor);

                }
                this.docConnectionWindow.fillPatients();
            });
            deleteButton.addActionListener(event -> {
                doctor = service.getDoctor(kostya.get(Arrays.asList(l).indexOf(u)));
                service.deletePatientFromDoctor(doctor.id,patient.id);
                this.docConnectionWindow.fillPatients();
            });
            docToPat.add(exitButton);
            docToPat.add(confirmButton);
            docToPat.add(deleteButton);
        }
        connectWindow.add(docToPat);
        connectWindow.setVisible(true);

    }

}
