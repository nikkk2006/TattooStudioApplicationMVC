package org.example.controller;

import org.example.database.AppointmentDao;
import org.example.database.ScheduleDao;
import org.example.database.WorkDao;
import org.example.view.RegisterWindow;
import org.example.view.LoginWindow;
import org.example.view.MainWindow;
import org.example.view.MasterWindow;
import org.example.database.UserDao;
import org.example.model.UserModel;


public class RegisterController {
    private final RegisterWindow view;
    private final UserDao userDao;
    private final WorkDao workDao;
    private final ScheduleDao scheduleDao;
    private final AppointmentDao appointmentDao;

    public RegisterController(RegisterWindow view) {
        this.view = view;
        this.userDao = new UserDao();
        this.workDao = new WorkDao();
        this.scheduleDao = new ScheduleDao();
        this.appointmentDao = new AppointmentDao();
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

        // Проверка email
        if (!isValidEmail(view.getEmail())) {
            view.showError("Введите корректный email");
            return;
        }

        // Проверка существования пользователя
        if (userDao.getUserByEmail(view.getEmail()) != null) {
            view.showError("Пользователь с таким email уже зарегистрирован");
            return;
        }

        // Создаем нового пользователя (без ID)
        UserModel newUser = new UserModel(
                view.getName(),
                view.getEmail(),
                view.getPassword(),
                convertRoleToDatabaseFormat(view.getSelectedRole())
        );

        // Сохраняем пользователя и получаем обновленный объект с ID
        if (userDao.createUser(newUser)) {
            view.showSuccess("Регистрация прошла успешно!");

            // Теперь newUser имеет установленный ID
            if ("MASTER".equals(newUser.getRole())) {
                openMasterWindow(newUser);
            } else {
                openLoginWindow();
            }
        } else {
            view.showError("Ошибка при регистрации. Попробуйте позже.");
        }
    }

    private String convertRoleToDatabaseFormat(String selectedRole) {
        // Преобразуем "Клиент"/"Мастер" в "CLIENT"/"MASTER"
        return "Мастер".equals(selectedRole) ? "MASTER" : "CLIENT";
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }

    private void openMasterWindow(UserModel user) {
        MasterWindow masterView = new MasterWindow(user);
        LoginWindow loginView = new LoginWindow(); // Для возврата после logout

        new MasterController(
                masterView,
                loginView,
                user,
                workDao,
                scheduleDao,
                appointmentDao
        );

        view.close();
        masterView.setVisible(true);
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