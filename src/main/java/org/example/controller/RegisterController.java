package org.example.controller;

import org.example.view.RegisterWindow;
import org.example.view.LoginWindow;
import org.example.view.MainWindow;

public class RegisterController {
    private final RegisterWindow view;

    public RegisterController(RegisterWindow view) {
        this.view = view;
        setupEventListeners();
    }

    private void setupEventListeners() {
        view.getRegisterButton().addActionListener(e -> handleRegistration());
        view.getLoginButton().addActionListener(e -> openLoginWindow());
        view.getBackButton().addActionListener(e -> returnToMain());
    }

    private void handleRegistration() {
        // Проверка заполнения полей
        if (view.getName().isEmpty() || view.getEmail().isEmpty() ||
                view.getPassword().isEmpty() || view.getConfirmPassword().isEmpty()) {
            view.showError("Заполните все поля");
            return;
        }

        // Проверка совпадения паролей
        if (!view.getPassword().equals(view.getConfirmPassword())) {
            view.showError("Пароли не совпадают");
            return;
        }

    }

    private void openLoginWindow() {
        LoginWindow loginView = new LoginWindow();
        new LoginController(loginView);
        view.close();
        loginView.setVisible(true);
    }

    private void returnToMain() {
        MainWindow mainView = new MainWindow();
        new MainController(mainView);
        view.close();
        mainView.setVisible(true);
    }
}