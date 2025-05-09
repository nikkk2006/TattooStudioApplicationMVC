// AppointmentDao.java
package org.example.database;

import org.example.model.AppointmentModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDao {
    public boolean createAppointment(AppointmentModel appointment) {
        String sql = "INSERT INTO appointments (client_id, master_id, schedule_id, tattoo_description, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, appointment.getClientId());
            pstmt.setInt(2, appointment.getMasterId());
            pstmt.setInt(3, appointment.getScheduleId());
            pstmt.setString(4, appointment.getTattooDescription());
            pstmt.setString(5, appointment.getStatus());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating appointment: " + e.getMessage());
            return false;
        }
    }

    public List<AppointmentModel> getAppointmentsByClient(int clientId) {
        List<AppointmentModel> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE client_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clientId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                AppointmentModel appointment = new AppointmentModel();
                appointment.setId(rs.getInt("id"));
                appointment.setClientId(rs.getInt("client_id"));
                appointment.setMasterId(rs.getInt("master_id"));
                appointment.setScheduleId(rs.getInt("schedule_id"));
                appointment.setTattooDescription(rs.getString("tattoo_description"));
                appointment.setStatus(rs.getString("status"));
                appointment.setCreatedAt(rs.getTimestamp("created_at"));

                appointments.add(appointment);
            }
        } catch (SQLException e) {
            System.err.println("Error getting appointments: " + e.getMessage());
        }

        return appointments;
    }
}