package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchWindow {
    private JButton back_button;
    private JPanel panel;

    private JComboBox doctors;
    private JTextField Error;

    private JButton filter;


    private void checkName(JTextField bName) throws MyException, NullPointerException {
            String sName = bName.getText();
            if (sName.contains("Введите имя")) throw new MyException();
            if (sName.length() == 0) throw new NullPointerException();
        }

    public boolean checkName2(String sName){

        if (sName.contains("Введите имя")) return false;
        if (sName.length() == 0) return false;
        else return true;
    }



    public void show() {

        JFrame searching = new JFrame("Поиск врачей и пациентов");
        searching.setSize(1080, 720);
        searching.setLocation(100, 100);
        searching.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        doctors = new JComboBox(new String[]{"5", "4 Дюма", "44 4"});

//        panel.add(panel,BorderLayout.SOUTH);


        back_button = new JButton("NO");
        back_button.setToolTipText("хз не помню о чем я думала кода я создавала эту кнопку");
        panel.add(back_button);
        panel.add(doctors);

        Error = new JTextField("Введите имя");
        filter = new JButton("Поиск");
// Добавление компонентов на панель
        JPanel filterPanel = new JPanel();
        panel.add(Error);
        panel.add(filter);
        panel.add(filter);


        filter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    checkName(Error);
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(searching, ex.toString());
                } catch (MyException myEx) {
                    JOptionPane.showMessageDialog(null, myEx.getMessage());
                }
            }
        });

        searching.add(panel, BorderLayout.NORTH);
        back_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                MainWindow a = new MainWindow();
                a.show();
                searching.setVisible(false);
            }
        });

        searching.setVisible(true);
    }
}
