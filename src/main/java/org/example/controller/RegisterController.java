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
        view.getRegisterButton().addActionListener(e -> {
            // Теперь можем получить выбранную роль
            String role = view.getSelectedRole();
            view.showSuccess("Регистрация как " + role + " (заглушка)");
            openLoginWindow();
        });

        view.getLoginButton().addActionListener(e -> openLoginWindow());
        view.getBackButton().addActionListener(e -> returnToMain());
    }

    private void openLoginWindow() {
        LoginWindow loginView = new LoginWindow();
        new LoginController(loginView);
        view.close(); // Используем единый метод
        loginView.setVisible(true);
    }

    private void returnToMain() {
        MainWindow mainView = new MainWindow();
        new MainController(mainView);
        view.close(); // Используем единый метод
        mainView.setVisible(true);
    }
}