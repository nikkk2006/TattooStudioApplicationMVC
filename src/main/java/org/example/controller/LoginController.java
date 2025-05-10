package org.example.controller;

import org.example.view.*;
import org.example.database.*;
import org.example.model.User;


public class LoginController {
    private final LoginWindow view;
    private final UserDao userDao;

    public LoginController(LoginWindow view) {
        this.view = view;
        this.userDao = new UserDao();
        setupEventListeners();
    }

    private void setupEventListeners() {
        view.getLoginButton().addActionListener(e -> handleLogin());
        view.getRegisterButton().addActionListener(e -> openRegisterWindow());
        view.getBackButton().addActionListener(e -> returnToMain());
    }

    private void handleLogin() {
        if (view.getEmail().isEmpty() || view.getPassword().isEmpty()) {
            view.showError("Заполните все поля");
            return;
        }

        User user = userDao.getUserByEmail(view.getEmail());

        if (user == null) {
            view.showError("Пользователь с таким email не найден");
            return;
        }

        if (!user.getPassword().equals(view.getPassword())) {
            view.showError("Неверный пароль");
            return;
        }

        view.showSuccess("Вход выполнен успешно!");
        redirectAfterLogin(user);
    }

    private void redirectAfterLogin(User user) {
        view.close();

        switch (user.getRole().toUpperCase()) {
            case "CLIENT":
                ClientWindow clientView = new ClientWindow(user);
                // Инициализируем DAO для ClientController
                AppointmentDao appointmentDao = new AppointmentDao();
                UserDao clientUserDao = new UserDao();
                ScheduleDao scheduleDao = new ScheduleDao();
                new ClientController(clientView, user, appointmentDao, clientUserDao, scheduleDao);
                clientView.setVisible(true);
                break;

            case "MASTER":
                MasterWindow masterView = new MasterWindow(user);
                LoginWindow loginView = new LoginWindow();

                // Инициализируем DAO
                WorkDao workDao = new WorkDao();
                ScheduleDao masterScheduleDao = new ScheduleDao();
                AppointmentDao masterAppointmentDao = new AppointmentDao();

                new MasterController(
                        masterView,
                        loginView,
                        user,
                        workDao,
                        masterScheduleDao,
                        masterAppointmentDao
                );
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