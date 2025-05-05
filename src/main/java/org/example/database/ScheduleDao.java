package org.example.database;

import org.example.model.MasterModel.ScheduleSlot;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDao {
    private static final String CREATE_SLOT_SQL =
            "INSERT INTO schedule(master_id, date, start_time, end_time, is_available) VALUES(?, ?, ?, ?, ?)";
    private static final String GET_AVAILABLE_SLOTS_SQL =
            "SELECT * FROM schedule WHERE master_id = ? AND date = ? AND is_available = TRUE ORDER BY start_time";
    private static final String GET_MASTER_SCHEDULE_SQL =
            "SELECT * FROM schedule WHERE master_id = ? AND date >= ? ORDER BY date, start_time";
    private static final String UPDATE_SLOT_AVAILABILITY_SQL =
            "UPDATE schedule SET is_available = ? WHERE id = ? AND master_id = ?";
    private static final String DELETE_SLOT_SQL =
            "DELETE FROM schedule WHERE id = ? AND master_id = ?";

    public boolean createScheduleSlot(int masterId, ScheduleSlot slot) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(CREATE_SLOT_SQL)) {

            pstmt.setInt(1, masterId);
            pstmt.setString(2, slot.getDate());
            pstmt.setString(3, slot.getStartTime());
            pstmt.setString(4, slot.getEndTime());
            pstmt.setBoolean(5, slot.isAvailable());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Ошибка при создании слота расписания: " + e.getMessage());
            return false;
        }
    }

    public List<ScheduleSlot> getAvailableSlots(int masterId, String date) {
        List<ScheduleSlot> slots = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_AVAILABLE_SLOTS_SQL)) {

            pstmt.setInt(1, masterId);
            pstmt.setString(2, date);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                slots.add(new ScheduleSlot(
                        rs.getString("date"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getBoolean("is_available")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении доступных слотов: " + e.getMessage());
        }
        return slots;
    }

    public List<ScheduleSlot> getMasterSchedule(int masterId, String fromDate) {
        List<ScheduleSlot> schedule = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_MASTER_SCHEDULE_SQL)) {

            pstmt.setInt(1, masterId);
            pstmt.setString(2, fromDate);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                schedule.add(new ScheduleSlot(
                        rs.getString("date"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getBoolean("is_available")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении расписания мастера: " + e.getMessage());
        }
        return schedule;
    }

    public boolean updateSlotAvailability(int slotId, int masterId, boolean isAvailable) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_SLOT_AVAILABILITY_SQL)) {

            pstmt.setBoolean(1, isAvailable);
            pstmt.setInt(2, slotId);
            pstmt.setInt(3, masterId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении статуса слота: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteScheduleSlot(int slotId, int masterId) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_SLOT_SQL)) {

            pstmt.setInt(1, slotId);
            pstmt.setInt(2, masterId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении слота расписания: " + e.getMessage());
            return false;
        }
    }
}