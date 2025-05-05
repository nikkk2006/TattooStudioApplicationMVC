package org.example.controller;

import org.example.model.MasterModel;
import org.example.model.User;
import org.example.view.ClientWindow;
import org.example.view.MasterSelectionWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MasterSelectionController {
    private MasterSelectionWindow view;
    private User user;

    public MasterSelectionController(MasterSelectionWindow view, User user) {
        this.view = view;
        this.user = user;

        // Загрузка мастеров (заглушка - замените на реальную загрузку из БД)
        List<MasterModel> masters = List.of(
                new MasterModel(1, "Иван Иванов", "Традиционные тату"),
                new MasterModel(2, "Анна Петрова", "Минимализм"),
                new MasterModel(3, "Сергей Сидоров", "Реализм")
        );

        view.displayMasters(masters);
        setupListeners();
    }

    private void setupListeners() {
        view.getBackButton().addActionListener(e -> returnToClientWindow());

        // Здесь можно добавить обработчик выбора мастера
    }

    private void returnToClientWindow(){
        ClientWindow clientView = new ClientWindow(user);
        new ClientController(clientView, user);
        view.close();
        clientView.setVisible(true);
    }
}