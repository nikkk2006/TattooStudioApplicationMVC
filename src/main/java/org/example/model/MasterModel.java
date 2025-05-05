package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class MasterModel {
    private int id;  // Добавляем ID для связи с БД
    private String masterName;
    private String specialization;  // Специализация мастера
    private List<Work> works;
    private List<Appointment> appointments;


    public MasterModel(int id, String masterName, String specialization) {
        this.id = id;
        this.masterName = masterName;
        this.specialization = specialization;
        this.works = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }


    // Статический вложенный класс Work (оставляем как есть)
    public static class Work {
        private String title;
        private String description;
        private int price;
        private String imagePath;  // Добавляем новое поле

        public Work(String title, String description, int price) {
            this(title, description, price, null);
        }
        // Обновленный конструктор
        public Work(String title, String description, int price, String imagePath) {
            this.title = title;
            this.description = description;
            this.price = price;
            this.imagePath = imagePath;
        }

        // Геттеры
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public int getPrice() { return price; }
        public String getImagePath() { return imagePath; }

        // Сеттеры (если нужны)
        public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    }

    // Статический вложенный класс Appointment (добавляем геттеры)
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

        // Добавленные геттеры
        public String getClientName() { return clientName; }
        public String getDate() { return date; }
        public String getTime() { return time; }
        public String getWorkTitle() { return workTitle; }
    }
    public static class ScheduleSlot {
        private String date;
        private String startTime;
        private String endTime;
        private boolean isAvailable;

        public ScheduleSlot(String date, String startTime, String endTime, boolean isAvailable) {
            this.date = date;
            this.startTime = startTime;
            this.endTime = endTime;
            this.isAvailable = isAvailable;
        }

        // Геттеры
        public String getDate() { return date; }
        public String getStartTime() { return startTime; }
        public String getEndTime() { return endTime; }
        public boolean isAvailable() { return isAvailable; }

        // Сеттеры (при необходимости)
        public void setAvailable(boolean available) { isAvailable = available; }
    }

    // Новые геттеры для добавленных полей
    public int getId() { return id; }
    public String getSpecialization() { return specialization; }

    // Существующие геттеры
    public String getMasterName() { return masterName; }
    public List<Work> getWorks() { return works; }
    public List<Appointment> getAppointments() { return appointments; }

    // Метод для загрузки мастеров из БД
    public static List<MasterModel> getAllMasters() {
        List<MasterModel> masters = new ArrayList<>();
        // Здесь должна быть реализация загрузки из БД
        // Пример:
        // masters = DatabaseManager.getMasters();
        return masters;
    }

    // Бизнес-логика (оставляем как есть)
    public void addWork(String title, String description, int price, String imagePath) {
        works.add(new Work(title, description, price, imagePath));
    }
}