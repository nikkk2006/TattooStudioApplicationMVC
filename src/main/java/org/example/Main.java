package org.example;

import org.example.controller.ClientController;
import org.example.model.ClientModel;
import org.example.view.ClientWindow;
import org.example.view.MainWindow;

public class Main {
    public static void main(String[] args) {
        String clientName = "Client Name";
        MainWindow mainWindow = new MainWindow();

        ClientModel model = new ClientModel(clientName);
        ClientWindow view = new ClientWindow(clientName);
        new ClientController(model, view, mainWindow);

        view.setVisible(true);

        mainWindow.setVisible(false);
    }
}