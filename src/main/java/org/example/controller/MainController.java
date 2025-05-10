package org.example.controller;

import org.example.view.MainWindow;
import org.example.view.LoginWindow;
import org.example.view.RegisterWindow;


public class MainController {
    private final MainWindow view;

    public MainController(MainWindow view) {
        this.view = view;
        setupEventListeners();
    }

    private void setupEventListeners() {
        // Обработчик для кнопки входа
        view.getLoginButton().addActionListener(e -> {
            LoginWindow loginView = new LoginWindow();
            new LoginController(loginView);
            view.close();
            loginView.setVisible(true);
        });

        // Обработчик для кнопки регистрации (исправленный)
        view.getRegisterButton().addActionListener(e -> {
            RegisterWindow registerView = new RegisterWindow();
            new RegisterController(registerView);  // Создаем контроллер
            view.close();
            registerView.setVisible(true);  // Показываем новое окно
        });
    }
}