package org.example.model;


public class ClientModel {
    private final UserModel currentUser;

    public ClientModel(UserModel user) {
        this.currentUser = user;
    }

    public UserModel getUser() {
        return currentUser;
    }

}