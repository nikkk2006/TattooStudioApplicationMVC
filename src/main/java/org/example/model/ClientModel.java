package org.example.model;

public class ClientModel {
    private String clientName;

    public ClientModel(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    // Здесь будут методы бизнес-логики, например:
    public void chooseMaster() {
        // Логика выбора мастера
    }

    public void viewPortfolio() {
        // Логика просмотра портфолио
    }

    // Другие методы модели...
}