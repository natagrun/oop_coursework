package org.example;
import javax.persistence.Query;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.example.NewMainWindow.em;

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

        if(!em.getTransaction().isActive()) em.getTransaction().begin();
        Query queryd = em.createNativeQuery("SELECT * FROM Disease", Disease.class);
        List<Disease> list = queryd.getResultList();
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
        disease = em.find(Disease.class, kostya.get(Arrays.asList(l).indexOf(f)));
        JButton exitButton = new JButton("Exit");
        JButton confirmButton = new JButton("Confirm");
        JButton deleteButton = new JButton("Delete connection");


        exitButton.addActionListener(event -> {
            this.SetVisible(false);
            patientConnectionWindow.SetVisible(true);
        });
        confirmButton.addActionListener(event -> {
            String u = comboBoxNotSetted.getSelectedItem().toString();
            if(!em.getTransaction().isActive()) em.getTransaction().begin();
            disease = em.find(Disease.class, kostya.get(Arrays.asList(l).indexOf(u)));
            Query query = em.createNativeQuery("SELECT * FROM patient_to_disease where disease_id =?1 and patient_id =?2");
            query.setParameter(1, disease.getId());
            query.setParameter(2, patient.id);
            List liss = query.getResultList();
            if (liss.size() == 0) {
                patient.add_disease(disease);
                em.merge(patient);

            }
            em.getTransaction().commit();
            patientConnectionWindow.fillPatients();
        });
        deleteButton.addActionListener(event -> {
            String u = comboBoxNotSetted.getSelectedItem().toString();
            if(!em.getTransaction().isActive()) em.getTransaction().begin();
            disease = em.find(Disease.class, kostya.get(Arrays.asList(l).indexOf(u)));
            Query queryd2 = em.createNativeQuery("DELETE FROM patient_to_disease where disease_id =?1 and patient_id =?2");
            queryd2.setParameter(1, disease.getId());
            queryd2.setParameter(2, patient.id);
            queryd2.executeUpdate();
            disease.setCount(disease.getCount()-1);

            em.getTransaction().commit();
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
