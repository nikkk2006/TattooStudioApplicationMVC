package org.example.database;

import org.example.model.AppointmentModel;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDao {
    private static final String GET_BY_MASTER_SQL = """
        SELECT a.id, 
               a.client_id, 
               uc.name as client_name, 
               a.master_id, 
               um.name as master_name,
               a.schedule_id, 
               s.date, 
               s.start_time as time,
               w.title as service_name,
               w.price,
               a.status
        FROM appointments a
        JOIN users uc ON a.client_id = uc.id
        JOIN users um ON a.master_id = um.id
        JOIN schedule s ON a.schedule_id = s.id
        LEFT JOIN works w ON s.user_id = w.user_id
        WHERE a.master_id = ?
        ORDER BY s.date, s.start_time
        """;

    public boolean createAppointment(AppointmentModel appointment) {
        String sql = "INSERT INTO appointments (client_id, master_id, schedule_id, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, appointment.getClientId());
            pstmt.setInt(2, appointment.getMasterId());
            pstmt.setInt(3, appointment.getScheduleId());
            pstmt.setString(4, appointment.getStatus());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        appointment.setId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error creating appointment: " + e.getMessage());
            return false;
        }
    }

    public List<AppointmentModel> getAppointmentsByMaster(int masterId) {
        List<AppointmentModel> appointments = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_BY_MASTER_SQL)) {

            pstmt.setInt(1, masterId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    AppointmentModel appointment = new AppointmentModel();
                    appointment.setId(rs.getInt("id"));
                    appointment.setClientId(rs.getInt("client_id"));
                    appointment.setClientName(rs.getString("client_name"));
                    appointment.setMasterId(rs.getInt("master_id"));
                    appointment.setMasterName(rs.getString("master_name"));
                    appointment.setScheduleId(rs.getInt("schedule_id"));
                    appointment.setServiceName(rs.getString("service_name"));
                    appointment.setPrice(rs.getInt("price"));
                    appointment.setDate(rs.getObject("date", LocalDate.class));
                    appointment.setTime(rs.getObject("time", LocalTime.class));
                    appointment.setStatus(rs.getString("status"));

                    appointments.add(appointment);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting appointments by master: " + e.getMessage());
        }
        return appointments;
    }

    public List<AppointmentModel> getActiveAppointments() {
        List<AppointmentModel> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE status IN ('pending', 'confirmed')";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                AppointmentModel appointment = new AppointmentModel();
                appointment.setId(rs.getInt("id"));
                appointment.setClientId(rs.getInt("client_id"));
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