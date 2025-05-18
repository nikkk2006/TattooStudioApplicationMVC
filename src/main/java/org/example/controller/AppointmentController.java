package org.example.controller;

import org.example.database.AppointmentDao;
import org.example.database.ScheduleDao;
import org.example.database.UserDao;
import org.example.model.UserModel;
import org.example.view.AppointmentWindow;
import org.example.view.ClientWindow;


public class AppointmentController {
    private AppointmentWindow view;
    private UserModel currentUser;
    private AppointmentDao appointmentDao;

    public AppointmentController(AppointmentWindow view, UserModel currentUser, AppointmentDao appointmentDao) {
        this.view = view;
        this.currentUser = currentUser;
        this.appointmentDao = appointmentDao;

        setupListeners();
    }

    private void setupListeners() {
        view.getBackButton().addActionListener(e -> goBack());
    }

    private void goBack() {
        view.close();
        ClientWindow clientWindowView = new ClientWindow(currentUser);
        UserDao userDao = new UserDao();
        ScheduleDao scheduleDao = new ScheduleDao();
        new ClientController(clientWindowView, currentUser, appointmentDao, userDao, scheduleDao);
        clientWindowView.setVisible(true);
    }
}