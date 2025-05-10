package org.example.model;


public class WorkModel {
    private int id;
    private int userId;
    private String title;
    private String description;
    private String imagePath;

    // Конструктор
    public WorkModel(int id, int userId, String title, String description, String imagePath) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    // Сеттеры (если нужны)
    public void setId(int id) {
        this.id = id;
    }

    // ... остальные сеттеры
}