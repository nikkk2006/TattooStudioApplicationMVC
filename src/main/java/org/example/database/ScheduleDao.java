package org.example.database;

import org.example.model.ScheduleModel;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDao {
    public static void addScheduleSlot(ScheduleModel schedule) throws SQLException {
        String sql = "INSERT INTO schedule (user_id, date, start_time, end_time, is_available) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, schedule.getMasterId());
            stmt.setString(2, schedule.getDate().toString());
            stmt.setString(3, schedule.getStartTime().toString());
            stmt.setString(4, schedule.getEndTime().toString());
            stmt.setBoolean(5, schedule.isAvailable());

            stmt.executeUpdate();
        }
    }
    public void updateSchedule(ScheduleModel schedule) {
        String sql = "UPDATE schedule SET is_available = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, schedule.isAvailable());
            stmt.setInt(2, schedule.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении расписания: " + e.getMessage());
        }
    }
    public List<String> getAvailableTimeSlots(int masterId) {
        List<String> timeSlots = new ArrayList<>();
        String sql = "SELECT date_time FROM schedules WHERE master_id = ? AND available = true ORDER BY date_time";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, masterId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                timeSlots.add(rs.getString("date_time"));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении расписания: " + e.getMessage());
        }

        return timeSlots;
    }
    public List<ScheduleModel> getAvailableSchedules(int masterId) {
        List<ScheduleModel> schedules = new ArrayList<>();
        String sql = "SELECT * FROM schedule WHERE user_id = ? AND is_available = true ORDER BY date, start_time";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, masterId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ScheduleModel schedule = new ScheduleModel();
                schedule.setId(rs.getInt("id"));
                schedule.setMasterId(rs.getInt("user_id"));
                schedule.setDate(LocalDate.parse(rs.getString("date")));
                schedule.setStartTime(LocalTime.parse(rs.getString("start_time")));
                schedule.setEndTime(LocalTime.parse(rs.getString("end_time")));
                schedule.setAvailable(rs.getBoolean("is_available"));
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении доступных расписаний: " + e.getMessage());
        }
        return schedules;
    }

    public static List<ScheduleModel> getMasterSchedule(int masterId) throws SQLException {
        List<ScheduleModel> scheduleList = new ArrayList<>();
        String sql = "SELECT * FROM schedule WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, masterId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ScheduleModel schedule = new ScheduleModel();
                schedule.setId(rs.getInt("id"));
                schedule.setMasterId(rs.getInt("user_id"));
                schedule.setDate(LocalDate.parse(rs.getString("date")));
                schedule.setStartTime(LocalTime.parse(rs.getString("start_time")));
                schedule.setEndTime(LocalTime.parse(rs.getString("end_time")));
                schedule.setAvailable(rs.getBoolean("is_available"));

                scheduleList.add(schedule);
            }
        }
        return scheduleList;
    }

    public int getScheduleIdByDateTime(String dateTime) throws SQLException {
        String date;
        String time;
        try {
            // Handle ISO format (e.g., "2025-05-08T14:00:00")
            LocalDateTime dateTimeParsed = LocalDateTime.parse(dateTime);
            date = dateTimeParsed.toLocalDate().toString();
            time = dateTimeParsed.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException ex) {
            // Fallback to original format (e.g., "2025-05-08 14:00")
            String[] parts = dateTime.split(" ");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid dateTime format. Expected 'YYYY-MM-DD HH:MM' or ISO format");
            }
            date = parts[0];
            time = parts[1];
        }

        String sql = "SELECT id FROM schedule WHERE date = ? AND start_time = ? AND is_available = true";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, date);
            stmt.setString(2, time);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("No available schedule slot found for the given date and time");
            }
        }
    }
}