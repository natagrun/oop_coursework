package org.example;

import javax.persistence.Query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.NewMainWindow.em;

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
            if (!em.getTransaction().isActive()) em.getTransaction().begin();
            Query queryd = em.createNativeQuery("SELECT * FROM Person where bd_type ='P'", Patient.class);
            List<Patient> list = queryd.getResultList();
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

            ActionListener actionListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JComboBox box = (JComboBox) e.getSource();
                    u[0] = (String) box.getSelectedItem();
                }
            };
            comboBoxNotSetted.addActionListener(actionListener);
            JButton exitButton = new JButton("Exit");
            JButton confirmButton = new JButton("Confirm");
            JButton deleteButton = new JButton("Delete connection");
            exitButton.addActionListener(event -> {
                this.SetVisible(false);
                docConnectionWindow.SetVisible(true);
            });
            confirmButton.addActionListener(event -> {
                if (!em.getTransaction().isActive()) {
                    em.getTransaction().begin();
                }
                patient = em.find(Patient.class, kostya.get(Arrays.asList(l).indexOf(u[0])));
                Query query = em.createNativeQuery("SELECT * FROM doctor_to_patient where doctor_id =?1 and patient_id =?2");
                query.setParameter(1, doctor.id);
                query.setParameter(2, patient.id);
                List liss = query.getResultList();
                System.out.println(liss);
                if (liss.size() == 0) {
                    patient = em.find(Patient.class, kostya.get(Arrays.asList(l).indexOf(u[0])));
                    doctor.add_patient(patient);
                    em.merge(doctor);
                    docConnectionWindow.fillPatients();
                }
                docConnectionWindow.fillPatients();
                em.getTransaction().commit();


            });
            deleteButton.addActionListener(event -> {

                if (!em.getTransaction().isActive()) em.getTransaction().begin();

                patient = em.find(Patient.class, kostya.get(Arrays.asList(l).indexOf(u[0])));
                Query queryd2 = em.createNativeQuery("DELETE FROM doctor_to_patient where doctor_id =?1 and patient_id =?2");
                queryd2.setParameter(1, doctor.id);
                queryd2.setParameter(2, patient.id);
                queryd2.executeUpdate();
                docConnectionWindow.fillPatients();
                em.getTransaction().commit();
            });

            docToPat.add(exitButton);
            docToPat.add(confirmButton);
            docToPat.add(deleteButton);

            connectWindow.add(docToPat);
        }
        if (doctor == null && patient != null) {
            if (!em.getTransaction().isActive()) em.getTransaction().begin();
            Query queryd = em.createNativeQuery("SELECT * FROM Person where bd_type ='D'", Doctor.class);
            List<Doctor> list = queryd.getResultList();
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
            doctor = em.find(Doctor.class, kostya.get(Arrays.asList(l).indexOf(u)));
            JButton exitButton = new JButton("Exit");
            JButton confirmButton = new JButton("Confirm");
            JButton deleteButton = new JButton("Delete connection");
            exitButton.addActionListener(event -> {
                this.SetVisible(false);
                docConnectionWindow.SetVisible(true);
            });
            confirmButton.addActionListener(event -> {
                em.getTransaction().begin();
                doctor = em.find(Doctor.class, kostya.get(Arrays.asList(l).indexOf(u)));
                Query query = em.createNativeQuery("SELECT * FROM doctor_to_patient where doctor_id =?1 and patient_id =?2");
                query.setParameter(1, doctor.id);
                query.setParameter(2, patient.id);
                List liss = query.getResultList();
                if (liss.size() == 0) {
                    doctor.add_patient(patient);
                    em.merge(doctor);

                }
                em.getTransaction().commit();
                this.docConnectionWindow.fillPatients();
            });
            deleteButton.addActionListener(event -> {
                em.getTransaction().begin();
                doctor = em.find(Doctor.class, kostya.get(Arrays.asList(l).indexOf(u)));
                Query queryd2 = em.createNativeQuery("DELETE FROM doctor_to_patient where doctor_id =?1 and patient_id =?2");
                queryd2.setParameter(1, doctor.id);
                queryd2.setParameter(2, patient.id);
                queryd2.executeUpdate();
                this.docConnectionWindow.fillPatients();
                em.getTransaction().commit();
            });
            docToPat.add(exitButton);
            docToPat.add(confirmButton);
            docToPat.add(deleteButton);
        }
        connectWindow.add(docToPat);
        connectWindow.setVisible(true);

    }

}
