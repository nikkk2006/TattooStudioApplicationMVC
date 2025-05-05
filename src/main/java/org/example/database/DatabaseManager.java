package org.example.database;

import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:TattooStudio.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Таблица пользователей
            String sqlUsers = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "email TEXT NOT NULL UNIQUE," +
                    "password TEXT NOT NULL," +
                    "role TEXT NOT NULL)";
            stmt.execute(sqlUsers);

            // Таблица работ (только для мастеров)
            String sqlWorks = "CREATE TABLE IF NOT EXISTS works (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "title TEXT NOT NULL," +
                    "description TEXT," +
                    "price INTEGER NOT NULL," + // Добавляем столбец price
                    "image_path TEXT NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES users(id))";
            stmt.execute(sqlWorks);

            // Таблица расписания (только для мастеров)
            String sqlSchedule = "CREATE TABLE IF NOT EXISTS schedule (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," + // ID пользователя с ролью master
                    "date DATE NOT NULL," +
                    "start_time TIME NOT NULL," +
                    "end_time TIME NOT NULL," +
                    "is_available BOOLEAN DEFAULT TRUE," +
                    "FOREIGN KEY (user_id) REFERENCES users(id))";
            stmt.execute(sqlSchedule);

            // Таблица записей клиентов
            String sqlAppointments = "CREATE TABLE IF NOT EXISTS appointments (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "client_id INTEGER NOT NULL," + // ID пользователя с ролью client
                    "master_id INTEGER NOT NULL," + // ID пользователя с ролью master
                    "schedule_id INTEGER NOT NULL," +
                    "tattoo_description TEXT," +
                    "status TEXT DEFAULT 'pending' CHECK (status IN ('pending', 'confirmed', 'completed', 'cancelled'))," +
                    "created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (client_id) REFERENCES users(id)," +
                    "FOREIGN KEY (master_id) REFERENCES users(id)," +
                    "FOREIGN KEY (schedule_id) REFERENCES schedule(id))";
            stmt.execute(sqlAppointments);

            System.out.println("База данных инициализирована");
        } catch (SQLException e) {
            System.err.println("Ошибка при инициализации БД: " + e.getMessage());
        }
    }

    public static boolean isDatabaseInitialized() {
        try (Connection conn = getConnection();
             ResultSet rs = conn.getMetaData().getTables(null, null, "users", null)) {
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }
}