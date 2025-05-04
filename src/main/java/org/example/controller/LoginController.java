package org.example.controller;

import org.example.view.*;
import org.example.database.UserDao;
import org.example.model.User;

public class LoginController {
    private final LoginWindow view;
    private final UserDao userDao;

    public LoginController(LoginWindow view) {
        this.view = view;
        this.userDao = new UserDao(); // Инициализация DAO
        setupEventListeners();
    }

    private void setupEventListeners() {
        view.getLoginButton().addActionListener(e -> handleLogin());
        view.getRegisterButton().addActionListener(e -> openRegisterWindow());
        view.getBackButton().addActionListener(e -> returnToMain());
    }

    private void handleLogin() {
        // Проверка заполнения полей
        if (view.getEmail().isEmpty() || view.getPassword().isEmpty()) {
            view.showError("Заполните все поля");
            return;
        }

        // Аутентификация пользователя
        User user = userDao.getUserByEmail(view.getEmail());

        if (user == null) {
            view.showError("Пользователь с таким email не найден");
            return;
        }

        if (!user.getPassword().equals(view.getPassword())) {
            view.showError("Неверный пароль");
            return;
        }

        // Успешная аутентификация
        view.showSuccess("Вход выполнен успешно!");

        // Перенаправление в зависимости от роли
        redirectAfterLogin(user);
    }

    private void redirectAfterLogin(User user) {
        view.close();

        switch (user.getRole().toUpperCase()) {
            case "CLIENT":
                ClientWindow clientView = new ClientWindow(user);
                new ClientController(clientView, user);
                clientView.setVisible(true);
                break;

            case "MASTER":
                MasterWindow masterView = new MasterWindow(user);
                new MasterController(masterView, user);
                masterView.setVisible(true);
                break;

            default:
                MainWindow mainView = new MainWindow();
                new MainController(mainView);
                mainView.setVisible(true);
        }
    }

    private void openRegisterWindow() {
        RegisterWindow registerView = new RegisterWindow();
        new RegisterController(registerView);
        view.close();
        registerView.setVisible(true);
    }

    private void returnToMain() {
        MainWindow mainView = new MainWindow();
        new MainController(mainView);
        view.close();
        mainView.setVisible(true);
    }
}