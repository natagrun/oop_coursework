package org.example;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewMainWindow {

    static final Service service = new Service();

    private final JFrame MainWindow = new JFrame("CourseWork");
    private final ArrayList<JButton> toolBarButtons = new ArrayList<>();
    private final JTabbedPane tabsFolder = new JTabbedPane(JTabbedPane.LEFT);
    private JToolBar toolBar;

    public void SetVisible(boolean flag) {
        MainWindow.setVisible(flag);
        updateTables();
    }

    public void openEditWin(int data, int code) {
        this.updateTables();
        EditWindow editWindow = new EditWindow(this, data);
        editWindow.setEditWindowDoc(code);
        editWindow.SetVisible(true);

    }

    private void toolBarInit() {
        AddObject n = new AddObject(this);
        n.setAddWindow();


        toolBar = new JToolBar("Панель");
        toolBar.setFloatable(false);

        JButton newButton = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/new.png"));
        newButton.setToolTipText("Добавить нового доктора/пациента");
        toolBarButtons.add(newButton);

        newButton.addActionListener(event -> n.setVisible(true));

        JButton statisticsButton = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/stat.png"));
        statisticsButton.setToolTipText("Подвести статистику заболеваний");
        toolBarButtons.add(statisticsButton);
        statisticsButton.addActionListener(event -> {
            StatWin statWin = new StatWin();
            statWin.show();
        });
        JButton saveButton = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/save.png"));
        saveButton.setToolTipText("Save xml");
        toolBarButtons.add(saveButton);

        saveButton.addActionListener(event -> {
            try {
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = builder.newDocument();
                Node booklist = doc.createElement("Hospital");
                doc.appendChild(booklist);
                List<Doctor> listd = service.getAllDoctors();
                for (Doctor doctor : listd) {
                    Element doctors = doc.createElement("doctors");
                    booklist.appendChild(doctors);
                    doctors.setAttribute("Name", doctor.getName());
                    doctors.setAttribute("Last_Name", doctor.getLastName());
                    doctors.setAttribute("Age", String.valueOf(doctor.getAge()));
                    doctors.setAttribute("Phone", doctor.getPhoneNumber());
                    doctors.setAttribute("Spec", doctor.getSpecialization());
                    doctors.setAttribute("WorkDays", doctor.getWork_days());
                    doctors.setAttribute("WorkTime", doctor.getWork_time());
                    doctors.setAttribute("Cabinet", String.valueOf(doctor.getCabinet()));


                }

                List<Patient> listp = service.getAllPatients();
                for (Patient patient : listp) {
                    Element patietns = doc.createElement("patients");
                    booklist.appendChild(patietns);
                    patietns.setAttribute("Name", patient.getName());
                    patietns.setAttribute("Last_Name", patient.getLastName());
                    patietns.setAttribute("Age", String.valueOf(patient.getAge()));
                    patietns.setAttribute("Phone", patient.getPhoneNumber());
                    patietns.setAttribute("BloodType", String.valueOf(patient.getBloodType()));

                }
                List<Disease> listdis = service.getAllDiseases();
                for (Disease disease : listdis) {
                    Element diss = doc.createElement("diseases");
                    booklist.appendChild(diss);
                    diss.setAttribute("Name", disease.getName());
                }
                try {

                    Transformer trans = TransformerFactory.newInstance().newTransformer();
                    FileWriter fw = new FileWriter("Hospital.xml");
                    trans.transform(new DOMSource(doc), new StreamResult(fw));

                } catch (TransformerException | IOException e) {
                    e.printStackTrace();
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        });
        JButton uploadButton = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/doctor.png"));
        uploadButton.setToolTipText("хз не помню о чем я думала кода я создавала эту кнопку");

        uploadButton.addActionListener(event -> {
            Document doc = null;
            try {
                DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                doc = dBuilder.parse(new File("save1.xml"));
                doc.getDocumentElement().normalize();
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
            assert doc != null;
            NodeList dlBooks = doc.getElementsByTagName("doctors");
            for (int temp = 0; temp < dlBooks.getLength(); temp++) { // Выбор очередного элемента списка
                Node elem = dlBooks.item(temp);
                NamedNodeMap attrs = elem.getAttributes();
                service.addDoctor(attrs.getNamedItem("Name").getNodeValue(), attrs.getNamedItem("Last_Name").getNodeValue(), Integer.parseInt(attrs.getNamedItem("Age").getNodeValue()), attrs.getNamedItem("Phone").getNodeValue(), attrs.getNamedItem("Spec").getNodeValue(), Integer.parseInt(attrs.getNamedItem("Cabinet").getNodeValue())
                        , attrs.getNamedItem("WorkDays").getNodeValue(), attrs.getNamedItem("WorkTime").getNodeValue());

            }

            NodeList plBooks = doc.getElementsByTagName("patients");
            for (int temp = 0; temp < plBooks.getLength(); temp++) { // Выбор очередного элемента списка
                Node elem = plBooks.item(temp);
                NamedNodeMap attrs = elem.getAttributes();
                service.addPatient(attrs.getNamedItem("Name").getNodeValue(), attrs.getNamedItem("Last_Name").getNodeValue(), Integer.parseInt(attrs.getNamedItem("Age").getNodeValue()), attrs.getNamedItem("Phone").getNodeValue(),Integer.parseInt(attrs.getNamedItem("BloodType").getNodeValue()));

            }
            NodeList diseases = doc.getElementsByTagName("diseases");
            for (int temp = 0; temp < diseases.getLength(); temp++) { // Выбор очередного элемента списка
                Node elem = diseases.item(temp);
                NamedNodeMap attrs = elem.getAttributes();
                service.addDisease(attrs.getNamedItem("Name").getNodeValue());
            }
            updateTables();
        });
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
        String[] newArray = Arrays.copyOfRange(columns, 0, columns.length / 2);
        DefaultTableModel model = new DefaultTableModel(data, newArray);
        JTable mainTable = new JTable(model);
        mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ListSelectionModel selModel = mainTable.getSelectionModel();
        CardBox cardBox = new CardBox(columns, this);

        selModel.addListSelectionListener(e -> {
            cardBox.data = null;
            cardBox.updateCard();
            int[] selectedRows = mainTable.getSelectedRows();
            int selIndex = selectedRows[0];
            TableModel model1 = mainTable.getModel();
            Object value = model1.getValueAt(selIndex, 0);

            Doctor doctor = service.getDoctor(Integer.parseInt(value.toString()));
            String[] dataSelect;
            if (doctor == null) {
                Patient patient = service.getPatient(Integer.parseInt(value.toString()));
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
            cardBox.data = dataSelect;
            cardBox.updateCard();

        });

        mainTable.setAutoCreateRowSorter(false);
        JScrollPane scrolld = new JScrollPane(mainTable);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(scrolld, BorderLayout.CENTER);
        mainPanel.add(cardBox, BorderLayout.EAST);
        return mainPanel;
    }


    public void updateTables() {
        ArrayList<String[]> dataDoctors = new ArrayList<>();
        ArrayList<String[]> dataPatients = new ArrayList<>();
        List<Doctor> listd = service.getAllDoctors();
        for (Doctor doctor : listd) {
            String[] oneD = new String[4];
            oneD[0] = String.valueOf(doctor.getId());
            oneD[1] = doctor.getName();
            oneD[2] = doctor.getLastName();
            oneD[3] = doctor.getSpecialization();
            dataDoctors.add(oneD);
        }
        List<Patient> listp = service.getAllPatients();
        for (Patient patient : listp) {
            String[] oneP = new String[3];
            oneP[0] = String.valueOf(patient.getId());
            oneP[1] = patient.getName();
            oneP[2] = patient.getLastName();
            dataPatients.add(oneP);
        }
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

    public void show() {
        MainWindow.setSize(1080, 720);
        MainWindow.setLocation(150, 100);
        MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainWindow.setVisible(true);
        MainWindow.setLayout(new BorderLayout());
        toolBarInit();
        MainWindow.add(toolBar, BorderLayout.NORTH);
        updateTables();
    }

    public static void main(String[] args) {
        NewMainWindow n = new NewMainWindow();
        service.startConnection();
        n.show();
    }
}
