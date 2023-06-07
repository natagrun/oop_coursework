package org.example;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import static org.example.NewMainWindow.service;

public class DocConnectionWindow {

    public final JFrame docConnectWin = new JFrame("Patient appointment");
    private final Doctor doctor;
    private final NewMainWindow mainWindow;
    private final JPanel tableandtext = new JPanel();

    private Patient selectedPatient;

    public DocConnectionWindow(NewMainWindow mainWindow, Doctor doctor) {
        this.mainWindow = mainWindow;
        this.doctor = doctor;

    }

    public JPanel fillPatients() {
        tableandtext.removeAll();
        JPanel text = new JPanel();
        JLabel name = new JLabel(doctor.getName());
        JLabel lname = new JLabel(doctor.getLastName());
        text.add(name);
        text.add(lname);

        tableandtext.add(text);

        ArrayList<String[]> dataDoctors = new ArrayList<>();

        for (Patient patient : doctor.getPatients()) {
            String[] oneD = new String[3];
            oneD[0] = String.valueOf(patient.getId());
            oneD[1] = patient.getName();
            oneD[2] = patient.getLastName();
            dataDoctors.add(oneD);
        }
        String[][] ans = null;
        try {
            ans = new String[dataDoctors.size()][dataDoctors.get(0).length];
            for (int i = 0; i < ans.length; i++) {
                ans[i] = dataDoctors.get(i);
            }

        } catch (IndexOutOfBoundsException ignored) {
        }

        DefaultTableModel model = new DefaultTableModel(ans, new String[]{"id", "Name", "Last name"});
        JTable mainTable = new JTable(model);
        mainTable.setSize(300, 700);
        mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ListSelectionModel selModel = mainTable.getSelectionModel();
        selModel.addListSelectionListener(e -> {
            int[] selectedRows = mainTable.getSelectedRows();
            int selIndex = selectedRows[0];
            TableModel model1 = mainTable.getModel();
            Object value = model1.getValueAt(selIndex, 0);
            selectedPatient = service.getPatient(Integer.parseInt(value.toString()));
        });

        JScrollPane scroll = new JScrollPane(mainTable);
        tableandtext.add(scroll);
        return tableandtext;
    }

    public void setWin() {
        docConnectWin.setSize(500, 600);
        docConnectWin.setLocation(400, 150);
        docConnectWin.setLayout(new BorderLayout());
        docConnectWin.add(fillPatients(), BorderLayout.CENTER);
        JPanel buttons = new JPanel(new GridLayout(3, 2, 1, 1));

        JButton addButton = new JButton("Add");
        buttons.add(addButton);

        JButton deleteButton = new JButton("Delete");
        buttons.add(deleteButton);

        deleteButton.addActionListener(event -> {
            try {
                service.deletePatientFromDoctor(doctor.getId(), selectedPatient.getId());
                docConnectWin.add(fillPatients(), BorderLayout.CENTER);
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
            ConnectionWindow conn = new ConnectionWindow(this, doctor, null);
            conn.setWinLayout();
            conn.SetVisible(true);
            fillPatients();
        });

        docConnectWin.add(buttons, BorderLayout.SOUTH);
    }

    public void SetVisible(boolean b) {
        docConnectWin.setVisible(b);
    }


}
