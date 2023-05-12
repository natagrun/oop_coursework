package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CardBox extends JPanel {
    String[] data;
    ArrayList<JLabel> listLabels=new ArrayList<>();
    public CardBox( String[] columns, NewMainWindow newMainWindow) {
        this.setLayout(new GridLayout(columns.length, 1, 0, 0));
        JLabel name = new JLabel();
        listLabels.add(name);
        JLabel lastname = new JLabel();listLabels.add(lastname);
        name.setFont(new Font("Dialog", Font.PLAIN, 16));
        lastname.setFont(new Font("Dialog", Font.PLAIN, 16));

        this.add(name);
        this.add(lastname);


        for (int i = 3; i < columns.length; i++) {
            JLabel label1 = new JLabel();
            listLabels.add(label1);
            JLabel label2 = new JLabel();
            label2.setText(columns[i] + ": " + " ");
            this.add(label2);
            this.add(label1);
        }
        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton("Edit");
        editButton.setToolTipText("Edit card");
        buttonPanel.add(editButton);

        editButton.addActionListener(event -> {
            try{
                int code = 17;
                if (columns.length != 6) {
                    code = 1;
                }
                newMainWindow.openEditWin(Integer.parseInt(data[0]),code);

            }catch(NullPointerException n){
               System.out.println("");
            }




        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.setToolTipText("Delete card");
        buttonPanel.add(deleteButton);

        deleteButton.addActionListener(event -> {

            try{
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_persistence");
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();
                Person person = em.find(Person.class, Integer.parseInt(data[0]));
                em.remove(person);
                em.getTransaction().commit();
                newMainWindow.updateTables();
            }catch(NullPointerException n){
                System.out.println("");
            }




        });

        this.add(buttonPanel);
        this.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        this.setPreferredSize(new Dimension(300, 500));
    }

    public void updateCard() {
        if (data!=null){
        for(int i=0;i<this.data.length;i++){
            JLabel l = listLabels.get(i);
            l.setText(data[i]);
        }}
    }
}
