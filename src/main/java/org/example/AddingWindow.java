package org.example;

import javax.swing.*;
import java.awt.*;

public class AddingWindow {
    private final JFrame Adding = new JFrame("Adding");
    private NewMainWindow mainWindow;

    public AddingWindow(NewMainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    private void fillPanel(JPanel panel,int r){

        if (r == 1) {
            Doctor d = new Doctor();
            panel.setLayout(new GridLayout(8, 2, 3, 3));
            JLabel name = new JLabel("Enter name");
            panel.add(name);
            JTextField nameInsert = new JTextField();
            panel.add(nameInsert);

            JLabel lastName = new JLabel("Enter last name");
            panel.add(lastName);
            JTextField lastNameInsert = new JTextField();
            panel.add(lastNameInsert);

            JLabel age = new JLabel("Enter age");
            panel.add(age);
            JTextField ageInsert = new JTextField();
            panel.add(ageInsert);

            JLabel spec = new JLabel("Enter specialization");
            panel.add(spec);
            JTextField specInsert = new JTextField();
            panel.add(specInsert);


            JLabel workd = new JLabel("Enter work days");
            panel.add(workd);
            JTextField workdInsert = new JTextField();
            panel.add(workdInsert);

            JLabel workt = new JLabel("Enter work time");
            panel.add(workt);
            JTextField worktInsert = new JTextField();
            panel.add(worktInsert);


            JLabel phnum = new JLabel("Enter phone number");
            panel.add(phnum);
            JTextField phnumInsert = new JTextField();
            panel.add(phnumInsert);

            JButton exit = new JButton("Exit");
            JButton confirm = new JButton("Confirm");

            exit.addActionListener(event -> {
                mainWindow.SetVisible(true);
                Adding.setVisible(false);
            });

            panel.add(exit);
            panel.add(confirm);
        }

        if (r == 2) {
            Patient p = new Patient();
            panel.setLayout(new GridLayout(6, 2, 3, 3));
            JLabel name = new JLabel("Enter name");
            panel.add(name);
            JTextField nameInsert = new JTextField();
            panel.add(nameInsert);

            JLabel lastName = new JLabel("Enter last name");
            panel.add(lastName);
            JTextField lastNameInsert = new JTextField();
            panel.add(lastNameInsert);

            JLabel age = new JLabel("Enter age");
            panel.add(age);
            JTextField ageInsert = new JTextField();
            panel.add(ageInsert);


            JLabel workt = new JLabel("Enter blood type");
            panel.add(workt);
            JTextField worktInsert = new JTextField();
            panel.add(worktInsert);


            JLabel phnum = new JLabel("Enter phone number");
            panel.add(phnum);
            JTextField phnumInsert = new JTextField();
            panel.add(phnumInsert);

            JButton exit = new JButton("Exit");
            JButton confirm = new JButton("Confirm");

            exit.addActionListener(event -> {
                mainWindow.SetVisible(true);
                Adding.setVisible(false);
            });

            panel.add(exit);
            panel.add(confirm);
        }

        if (r == 3) {
            Disease disease = new Disease();
            panel.setLayout(new GridLayout(3, 2, 3, 3));
            JLabel name = new JLabel("Enter name");
            panel.add(name);
            JTextField nameInsert = new JTextField();
            panel.add(nameInsert);

            JButton exit = new JButton("Exit");
            JButton confirm = new JButton("Confirm");

            exit.addActionListener(event -> {
                mainWindow.SetVisible(true);
                Adding.setVisible(false);

            });

            panel.add(exit);
            panel.add(confirm);
        }
    }
    public void show(){
        Adding.setSize(500, 600);
        Adding.setLocation(400, 150);
        Adding.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabsFolder = new JTabbedPane(JTabbedPane.TOP);
        Adding.add(tabsFolder, BorderLayout.CENTER);

        JPanel docPanel = new JPanel();
        fillPanel(docPanel,1);
        JPanel patPanel = new JPanel();
        fillPanel(patPanel,2);
        JPanel disPanel = new JPanel();
        fillPanel(disPanel,3);

        tabsFolder.addTab("Доктора", new ImageIcon("/Users/natalagrunskaa/Downloads/doctor.png"), docPanel, "Открыть таблицу докторов");
        tabsFolder.addTab("Доктора", new ImageIcon("/Users/natalagrunskaa/Downloads/doctor.png"), patPanel, "Открыть таблицу докторов");
        tabsFolder.addTab("Доктора", new ImageIcon("/Users/natalagrunskaa/Downloads/doctor.png"), disPanel, "Открыть таблицу докторов");



        Adding.setVisible(true);

    }
}
