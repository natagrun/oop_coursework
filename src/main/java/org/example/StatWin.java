package org.example;
import javax.persistence.Query;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.NewMainWindow.em;

public class StatWin {
    private final JFrame statWin = new JFrame("Statistics");
    private final String[] columns = new String[]{"id", "Name", "Count"};
    public void show() {
        statWin.setSize(500, 600);
        statWin.setLocation(400, 150);
        statWin.setLayout(new BorderLayout());
        ArrayList<String[]> dataDisease = new ArrayList<>();
        if(!em.getTransaction().isActive()) em.getTransaction().begin();
        Query queryd = em.createNativeQuery("SELECT * FROM Disease ORDER BY count DESC", Disease.class);
        List<Disease> listd = queryd.getResultList();
        for (Disease disease : listd) {
            String[] oneD = new String[3];
            oneD[0] = String.valueOf(disease.getId());
            oneD[1] = disease.getName();
            oneD[2] = String.valueOf(disease.getCount());
            dataDisease.add(oneD);
        }
        em.getTransaction().commit();
        String[][] ans = new String[dataDisease.size()][dataDisease.get(0).length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = dataDisease.get(i);
        }
        DefaultTableModel model = new DefaultTableModel(ans,columns);
        JTable mainTable = new JTable(model);
        JScrollPane scrolld = new JScrollPane(mainTable);
        statWin.add(scrolld,BorderLayout.CENTER);

        JLabel s = new JLabel("Diseases statistics");
        s.setFont(new Font("Dialog", Font.PLAIN, 16));
        statWin.add(s,BorderLayout.NORTH);
        statWin.setVisible(true);

    }
}
