package org.example.model;


import java.util.ArrayList;
import java.util.List;

public class MasterModel {
    private String masterName;
    private List<Work> works;
    private List<Appointment> appointments;

    public MasterModel(String masterName) {
        this.masterName = masterName;
        this.works = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }

    // Статический вложенный класс
    public static class Work {
        private String title;
        private String description;
        private int price;

        public Work(String title, String description, int price) {
            this.title = title;
            this.description = description;
            this.price = price;
        }

        // Геттеры
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public int getPrice() { return price; }
    }

    // Геттеры и сеттеры
    public String getMasterName() {
        return masterName;
    }

    public List<Work> getWorks() {
        return works;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    // Бизнес-логика
    public void addWork(String title, String description, int price) {
        works.add(new Work(title, description, price));
    }

    public static class Appointment {
        private String clientName;
        private String date;
        private String time;
        private String workTitle;

        public Appointment(String clientName, String date, String time, String workTitle) {
            this.clientName = clientName;
            this.date = date;
            this.time = time;
            this.workTitle = workTitle;
        }

        // Геттеры
    }
}