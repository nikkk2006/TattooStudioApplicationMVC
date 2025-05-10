package org.example.database;

import org.example.model.ScheduleModel;
import java.sql.*;


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
}