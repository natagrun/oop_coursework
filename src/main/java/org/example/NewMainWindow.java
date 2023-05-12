package org.example;

import javax.persistence.*;
import javax.print.Doc;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.text.ParseException;
import java.util.*;
import java.util.List;

public class NewMainWindow {
    private final JFrame MainWindow = new JFrame("CourseWork");
    private final ArrayList<JButton> toolBarButtons = new ArrayList<JButton>();
    private final JTabbedPane tabsFolder = new JTabbedPane(JTabbedPane.LEFT);

    private JToolBar toolBar;

    public void SetVisible(boolean flag) {
        MainWindow.setVisible(flag);
        updateTables();
    }

    public void openEditWin(int data,int code){
        this.updateTables();
        EditWindow editWindow = new EditWindow(this,data);
        editWindow.setEditWindowDoc(code);
        editWindow.SetVisible(true);

    }

    private void toolBarInit() throws ParseException {
        AddObject n = new AddObject(this);
        n.setAddWindow();

        CertificateWindow c = new CertificateWindow();
        c.setCertifWin();

        toolBar = new JToolBar("Панель");
        toolBar.setFloatable(false);

        JButton newButton = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/new.png"));
        newButton.setToolTipText("Добавить нового доктора/пациента");
        toolBarButtons.add(newButton);

        newButton.addActionListener(event -> {
            n.setVisible(true);
        });

        JButton statisticsButton = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/stat.png"));
        statisticsButton.setToolTipText("Подвести статистику заболеваний");
        toolBarButtons.add(statisticsButton);

        JButton certificateButton = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/sertif.png"));
        certificateButton.setToolTipText("Создать справку");
        toolBarButtons.add(certificateButton);
        certificateButton.addActionListener(event -> {
            c.SetVisible(true);
        });


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


    private JPanel tabInit(String[][] data) {
        JPanel mainPanel = new JPanel();
        String[] columns;
        if (data[0].length == 3) {
            columns = new String[]{"id", "Name", "Last Name", "Age", "Phone number", "Blood type"};
        } else {
            columns = new String[]{"id", "Name", "Last Name", " Specialization", "Age", "Phone number", "Work Days", "Work time", "Cabinet"};
        }
        String[] newArray = Arrays.copyOfRange(columns, 0, columns.length/2);
        DefaultTableModel model = new DefaultTableModel(data, newArray);



        JTable mainTable = new JTable(model);
        mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ListSelectionModel selModel = mainTable.getSelectionModel();
        CardBox cardBox = new CardBox(columns,this);

        selModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                cardBox.data = null;
                cardBox.updateCard();

                int[] selectedRows = mainTable.getSelectedRows();
                int selIndex = selectedRows[0];
                TableModel model = mainTable.getModel();
                Object value = model.getValueAt(selIndex, 0);

                EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_persistence");
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();
                Doctor doctor = em.find(Doctor.class, Integer.parseInt(value.toString()));
                String[] dataSelect;
                if (doctor == null) {
                    Patient patient = em.find(Patient.class, Integer.parseInt(value.toString()));
                    dataSelect = new String[5];
                    dataSelect[0] = String.valueOf(patient.getId());
                    dataSelect[1] = patient.getName() + " " + patient.getLastName();
                    dataSelect[2] = String.valueOf(patient.getAge());
                    dataSelect[3] = patient.getPhoneNumber();
                    dataSelect[4] = String.valueOf(patient.getBloodType());
                } else {
                    dataSelect = new String[8];
                    dataSelect[0] = String.valueOf(doctor.getId());
                    dataSelect[1] = doctor.getName() + " " + doctor.getLastName();
                    dataSelect[2] = doctor.getSpecialization();
                    dataSelect[3] = String.valueOf(doctor.getAge());
                    dataSelect[4] = doctor.getPhoneNumber();
                    dataSelect[5] = doctor.getWork_days();
                    dataSelect[6] = doctor.getWork_time();
                    dataSelect[7] = String.valueOf(doctor.getCabinet());
                }
                em.getTransaction().commit();
                cardBox.data = dataSelect;
                cardBox.updateCard();

            }

        });

        mainTable.setAutoCreateRowSorter(false);
//        sortToolBar.func(mainTable);
        JScrollPane scrolld = new JScrollPane(mainTable);
        mainPanel.setLayout(new

                BorderLayout());

//        mainPanel.add(sortToolBar, BorderLayout.NORTH);
        mainPanel.add(scrolld, BorderLayout.CENTER);
        mainPanel.add(cardBox, BorderLayout.EAST);
        return mainPanel;
    }


    public void updateTables() {

        ArrayList<String[]> dataDoctors = new ArrayList<>();
        ArrayList<String[]> dataPatients = new ArrayList<>();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_persistence");

        EntityManager em = emf.createEntityManager();

        System.out.println("Start test!");

        em.getTransaction().begin();
        Query queryd = em.createNativeQuery("SELECT * FROM Person where bd_type ='D'", Doctor.class);
        List<Doctor> listd = queryd.getResultList();
        for (Doctor doctor : listd) {
            String[] oneD = new String[4];
            oneD[0] = String.valueOf(doctor.getId());
            oneD[1] = doctor.getName();
            oneD[2] = doctor.getLastName();
            oneD[3] =  doctor.getSpecialization();
            dataDoctors.add(oneD);
        }

        Query queryp = em.createNativeQuery("SELECT * FROM Person where bd_type ='P'", Patient.class);
        List<Patient> listp = queryp.getResultList();
        for (Patient patient : listp) {
            String[] oneP = new String[3];
            oneP[0] = String.valueOf(patient.getId());
            oneP[1] = patient.getName();
            oneP[2] = patient.getLastName();

            System.out.println(Arrays.toString(oneP));
            dataPatients.add(oneP);
        }

        em.getTransaction().commit();

        tabsFolder.removeAll();
        MainWindow.add(tabsFolder, BorderLayout.CENTER);
        tabsFolder.addTab("Doctors", new ImageIcon("/Users/natalagrunskaa/Downloads/doctor.png"), tabInit(ArrLsttoStr(dataDoctors)), "Открыть таблицу докторов");
        tabsFolder.addTab("Patients", new ImageIcon("/Users/natalagrunskaa/Downloads/doctor.png"), tabInit(ArrLsttoStr(dataPatients)), "Открыть таблицу patients");
    }

    private String[][] ArrLsttoStr(ArrayList<String[]> n) {
        String[][] ans = new String[n.size()][n.get(0).length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = n.get(i);
        }
        return ans;
    }

    public void show() throws ParseException {

        MainWindow.setSize(1080, 720);
        MainWindow.setLocation(150, 100);
        MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainWindow.setVisible(true);
        MainWindow.setLayout(new BorderLayout());
        toolBarInit();
        MainWindow.add(toolBar, BorderLayout.NORTH);
        updateTables();
    }

    public static void main(String[] args) throws ParseException {
        NewMainWindow n = new NewMainWindow();
        n.show();
    }
}
