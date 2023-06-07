package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.example.NewMainWindow.service;
public class ConnewctionButSEcond {
    private final JFrame connectWindow = new JFrame("Making connection");
    private final PatientConnectionWindow patientConnectionWindow;

    private Disease disease;
    private final Patient patient;

    public ConnewctionButSEcond(PatientConnectionWindow patientConnectionWindow, Disease disease, Patient patient) {
        this.patientConnectionWindow = patientConnectionWindow;
        this.disease = disease;
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
        List<Disease> list = service.getAllDiseases();
        String[] l = new String[list.size()];
        ArrayList<Integer> kostya = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            l[i] = list.get(i).getName();
            kostya.add(list.get(i).getId());
        }
        comboBoxNotSetted = new JComboBox<>(l);
        JLabel patName = new JLabel("Connect " + patient.getName() + " " + patient.getLastName());
        docToPat.add(patName);
        docToPat.add(comboBoxNotSetted);
        String f = comboBoxNotSetted.getSelectedItem().toString();
        disease = service.getDisease(kostya.get(Arrays.asList(l).indexOf(f)));
        JButton exitButton = new JButton("Exit");
        JButton confirmButton = new JButton("Confirm");
        JButton deleteButton = new JButton("Delete connection");


        exitButton.addActionListener(event -> {
            this.SetVisible(false);
            patientConnectionWindow.SetVisible(true);
        });
        confirmButton.addActionListener(event -> {
            String u = comboBoxNotSetted.getSelectedItem().toString();
            disease = service.getDisease(kostya.get(Arrays.asList(l).indexOf(u)));
            if (service.getPatientDiseaseConnections(disease.getId(), patient.id)) service.addDiseaseToPatient(patient,disease);
            patientConnectionWindow.fillPatients();
        });
        deleteButton.addActionListener(event -> {
            String u = comboBoxNotSetted.getSelectedItem().toString();
            disease = service.getDisease(kostya.get(Arrays.asList(l).indexOf(u)));
            service.deleteDiseaseFromPatient(disease.getId(), patient.id);
            disease.setCount(disease.getCount()-1);
            patientConnectionWindow.fillPatients();
        });
        docToPat.add(exitButton);
        docToPat.add(confirmButton);
        docToPat.add(deleteButton);
        patientConnectionWindow.fillPatients();
        connectWindow.add(docToPat);
        connectWindow.setVisible(true);
}

}
