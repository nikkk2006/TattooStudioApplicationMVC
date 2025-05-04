package org.example.controller;


import org.example.model.ClientModel;
import org.example.view.ClientWindow;
import org.example.view.MainWindow;


public class ClientController {
    private ClientModel model;
    private ClientWindow view;
    private MainWindow mainWindow;

    public ClientController(ClientModel model, ClientWindow view, MainWindow mainWindow) {
        this.model = model;
        this.view = view;
        this.mainWindow = mainWindow;

        setupListeners();
    }

    private void setupListeners() {
        view.getChooseMasterButton().addActionListener(e -> chooseMaster());
        view.getViewPortfolioButton().addActionListener(e -> viewPortfolio());
        view.getChooseDateTimeButton().addActionListener(e -> chooseDateTime());
        view.getMakeAppointmentButton().addActionListener(e -> makeAppointment());
        view.getBackButton().addActionListener(e -> goBack());
    }

    private void chooseMaster() {
        model.chooseMaster();
        view.showMessage("Выбор мастера");
    }

    private void viewPortfolio() {
        model.viewPortfolio();
        view.showMessage("Просмотр портфолио мастера");
    }

    private void chooseDateTime() {
        view.showMessage("Выбор даты и времени");
    }

    private void makeAppointment() {
        view.showMessage("Запись на татуировку");
    }

    private void goBack() {
        view.dispose();
        mainWindow.setVisible(true);
    }
}
