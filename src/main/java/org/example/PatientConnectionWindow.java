package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class PatientConnectionWindow {

    private final JFrame patConnectWin = new JFrame("Patient appointment");
    private final Patient patient;
    private final NewMainWindow mainWindow;
    private final JPanel tableandtext = new JPanel();

    private Disease selectedDisease;

    public PatientConnectionWindow(NewMainWindow mainWindow, Patient patient) {
        this.mainWindow = mainWindow;
        this.patient = patient;

    }


    public JPanel fillPatients() {
        tableandtext.removeAll();
        JPanel text = new JPanel();
        JLabel name = new JLabel(patient.getName());
        JLabel lname = new JLabel(patient.getLastName());
        text.add(name);
        text.add(lname);

        tableandtext.add(text);

        ArrayList<String[]> dataPatient = new ArrayList<>();

        for (Disease disease : patient.getDiseases()) {
            String[] oneD = new String[3];
            oneD[0] = String.valueOf(disease.getId());
            oneD[1] = disease.getName();
            oneD[2] = Integer.toString(disease.getCount());
            dataPatient.add(oneD);
        }
        String[][] ans = null;
        try {
            ans = new String[dataPatient.size()][dataPatient.get(0).length];
            for (int i = 0; i < ans.length; i++) {
                ans[i] = dataPatient.get(i);
            }

        } catch (IndexOutOfBoundsException ignored) {
        }

        DefaultTableModel model = new DefaultTableModel(ans, new String[]{"id", "Name", "Count"});
        JTable mainTable = new JTable(model);
        mainTable.setSize(300, 700);
        mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ListSelectionModel selModel = mainTable.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int[] selectedRows = mainTable.getSelectedRows();
                int selIndex = selectedRows[0];
                TableModel model = mainTable.getModel();
                Object value = model.getValueAt(selIndex, 0);

                EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_persistence");
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();
                selectedDisease = em.find(Disease.class, Integer.parseInt(value.toString()));

            }

        });

        JScrollPane scroll = new JScrollPane(mainTable);
        tableandtext.add(scroll);

        return tableandtext;


    }

    public void setWin() {
        patConnectWin.setSize(500, 600);
        patConnectWin.setLocation(400, 150);
        patConnectWin.setLayout(new BorderLayout());
        patConnectWin.add(fillPatients(), BorderLayout.CENTER);
        JPanel buttons = new JPanel(new GridLayout(3, 2, 1, 1));

        JButton addButton = new JButton("Add");
        buttons.add(addButton);

        JButton deleteButton = new JButton("Delete");
        buttons.add(deleteButton);

        deleteButton.addActionListener(event -> {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_persistence");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            try {
                Query queryd2 = em.createNativeQuery("DELETE FROM patient_to_disease where disease_id =?2");
                queryd2.setParameter(2, selectedDisease.getId());
                queryd2.executeUpdate();
                em.getTransaction().commit();
                patConnectWin.remove(fillPatients());
                patConnectWin.add(fillPatients(), BorderLayout.CENTER);
            }catch (IndexOutOfBoundsException ignored){}


        });

        JButton exitbutton = new JButton("Exit");
        buttons.add(exitbutton);

        exitbutton.addActionListener(event -> {
            fillPatients();
            mainWindow.SetVisible(true);
            this.SetVisible(false);


        });

        addButton.addActionListener(event -> {
            ConnewctionButSEcond conn = new ConnewctionButSEcond(this, null, patient);
            conn.setWinLayout();
            conn.SetVisible(true);
            fillPatients();
        });

        patConnectWin.add(buttons, BorderLayout.SOUTH);
    }

    public void SetVisible(boolean b) {
        patConnectWin.setVisible(b);
    }

}
