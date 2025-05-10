package org.example.model;

import org.example.database.DatabaseManager;
import org.example.database.WorkDao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class MasterModel {
    private int id;
    private String masterName;
    private String specialization;
    private List<Work> works;
    private List<Appointment> appointments; // Добавляем список записей
    private List<ScheduleSlot> schedule; // Добавляем расписание
    private transient WorkDao workDao;

    public MasterModel(int id, String masterName, String specialization) {
        this.id = id;
        this.masterName = masterName;
        this.specialization = specialization != null ? specialization : "";
        this.works = new ArrayList<>();
        this.appointments = new ArrayList<>(); // Инициализируем список записей
        this.schedule = new ArrayList<>(); // Инициализируем расписание
        this.workDao = new WorkDao();
    }

    public void loadScheduleFromDatabase() {
        this.schedule = new ArrayList<>();
        String sql = "SELECT id, date, start_time, end_time, is_available FROM schedule WHERE user_id = " + this.id;

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ScheduleSlot slot = new ScheduleSlot(
                        rs.getInt("id"),  // Получаем id из БД
                        rs.getString("date"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getBoolean("is_available")
                );
                this.schedule.add(slot);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при загрузке расписания: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void loadWorksFromDatabase() {
        this.works = workDao.getWorksByMaster(this.id);
    }

    public static class Work {
        private String title;
        private String description;
        private int price;
        private String imagePath;

        public Work(String title, String description, int price) {
            this(title, description, price, null);
        }

        public Work(String title, String description, int price, String imagePath) {
            this.title = title;
            this.description = description;
            this.price = price;
            this.imagePath = imagePath;
        }

        // Геттеры и сеттеры
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public int getPrice() { return price; }
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
    }

    public static class ScheduleSlot {
        private int id;
        private String date;
        private String startTime;
        private String endTime;
        private boolean isAvailable;

        public ScheduleSlot(int id, String date, String startTime, String endTime, boolean isAvailable) {
            this.id = id;
            this.date = date;
            this.startTime = startTime;
            this.endTime = endTime;
            this.isAvailable = isAvailable;
        }

        // Геттеры и сеттеры
        public String getDate() { return date; }
        public String getStartTime() { return startTime; }
        public String getEndTime() { return endTime; }
        public boolean isAvailable() { return isAvailable; }
        public int getId() { return id; }
    }

    // Геттеры
    public int getId() { return id; }
    public String getMasterName() { return masterName; }
    public List<ScheduleSlot> getSchedule() { return schedule; }

    // Метод для загрузки всех мастеров
    public static List<MasterModel> getAllMasters() {
        List<MasterModel> masters = new ArrayList<>();
        // Используем только существующие столбцы
        String sql = "SELECT id, name FROM users WHERE role = 'MASTER'";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Создаем мастера без специализации (можно использовать пустую строку или null)
                MasterModel master = new MasterModel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        "" // Пустая строка вместо специализации
                );
                master.loadWorksFromDatabase();
                masters.add(master);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при загрузке мастеров: " + e.getMessage());
            e.printStackTrace();
        }
        return masters;
    }
}