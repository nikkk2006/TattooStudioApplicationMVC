package org.example.model;


public class ClientModel {
    private final User currentUser;

    public ClientModel(User user) {
        this.currentUser = user;
    }

    public User getUser() {
        return currentUser;
    }

}