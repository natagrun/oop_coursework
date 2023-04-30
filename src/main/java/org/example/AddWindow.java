package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class AddWindow {

    private final Color[] colors = {Color.cyan, Color.orange, Color.magenta, Color.blue,
            Color.red, Color.green, Color.yellow, Color.pink};

    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private ArrayList<JTextField> text_fields = new ArrayList<JTextField>();

    private JButton back_button;

    private String[] textft = {"Имя","Фамилия","Возраст","Номер телефона","Специализация","Рабочие дни","Раюочее время","Кабинет"};
    private int k=0;


    private JTextField Name;
    private JTextField Last_Name;
    private JTextField Age;
    private JTextField Phone_number;
    private JTextField Spec;
    private JTextField WorkDays;
    private JTextField WorkTime;
    private JTextField Cabinet;

    private JButton filter;
    private JPanel filterPanel;

    private Dimension d;

    public boolean check_nums(String number){

        boolean isOnlyDigits = true;
        for(int i = 0; i < number.length() && isOnlyDigits; i++) {
            if(!Character.isDigit(number.charAt(i))) {
                isOnlyDigits = false;
            }
        }
        return isOnlyDigits;

    }

    public void show() {

        JFrame adding = new JFrame("Добавление нового врача");
        adding.setSize(1080, 720);
        adding.setLocation(100, 100);
        adding.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Name = new JTextField();
        text_fields.add(Name);
        Last_Name = new JTextField();
        text_fields.add(Last_Name);
        Age = new JTextField();
        text_fields.add(Age);
        Phone_number = new JTextField();
        text_fields.add(Phone_number);
        Spec = new JTextField();
        text_fields.add(Spec);
        WorkDays = new JTextField();
        text_fields.add(WorkDays);
        WorkTime = new JTextField();
        text_fields.add(WorkTime);
        Cabinet = new JTextField();
        text_fields.add(Cabinet);
        filter = new JButton("Поиск");


        filterPanel = new JPanel();
        filterPanel.setPreferredSize(d = new Dimension(200,300));
        for (JTextField b : text_fields) {
            b.setPreferredSize(new Dimension(300,30));
            JPanel f = new JPanel();
            JLabel label = new JLabel();
            label.setText(textft[k]);
            k++;
            f.add(label);
            f.add(b);

            b.addActionListener (new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                    JOptionPane.showMessageDialog (adding, "Поле успешно заполнено"); }});


            filterPanel.add(f,BorderLayout.EAST);

        }

        filterPanel.add(filter,BorderLayout.SOUTH);

        adding.add(filterPanel,BorderLayout.NORTH);

        back_button = new JButton("NO");
        back_button.setToolTipText("хз не помню о чем я думала кода я создавала эту кнопку");
        adding.add(back_button);



        back_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                MainWindow a = new MainWindow();
                a.show();
                adding.setVisible(false);
            }
        });

        adding.setVisible(true);
    }
}
