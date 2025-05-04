package org.example.controller;

import org.example.model.MasterModel;
import org.example.view.MasterWindow;
import org.example.view.LoginWindow;


public class MasterController {
    private MasterModel model;
    private MasterWindow view;
    private LoginWindow loginView;

    public MasterController(MasterModel model, MasterWindow view, LoginWindow loginView) {
        this.model = model;
        this.view = view;
        this.loginView = loginView;

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
