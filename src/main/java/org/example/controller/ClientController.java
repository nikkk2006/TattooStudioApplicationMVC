package org.example.controller;

import org.example.model.ClientModel;
import org.example.model.User;
import org.example.view.ClientWindow;
import org.example.view.MainWindow;
import org.example.view.MasterSelectionWindow;

public class ClientController {
    private ClientModel model;
    private ClientWindow view;
    private User currentUser;  // Добавляем текущего пользователя

    // Основной конструктор
    public ClientController(ClientWindow view, User user) {
        this.view = view;
        this.currentUser = user;
        this.model = new ClientModel(user);  // Инициализируем модель с пользователем

        setupListeners();
    }

    // Старый конструктор (можно оставить для совместимости или удалить)
    @Deprecated
    public ClientController(ClientModel model, ClientWindow view, MainWindow mainWindow) {
        this.model = model;
        this.view = view;
        this.currentUser = model.getUser();  // Предполагая, что модель хранит пользователя
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
//        model.chooseMaster();
//        view.showMessage(currentUser.getName() + ", выберите мастера");
        view.close();

        MasterSelectionWindow masterSelectionView = new MasterSelectionWindow();
        new MasterSelectionController(masterSelectionView, currentUser);
        masterSelectionView.setVisible(true);
    }

    private void viewPortfolio() {
        model.viewPortfolio();
        view.showMessage("Портфолио доступных мастеров");
    }

    private void chooseDateTime() {
        view.showMessage("Выберите удобное время для " + currentUser.getName());
    }

    private void makeAppointment() {
        if (model.makeAppointment()) {
            view.showSuccess("Запись успешно создана для " + currentUser.getName());
        } else {
            view.showError("Ошибка при создании записи");
        }
    }

    private void goBack() {
        view.dispose();
        new MainWindow().setVisible(true);  // Создаем новое главное окно
    }
}