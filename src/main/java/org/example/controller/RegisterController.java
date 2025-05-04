package org.example.controller;

import org.example.view.RegisterWindow;
import org.example.view.LoginWindow;
import org.example.view.MainWindow;
import org.example.database.UserDao;
import org.example.model.User;

public class RegisterController {
    private final RegisterWindow view;
    private final UserDao userDao; // Добавляем DAO

    public RegisterController(RegisterWindow view) {
        this.view = view;
        this.userDao = new UserDao(); // Инициализируем DAO
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

        // Проверка email на валидность (базовая проверка)
        if (!view.getEmail().contains("@") || !view.getEmail().contains(".")) {
            view.showError("Введите корректный email");
            return;
        }

        // Проверка, что пользователь с таким email не существует
        if (userDao.getUserByEmail(view.getEmail()) != null) {
            view.showError("Пользователь с таким email уже зарегистрирован");
            return;
        }

        // Создаем нового пользователя
        User newUser = new User(
                view.getName(),
                view.getEmail(),
                view.getPassword(),
                "CLIENT" // Роль по умолчанию (или можно выбрать из view)
        );

        // Пытаемся сохранить пользователя
        if (userDao.createUser(newUser)) {
            view.showSuccess("Регистрация прошла успешно!");
            openLoginWindow(); // Перенаправляем на окно входа
        } else {
            view.showError("Ошибка при регистрации. Попробуйте позже.");
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