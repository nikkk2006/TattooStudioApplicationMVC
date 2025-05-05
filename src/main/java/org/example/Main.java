package org.example;

import org.example.controller.MainController;
import org.example.database.DatabaseManager;
import org.example.view.MainWindow;


public class Main {
    public static void main(String[] args) {
        // Проверяем, нужно ли инициализировать БД (например, по наличию файла или таблиц)
        if (!DatabaseManager.isDatabaseInitialized()) {
            DatabaseManager.initializeDatabase();
        }
        MainWindow mainView = new MainWindow();
        new MainController(mainView);
        mainView.setVisible(true);
    }
}