package org.example.database;

import org.example.model.ScheduleModel;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDao {
    public static void addScheduleSlot(ScheduleModel schedule) throws SQLException {
        String sql = "INSERT INTO schedule (user_id, date, start_time, end_time, is_available) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Преобразование LocalDate → java.sql.Date
            stmt.setInt(1, schedule.getMasterId());
            stmt.setString(2, schedule.getDate().toString()); // Для SQLite используем строку
            stmt.setString(3, schedule.getStartTime().toString()); // Для SQLite используем строку
            stmt.setString(4, schedule.getEndTime().toString()); // Для SQLite используем строку
            stmt.setBoolean(5, schedule.isAvailable());

            stmt.executeUpdate();
        }
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

                // Для SQLite читаем как строку и преобразуем в LocalDate
                schedule.setDate(LocalDate.parse(rs.getString("date")));

                // Для SQLite читаем как строку и преобразуем в LocalTime
                schedule.setStartTime(LocalTime.parse(rs.getString("start_time")));
                schedule.setEndTime(LocalTime.parse(rs.getString("end_time")));
                schedule.setAvailable(rs.getBoolean("is_available"));

                scheduleList.add(schedule);
            }
        }
        return scheduleList;
    }
}