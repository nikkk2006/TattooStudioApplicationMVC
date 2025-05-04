package org.example.controller;

import org.example.model.MasterModel;
import org.example.model.User;
import org.example.view.MasterWindow;
import org.example.view.LoginWindow;


public class MasterController {
    private MasterModel model;
    private MasterWindow view;
    private LoginWindow loginView;
    private final User user;

    public MasterController(MasterWindow view, User user) {
        this.view = view;
        this.user = user;
        setupListeners();
    }

    private void setupListeners() {
        view.getViewScheduleButton().addActionListener(e -> handleViewSchedule());
        view.getLogoutButton().addActionListener(e -> handleLogout());
    }

    private void handleViewSchedule() {
        view.showMessage("Расписание (реализуйте логику)");
    }


    private void handleLogout() {
        view.dispose();
        loginView.setVisible(true);
    }
}
