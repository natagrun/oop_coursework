package org.example;

import  java.util.Random;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.Path;
import org.w3c.dom.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;


import org.apache.log4j.Logger;

import static com.sun.xml.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;


//класс создания главного окна
public class MainWindow {

    private final Color[] colors = {Color.cyan, Color.orange, Color.magenta, Color.blue,
            Color.red, Color.green, Color.yellow, Color.pink};
    private JFrame hospital;
    private DefaultTableModel modeld;
    private DefaultTableModel modelp;

    String[] list_tabled = {"Name", "Last Name", "Age", "Phone", "Specialization", "Work days", "Work time", "Cabinet"};
    String[] list_tablep = {"Name", "Last Name", "Age", "Phone", "Blood type"};

    private JTable doctors;
    private JTable patients;

    private JButton new_button;
    private JButton search_button;
    private JButton write_button;
    private JButton stat_button;
    private JButton sertif_button;
    private JButton save_button;
    private JButton upload_button;

    private JToolBar toolBar;
    private JScrollPane scrolld;
    private JScrollPane scrollp;

    final Random random = new Random();



    private static final Logger log = Logger.getLogger("MainWindow.class");


    private ArrayList<JButton> buttons = new ArrayList<JButton>();

    private BaseColor[] colorst = {BaseColor.RED, BaseColor.CYAN, BaseColor.BLACK, BaseColor.WHITE, BaseColor.LIGHT_GRAY, BaseColor.BLUE, BaseColor.GREEN, BaseColor.MAGENTA, BaseColor.ORANGE, BaseColor.PINK, BaseColor.YELLOW};

    Font font = FontFactory.getFont("arial.ttf", "cp1251", BaseFont.EMBEDDED, 10);

    private void fill_table(PdfPTable table, DefaultTableModel model, String[] listt) {
        Stream.of(listt)
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(colorst[random.nextInt(0,11)]);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });

        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < listt.length; j++) {
                Paragraph paragraph = new Paragraph();
                paragraph.add(new Paragraph((String) model.getValueAt(i, j)));
                paragraph.setFont(font);
                table.addCell(paragraph);
            }
        }
    }


    //создание окна и элементов на нем
    public void show() {

        hospital = new JFrame("Заходит как-то улитка в бар");
        hospital.setSize(1080, 720);
        hospital.setLocation(100, 100);
        hospital.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new_button = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/new.png"));
        new_button.setToolTipText("Добавить нового доктора/пациента");
        buttons.add(new_button);
        search_button = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/search.png"));
        search_button.setToolTipText("Найти доктора по..");
        buttons.add(search_button);
        write_button = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/write.png"));
        write_button.setToolTipText("Записать пациента к врачу..");
        buttons.add(write_button);
        stat_button = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/stat.png"));
        stat_button.setToolTipText("Подвести статистику заболеваний");
        buttons.add(stat_button);
        sertif_button = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/sertif.png"));
        sertif_button.setToolTipText("Создать справку");
        buttons.add(sertif_button);
        save_button = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/save.png"));
        save_button.setToolTipText("хз не помню о чем я думала кода я создавала эту кнопку");
        buttons.add(save_button);

        upload_button = new JButton(new ImageIcon("/Users/natalagrunskaa/Downloads/upload.png"));
        upload_button.setToolTipText("хз не помню о чем я думала кода я создавала эту кнопку");
        buttons.add(upload_button);




        log.info("Открытие главного окна");


        // слушатель кнопки для открытия окна добавления
        new_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                log.info("Нажатие кнопки +");

                AddWindow a = new AddWindow();
                a.show();
                log.info("Открытие окна добавления");
                hospital.setVisible(false);
            }
        });

        // слушатель кнопки для открытия окна поиска
        search_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                log.info("Нажатие кнопки поиска");
                SearchWindow a = new SearchWindow();
                a.show();
                log.info("Открытие окна поиска");
                hospital.setVisible(false);

            }
        });

        // слушатель кнопки для открытия окна поиска
        save_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                log.info("Нажатие кнопки сохранения в файл");
                try {
                    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    Document doc = builder.newDocument();
                    Node booklist = doc.createElement("booklist");
                    doc.appendChild(booklist);
                    for (int i = 0; i < modeld.getRowCount(); i++) {
                        Element doctors = doc.createElement("doctors");
                        booklist.appendChild(doctors);
                        doctors.setAttribute("Name", (String) modeld.getValueAt(i, 0));
                        doctors.setAttribute("Last_Name", (String) modeld.getValueAt(i, 1));
                        doctors.setAttribute("Age", (String) modeld.getValueAt(i, 2));
                        doctors.setAttribute("Phone", (String) modeld.getValueAt(i, 3));
                        doctors.setAttribute("Spec", (String) modeld.getValueAt(i, 4));
                        doctors.setAttribute("WorkDays", (String) modeld.getValueAt(i, 5));
                        doctors.setAttribute("WorkTime", (String) modeld.getValueAt(i, 6));
                        doctors.setAttribute("Cabinet", (String) modeld.getValueAt(i, 7));

                    }

                    for (int i = 0; i < modelp.getRowCount(); i++) {
                        Element patietns = doc.createElement("patients");
                        booklist.appendChild(patietns);
                        patietns.setAttribute("Name", (String) modelp.getValueAt(i, 0));
                        patietns.setAttribute("Last_Name", (String) modelp.getValueAt(i, 1));
                        patietns.setAttribute("Age", (String) modelp.getValueAt(i, 2));
                        patietns.setAttribute("Phone", (String) modelp.getValueAt(i, 3));
                        patietns.setAttribute("BloodType", (String) modelp.getValueAt(i, 4));

                    }
                    try {
// Создание преобразователя документа
                        Transformer trans = TransformerFactory.newInstance().newTransformer(); // Создание файла с именем books.xml для записи документа
                        java.io.FileWriter fw = new FileWriter("books.xml");
// Запись документа в файл
                        trans.transform(new DOMSource(doc), new StreamResult(fw));

                    } catch (TransformerConfigurationException e) {
                        log.warn("Ошибка сохранения данных");
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        log.warn("Ошибка сохранения данных");
                        e.printStackTrace();
                    } catch (IOException e) {
                        log.warn("Ошибка сохранения данных");
                        e.printStackTrace();
                    }


                    log.info("Данные успешно сохранены в файл");
                } catch (ParserConfigurationException e) {
                    log.warn("Ошибка сохранения данных");
                    e.printStackTrace();
                }


            }

        });


        // слушатель кнопки для открытия окна поиска
        upload_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                log.info("Нажание кнопки загрузки данных");
                Document doc = null;
                try {

                    DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    doc = dBuilder.parse(new File("save.xml"));
// Нормализация документа
                    doc.getDocumentElement().normalize();

                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } // Обработка ошибки парсера при чтении данных из XML-файла
                catch (SAXException e) {
                    log.warn("Ошибка загрузки данных");
                    e.printStackTrace();
                } catch (IOException e) {
                    log.warn("Ошибка загрузки данных");
                    e.printStackTrace();
                }
                NodeList dlBooks = doc.getElementsByTagName("doctors");
                for (int temp = 0; temp < dlBooks.getLength(); temp++) { // Выбор очередного элемента списка
                    Node elem = dlBooks.item(temp);
                    NamedNodeMap attrs = elem.getAttributes();
                    String Name = attrs.getNamedItem("Name").getNodeValue();
                    String Last_Name = attrs.getNamedItem("Last_Name").getNodeValue();
                    String Age = attrs.getNamedItem("Age").getNodeValue();
                    String Phone = attrs.getNamedItem("Phone").getNodeValue();
                    String Spec = attrs.getNamedItem("Spec").getNodeValue();
                    String WorkDays = attrs.getNamedItem("WorkDays").getNodeValue();
                    String WorkTime = attrs.getNamedItem("WorkTime").getNodeValue();
                    String Cabinet = attrs.getNamedItem("Cabinet").getNodeValue();
                    modeld.addRow(new String[]{Name, Last_Name, Age, Phone, Spec, WorkDays, WorkTime, Cabinet});
                }

                NodeList plBooks = doc.getElementsByTagName("patients");
                for (int temp = 0; temp < plBooks.getLength(); temp++) { // Выбор очередного элемента списка
                    Node elem = plBooks.item(temp);
                    NamedNodeMap attrs = elem.getAttributes();
                    String Name = attrs.getNamedItem("Name").getNodeValue();
                    String Last_Name = attrs.getNamedItem("Last_Name").getNodeValue();
                    String Age = attrs.getNamedItem("Age").getNodeValue();
                    String Phone = attrs.getNamedItem("Phone").getNodeValue();
                    String BloodType = attrs.getNamedItem("BloodType").getNodeValue();
                    modelp.addRow(new String[]{Name, Last_Name, Age, Phone, BloodType});
                }

                log.info("Данные успешно загружены");
            }
        });


        stat_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                log.info("Нажатие кнопки для предоставления пдф отчета");

                com.itextpdf.text.Document document = new com.itextpdf.text.Document();

                try {
                    PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }


                document.open();


                String para = "Lab report";
                Paragraph parag = new Paragraph(para);
                Font f = new Font(Font.FontFamily.TIMES_ROMAN, 50.0f, Font.UNDERLINE, BaseColor.RED);
                parag.setSpacingAfter(20.0f);
                parag.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED);

                PdfPTable tabled = new PdfPTable(8);
                PdfPTable tablep = new PdfPTable(5);
                fill_table(tabled, modeld, list_tabled);
                fill_table(tablep, modelp, list_tablep);

                tabled.setSpacingAfter(30.0f);


                try {
                    document.add(parag);
                    document.add(tabled);
                    document.add(tablep);
                    log.info("Пдф отчет готов");

                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                }

                document.close();

            }
        });


        toolBar = new JToolBar("Панель");

        for (JButton b : buttons) {
            toolBar.addSeparator();
            toolBar.add(b);

        }

        toolBar.setFloatable(false);
        hospital.setLayout(new BorderLayout());
        hospital.add(toolBar, BorderLayout.NORTH);


        JTabbedPane tabsRight = new JTabbedPane(JTabbedPane.LEFT);
        hospital.add(tabsRight, BorderLayout.CENTER);


        JPanel doc_panel = new JPanel();
        doc_panel.setBackground(colors[7]);

        String[] columnsd = {"Имя", "Фамилия", "Возраст", "Номер телефона", " Специализация", "Рабочие дни", "Время приема", "Номер кабинета"};
        String[][] datad = {{"Vlass", "Vozmitel", "19", "9583646583", " Heartbreaker", "Monday, Saturday", "9:50-17:20", "1381"},
                {"Alex", "Markush", "18", "3948573355", " Suspends smth", "Monday, Friday", "15:30-17:00", "1304"},
                {"Max", "Fastest", "19", "574389458", " Krag", "Wednesday", "13:30-18:00", "4578"},
                {"Mishk", "pigeon", "21", "2934866", " mmm boy", "Friday", "7:30-19:00", "1307"}};

        modeld = new DefaultTableModel(datad, columnsd);
        doctors = new JTable(modeld);
        scrolld = new JScrollPane(doctors);
        tabsRight.addTab("Доктора", new ImageIcon("/Users/natalagrunskaa/Downloads/doctor.png"), scrolld, "Открыть таблицу докторов");


        JPanel pat_panel = new JPanel();
        pat_panel.setBackground(colors[2]);
        String[] columnsp = {"Имя", "Фамилия", "Возраст", "Номер телефона", "Группа крови"};
        String[][] datap = {{"Vlass", "Vozmitel", "19", "9583646583", " 1"},
                {"Alex", "Markush", "18", "3948573355", " 2"}};
        modelp = new DefaultTableModel(datap, columnsp);
        patients = new JTable(modelp);
        scrollp = new JScrollPane(patients);

        pat_panel.add(scrollp, BorderLayout.CENTER);
        tabsRight.addTab("Пациенты", new ImageIcon("/Users/natalagrunskaa/Downloads/doctor.png"),
                scrollp, "Открыть таблицу пациентов");


        hospital.setVisible(true);
    }

    //мейн для создания окна
    public static void main(String[] args) {
        new MainWindow().show();
    }

}