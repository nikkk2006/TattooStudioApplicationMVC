// AppointmentController.java
package org.example.controller;

import org.example.database.AppointmentDao;
import org.example.database.ScheduleDao;
import org.example.database.UserDao;
import org.example.model.User;
import org.example.view.AppointmentWindow;
import org.example.view.ClientWindow;


public class AppointmentController {
    private final AppointmentWindow view;
    private User user;
    private final AppointmentDao appointmentDao;
    private final ScheduleDao scheduleDao;

    public AppointmentController(AppointmentWindow view, User user) {
        this.view = view;
        this.user = user;
        this.appointmentDao = new AppointmentDao();
        this.scheduleDao = new ScheduleDao();

        setupEventListeners();
    }

    private void setupEventListeners() {
        view.getBackButton().addActionListener(e -> returnToClientWindow());
    }

    private void returnToClientWindow() {
        ClientWindow clientView = new ClientWindow(user);
        // Инициализируем DAO для ClientController
        AppointmentDao appointmentDao = new AppointmentDao();
        UserDao userDao = new UserDao();
        ScheduleDao scheduleDao = new ScheduleDao();
        new ClientController(clientView, user, appointmentDao, userDao, scheduleDao);
        view.dispose();
        clientView.setVisible(true);
    }

}