// AppointmentController.java
package org.example.controller;

import org.example.database.AppointmentDao;
import org.example.database.ScheduleDao;
import org.example.view.AppointmentWindow;
import org.example.view.MainWindow;

public class AppointmentController {
    private final AppointmentWindow view;
    private final AppointmentDao appointmentDao;
    private final ScheduleDao scheduleDao;

    public AppointmentController(AppointmentWindow view) {
        this.view = view;
        this.appointmentDao = new AppointmentDao();
        this.scheduleDao = new ScheduleDao();

        setupEventListeners();
    }

    private void setupEventListeners() {
        view.getBackButton().addActionListener(e -> returnToMain());
    }

    private void returnToMain() {
        MainWindow mainView = new MainWindow();
        new MainController(mainView);
        view.close();
        mainView.setVisible(true);
    }

}