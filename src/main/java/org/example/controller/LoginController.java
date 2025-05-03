package org.example.controller;

import org.example.view.LoginWindow;
import org.example.view.MainWindow;
import org.example.view.RegisterWindow;

public class LoginController {
    private final LoginWindow view;

    public LoginController(LoginWindow view) {
        this.view = view;
        setupEventListeners();
    }

    private void setupEventListeners() {
        view.getLoginButton().addActionListener(e -> {
            MainWindow mainView = new MainWindow();
            new MainController(mainView);
            view.close(); // Унифицированный метод
            mainView.setVisible(true);
        });

        view.getRegisterButton().addActionListener(e -> {
            RegisterWindow registerView = new RegisterWindow();
            new RegisterController(registerView);
            view.close(); // Унифицированный метод
            registerView.setVisible(true);
        });

        view.getBackButton().addActionListener(e -> {
            MainWindow mainView = new MainWindow();
            new MainController(mainView);
            view.close(); // Унифицированный метод
            mainView.setVisible(true);
        });
    }
}