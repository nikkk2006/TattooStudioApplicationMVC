package org.example.database;

import org.example.model.MasterModel.Appointment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDao {
    private static final String CREATE_APPOINTMENT_SQL =
            "INSERT INTO appointments(master_id, client_name, date, time, work_title) VALUES(?, ?, ?, ?, ?)";
    private static final String GET_APPOINTMENTS_BY_MASTER_SQL =
            "SELECT * FROM appointments WHERE master_id = ? ORDER BY date, time";
    private static final String DELETE_APPOINTMENT_SQL =
            "DELETE FROM appointments WHERE id = ? AND master_id = ?";

    public boolean createAppointment(int masterId, Appointment appointment) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(CREATE_APPOINTMENT_SQL)) {

            pstmt.setInt(1, masterId);
            pstmt.setString(2, appointment.getClientName());
            pstmt.setString(3, appointment.getDate());
            pstmt.setString(4, appointment.getTime());
            pstmt.setString(5, appointment.getWorkTitle());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Ошибка при создании записи: " + e.getMessage());
            return false;
        }
    }

    public List<Appointment> getAppointmentsByMaster(int masterId) {
        List<Appointment> appointments = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_APPOINTMENTS_BY_MASTER_SQL)) {

            pstmt.setInt(1, masterId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                appointments.add(new Appointment(
                        rs.getString("client_name"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("work_title")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении записей: " + e.getMessage());
        }
        return appointments;
    }

    public boolean deleteAppointment(int appointmentId, int masterId) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_APPOINTMENT_SQL)) {

            pstmt.setInt(1, appointmentId);
            pstmt.setInt(2, masterId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении записи: " + e.getMessage());
            return false;
        }
    }
}