package org.example.database;

import org.example.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    // Существующие методы остаются без изменений
    public boolean createUser(User user) {
        String sql = "INSERT INTO users(name, email, password, role) VALUES(?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getRole());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении пользователя: " + e.getMessage());
        }
        return null;
    }

    // Новые методы для работы с мастерами и записями

    /**
     * Получает список всех мастеров (пользователей с ролью 'master')
     *
     * @return список имен мастеров
     */
    public List<String> getAllMastersNames() {
        List<String> masters = new ArrayList<>();
        String sql = "SELECT name FROM users WHERE role = 'master'";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                masters.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении списка мастеров: " + e.getMessage());
        }

        return masters;
    }

    /**
     * Получает ID пользователя по его имени
     * @param name имя пользователя
     * @return ID пользователя или -1 если не найден
     */
    public int getUserIdByName(String name) {
        String sql = "SELECT id FROM users WHERE name = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении ID пользователя: " + e.getMessage());
        }

        return -1;
    }

    /**
     * Получает объект пользователя по ID
     * @param id ID пользователя
     * @return объект User или null если не найден
     */
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении пользователя по ID: " + e.getMessage());
        }

        return null;
    }

    /**
     * Получает список всех мастеров с полной информацией
     * @return список объектов User с ролью 'master'
     */
    public List<User> getAllMasters() {
        List<User> masters = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'master'";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                masters.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении списка мастеров: " + e.getMessage());
        }

        return masters;
    }
}