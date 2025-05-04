package org.example.model;

public class ClientModel {
    private final User currentUser;

    public ClientModel(User user) {
        this.currentUser = user;
    }

    public User getUser() {
        return currentUser;
    }

    public boolean chooseMaster() {
        // Логика выбора мастера
        return true;
    }

    public boolean viewPortfolio() {
        // Логика просмотра портфолио
        return true;
    }

    public boolean makeAppointment() {
        // Логика создания записи
        return true;
    }
}