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

            // Создание таблицы пользователей с вашими полями
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "email TEXT NOT NULL UNIQUE," +
                    "password TEXT NOT NULL," +
                    "role TEXT NOT NULL)";

            stmt.execute(sql);

            // Можно добавить другие таблицы по необходимости
            System.out.println("База данных инициализирована");
        } catch (SQLException e) {
            System.err.println("Ошибка при инициализации БД: " + e.getMessage());
        }
    }

    public static boolean isDatabaseInitialized() {
        // Проверка существования таблиц или файла БД
        // Пример для SQLite:
        try (Connection conn = getConnection();
             ResultSet rs = conn.getMetaData().getTables(null, null, "users", null)) {
            return rs.next(); // Если таблица существует, возвращает true
        } catch (SQLException e) {
            return false;
        }
    }
}