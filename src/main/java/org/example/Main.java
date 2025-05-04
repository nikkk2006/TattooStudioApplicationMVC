package org.example;

import org.example.controller.MasterController;
import org.example.model.MasterModel;
import org.example.view.LoginWindow;
import org.example.view.LoginWindow;
import org.example.view.MasterWindow;
import org.example.view.MasterWindow;

public class Main {
    public static void main(String[] args) {
        String masterName = "Master Nikita";

        // Создаём компоненты MVC
        MasterModel model = new MasterModel(masterName);
        MasterWindow view = new MasterWindow(masterName);
        LoginWindow loginView = new LoginWindow(); // Окно авторизации

        // Создаём контроллер
        new MasterController(model, view, loginView);

        // Показываем окно мастера
        view.setVisible(true);

        // Можно скрыть окно логина, если оно было
        loginView.setVisible(false);
    }
}