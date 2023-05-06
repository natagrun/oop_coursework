package org.example;

import org.hibernate.Session;

import javax.persistence.Query;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class NewMainWindow {
    private final JFrame MainWindow = new JFrame("CourseWork");

    private final AddingWindow addWindow = new AddingWindow(this);
//    private final AddWindow AddWindow = new AddWindow();
//    private final EditWindow EditWindow = new AddWindow();
//    private final SaveWindow SaveWindow = new AddWindow();


    //    private final ArrayList<JButton> toolBarButtons = new ArrayList<>(Arrays.asList(newButton, statisticsButton, certificateButton, saveButton, uploadButton));
    private final ArrayList<JButton> toolBarButtons = new ArrayList<JButton>();

    private JToolBar toolBar;

    public void SetVisible(boolean flag) {
        MainWindow.setVisible(flag);
    }

    private void toolBarInit() {

        toolBar = new JToolBar("Панель");
        toolBar.setFloatable(false);

        JButton newButton = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/new.png"));
        newButton.setToolTipText("Добавить нового доктора/пациента");
        toolBarButtons.add(newButton);

        newButton.addActionListener(event -> {
            addWindow.show();
            MainWindow.setVisible(false);
        });

        JButton statisticsButton = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/stat.png"));
        statisticsButton.setToolTipText("Подвести статистику заболеваний");
        toolBarButtons.add(statisticsButton);

        JButton certificateButton = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/sertif.png"));
        certificateButton.setToolTipText("Создать справку");
        toolBarButtons.add(certificateButton);

        JButton saveButton = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/save.png"));
        saveButton.setToolTipText("хз не помню о чем я думала кода я создавала эту кнопку");
        toolBarButtons.add(saveButton);

        JButton uploadButton = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/doctor.png"));
        uploadButton.setToolTipText("хз не помню о чем я думала кода я создавала эту кнопку");
        toolBarButtons.add(uploadButton);

        for (JButton b : toolBarButtons) {
            toolBar.addSeparator();
            toolBar.add(b);
        }

    }

    private void fillDoctorBox(JPanel box) {
        String test = "test";

        box.setLayout(new GridLayout(6, 1, 0, 0));

        JLabel name = new JLabel();
        name.setText("   NAME AND SURNAME");
        name.setFont(new Font("Dialog", Font.PLAIN, 16));

        JLabel specialization = new JLabel();
        specialization.setText("   Specialization: " + test);
        JLabel age = new JLabel();
        age.setText("   Age: " + test);
        JLabel workDays = new JLabel();
        workDays.setText("   Work Days: " + test);
        JLabel workTime = new JLabel();
        workTime.setText("   Work Time: " + test);

        JPanel buttonPanel = new JPanel();


        JButton editButton = new JButton("Edit");
        editButton.setToolTipText("Edit card");
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setToolTipText("Delete card");
        buttonPanel.add(deleteButton);

        box.add(name);
        box.add(specialization);
        box.add(age);
        box.add(workDays);
        box.add(workTime);
        box.add(buttonPanel);
        box.setBorder(BorderFactory.createLineBorder(Color.darkGray));

    }

    private void fillPatientsBox(JPanel box, DefaultTableModel b) {
        String test = "test";

        box.setLayout(new GridLayout(b.getColumnCount() + 2, 1, 0, 0));
        JLabel name = new JLabel();
        name.setText("   NAME AND SURNAME");
        name.setFont(new Font("Dialog", Font.PLAIN, 16));

        JLabel age = new JLabel();
        age.setText("   Age: " + test);

        JLabel bloodType = new JLabel();
        bloodType.setText("   Blood Type: " + test);

        JLabel diseases = new JLabel();
        diseases.setText("   Diseases: " + test);

        JPanel buttonPanel = new JPanel();


        JButton editButton = new JButton("Edit");
        editButton.setToolTipText("Edit card");
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setToolTipText("Delete card");
        buttonPanel.add(deleteButton);

        JButton writeButton = new JButton("Assign");
        writeButton.setToolTipText("Assign to doctor");
        buttonPanel.add(writeButton);

        JButton certificateButton = new JButton("Make certificate");
        certificateButton.setToolTipText("Make certificate");


        box.add(name);
        box.add(age);
        box.add(bloodType);
        box.add(diseases);
        box.add(buttonPanel);
        box.add(certificateButton);
        box.setBorder(BorderFactory.createLineBorder(Color.darkGray));
    }

//    private String[][] fillTables() {
//
//    }

    public void show() {
        MainWindow.setSize(1080, 720);
        MainWindow.setLocation(150, 100);
        MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainWindow.setVisible(true);
        MainWindow.setLayout(new BorderLayout());

        toolBarInit();
        MainWindow.add(toolBar, BorderLayout.NORTH);

        JTabbedPane tabsFolder = new JTabbedPane(JTabbedPane.LEFT);
        MainWindow.add(tabsFolder, BorderLayout.CENTER);

        JPanel doctorsPanel = new JPanel();
        JPanel doctorsBox = new JPanel();

        SortToolBar sortToolBarD = new SortToolBar();


        String[] columnsd = {"Name", "Last Name", "Age", "Phone number", " Specialization", "Work Days", "Work time", "Cabinet"};
        String[][] datad = {{"Vlass", "Vozmitel", "19", "9583646583", " Heartbreaker", "Monday, Saturday", "9:50-17:20", "1381"},
                {"Alex", "Markush", "18", "3948573355", " Suspends smth", "Monday, Friday", "15:30-17:00", "1304"},
                {"Max", "Fastest", "19", "574389458", " Krag", "Wednesday", "13:30-18:00", "4578"},
                {"Mishk", "pigeon", "21", "2934866", " mmm boy", "Friday", "7:30-19:00", "1307"}};
//        DefaultTableModel modeld = new DefaultTableModel(fillTables(), columnsd);
        DefaultTableModel modeld = new DefaultTableModel(datad, columnsd);
        JTable doctors = new JTable(modeld);
        doctors.setAutoCreateRowSorter(true);
        sortToolBarD.func(doctors);

        JScrollPane scrolld = new JScrollPane(doctors);
        doctorsPanel.setLayout(new BorderLayout());
        fillDoctorBox(doctorsBox);
        doctorsPanel.add(sortToolBarD, BorderLayout.NORTH);
        doctorsPanel.add(scrolld, BorderLayout.CENTER);
        doctorsBox.setPreferredSize(new Dimension(300, 500));
        doctorsPanel.add(doctorsBox, BorderLayout.EAST);
        tabsFolder.addTab("Доктора", new ImageIcon("/Users/natalagrunskaa/Downloads/doctor.png"), doctorsPanel, "Открыть таблицу докторов");


        JPanel patientsPanel = new JPanel();
        JPanel patientsBox = new JPanel();

        SortToolBar sortToolBarP = new SortToolBar();


        String[] columnsp = {"Имя", "Фамилия", "Возраст", "Номер телефона", "Группа крови"};
        String[][] datap = {{"Vlass", "Vozmitel", "19", "9583646583", " 1"},
                {"Alex", "Markush", "18", "3948573355", " 2"}};

        DefaultTableModel modelp = new DefaultTableModel(datap, columnsp);


        JTable patients = new JTable(modelp);
        sortToolBarP.func(patients);
        patients.setAutoCreateRowSorter(true);

        JScrollPane scrollp = new JScrollPane(patients);
        patientsPanel.setLayout(new BorderLayout());
        fillPatientsBox(patientsBox, modelp);
        patientsPanel.add(sortToolBarP, BorderLayout.NORTH);
        patientsPanel.add(scrollp, BorderLayout.CENTER);
        patientsBox.setPreferredSize(new Dimension(300, 500));
        patientsPanel.add(patientsBox, BorderLayout.EAST);
        tabsFolder.addTab("patients", new ImageIcon("/Users/natalagrunskaa/Downloads/doctor.png"), patientsPanel, "Открыть таблицу patients");


    }

    public static void main(String[] args) {
        NewMainWindow n = new NewMainWindow();
        n.show();
    }
}
