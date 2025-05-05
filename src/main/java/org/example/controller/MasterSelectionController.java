package org.example.controller;

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

        view.addMasterSelectionListener(new MasterSelectionWindow.MasterSelectionListener() {
            @Override
            public void onMasterSelected(MasterModel master) {
                System.out.println("Выбран мастер: " + master.getMasterName());
                System.out.println("Специализация: " + master.getSpecialization());
                System.out.println("Количество работ: " + master.getWorks().size());

                // Здесь можно открыть детальное окно мастера
                // new MasterDetailsController(master, user);
            }
        });
    }

    private void loadAndDisplayMasters() {
        List<MasterModel> masters = MasterModel.getAllMasters();
        view.displayMasters(masters);
    }

    private void returnToClientWindow() {
        ClientWindow clientView = new ClientWindow(user);
        new ClientController(clientView, user);
        view.dispose();
        clientView.setVisible(true);
    }
}