package org.example;

import org.example.controller.MainController;
import org.example.view.MainWindow;

public class Main {
    public static void main(String[] args) {
        MainWindow view = new MainWindow();
        new MainController(view);
        view.setVisible(true);
    }
}