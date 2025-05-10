package org.example.database;

import org.example.model.AppointmentModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDao {
    public boolean createAppointment(AppointmentModel appointment) {
        String sql = "INSERT INTO appointments (client_id, master_id, schedule_id, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, appointment.getClientId());
            pstmt.setInt(2, appointment.getMasterId());
            pstmt.setInt(3, appointment.getScheduleId());
            pstmt.setString(4, appointment.getStatus());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating appointment: " + e.getMessage());
            return false;
        }
    }

    public List<AppointmentModel> getActiveAppointments() {
        List<AppointmentModel> appointments = new ArrayList<>();
        String sql = "SELECT master_id, schedule_id, status FROM appointments WHERE status = 'active'";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                AppointmentModel appointment = new AppointmentModel();
                appointment.setMasterId(rs.getInt("master_id"));
                appointment.setScheduleId(rs.getInt("schedule_id"));
                appointment.setStatus(rs.getString("status"));
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            System.err.println("Error getting active appointments: " + e.getMessage());
        }
        return appointments;
    }
}