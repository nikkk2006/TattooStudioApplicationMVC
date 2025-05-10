package org.example.controller;

import org.example.database.AppointmentDao;
import org.example.database.UserDao;
import org.example.database.ScheduleDao;
import org.example.model.MasterModel;
import org.example.model.User;
import org.example.view.ClientWindow;
import org.example.view.MasterSelectionWindow;
import java.util.List;


public class MasterSelectionController {
    private MasterSelectionWindow view;
    private User user;

    public MasterSelectionController(MasterSelectionWindow view, User user) {
        this.view = view;
        this.user = user;
        setupListeners();
        loadAndDisplayMasters();
    }

    private void setupListeners() {
        view.getBackButton().addActionListener(e -> returnToClientWindow());
    }

    private void loadAndDisplayMasters() {
        List<MasterModel> masters = MasterModel.getAllMasters();
        view.displayMasters(masters);
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