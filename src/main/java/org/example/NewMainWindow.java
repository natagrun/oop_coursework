package org.example;

import javax.swing.*;

public class NewMainWindow {
    private JFrame MainWindow;

    public void show(){
        MainWindow = new JFrame("CourseWork");
        MainWindow.setSize(500, 300);
        MainWindow.setLocation(100, 100);
        MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(){
        NewMainWindow n = new NewMainWindow();
        n.show();
    }
}
