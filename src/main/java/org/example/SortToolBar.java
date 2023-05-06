package org.example;

import javax.swing.*;
import java.util.ArrayList;

public class SortToolBar extends JToolBar {
    public void func(JTable b) {

        String[] items = new String[ b.getColumnCount()];

        for (int i=0;i<b.getColumnCount();i++){
            items[i] = b.getColumnName(i);
        }


        JTextField search = new JTextField();

        JComboBox patternBox = new JComboBox(items);


        this.setFloatable(false);
        JButton searchButton = new JButton("Search");
        searchButton.setToolTipText("Search by component");
        this.add(search);
        this.add(patternBox);
        this.add(searchButton);


    }

}
