package org.example.controller;

import org.example.model.MasterModel;
import org.example.model.User;
import org.example.view.*;
import org.example.database.WorkDao;
import org.example.database.ScheduleDao;
import org.example.database.AppointmentDao;


public class MasterController {
    private final MasterModel model;
    private final MasterWindow view;
    private final LoginWindow loginView;
    private final User user;
    private final WorkDao workDao;
    private final ScheduleDao scheduleDao;
    private final AppointmentDao appointmentDao;

    public MasterController(MasterWindow view,
                            LoginWindow loginView,
                            User user,
                            WorkDao workDao,
                            ScheduleDao scheduleDao,
                            AppointmentDao appointmentDao) {
        this.view = view;
        this.loginView = loginView;
        this.user = user;
        this.workDao = workDao;
        this.scheduleDao = scheduleDao;
        this.appointmentDao = appointmentDao;

        // Проверяем, что user и его ID не null
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User и user ID не могут быть null");
        }

        this.model = new MasterModel(user.getId(), user.getName(), "Мастер");
        initController();
    }

    // Остальные методы остаются без изменений
    private void initController() {
        setupListeners();
    }

    private void setupListeners() {
        view.getViewScheduleButton().addActionListener(e -> handleViewSchedule());
        view.getLogoutButton().addActionListener(e -> returnToMain());
        view.getAddWorkButton().addActionListener(e -> handleAddWork());
    }

    private void handleAddWork() {
        WorkView workView = new WorkView();
        new WorkController(workView, workDao, model);
        workView.setLocationRelativeTo(view);
        workView.setVisible(true);
    }

    private void handleViewSchedule() {
        MasterScheduleWindow scheduleWindow = new MasterScheduleWindow(this, model.getId());
        scheduleWindow.setLocationRelativeTo(view);
        scheduleWindow.setVisible(true);
    }

    private void returnToMain() {
        view.dispose();
        MainWindow mainView = new MainWindow();
        new MainController(mainView);
        mainView.setVisible(true);
    }
}