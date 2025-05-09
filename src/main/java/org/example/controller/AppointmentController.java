package org.example.controller;

import org.example.database.AppointmentDao;
import org.example.model.User;
import org.example.view.AppointmentWindow;
import org.example.view.ClientWindow;

public class AppointmentController {
    private AppointmentWindow view;
    private User currentUser;
    private AppointmentDao appointmentDao;

    public AppointmentController(AppointmentWindow view, User currentUser, AppointmentDao appointmentDao) {
        this.view = view;
        this.currentUser = currentUser;
        this.appointmentDao = appointmentDao;

        setupListeners();
    }

    private void setupListeners() {
        view.getBackButton().addActionListener(e -> goBack());
        view.getRefreshButton().addActionListener(e -> refreshSchedule());
    }

    private void refreshSchedule() {
        // Здесь можно добавить логику обновления расписания
        // Например, перезагрузить данные из базы
    }

    private void goBack() {
        view.close();
        // Возвращаемся к предыдущему окну
        new ClientWindow(currentUser).setVisible(true);
    }
}