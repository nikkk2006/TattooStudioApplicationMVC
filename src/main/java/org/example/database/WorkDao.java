package org.example.database;

import org.example.model.MasterModel.Work;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class WorkDao {
    private static final String CREATE_WORK_SQL =
            "INSERT INTO works(user_id, title, description, price, image_path) VALUES(?, ?, ?, ?, ?)";
    private static final String GET_WORKS_BY_MASTER_SQL =
            "SELECT * FROM works WHERE user_id = ?";

    public boolean createWork(int masterId, Work work, String savedImagePath) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(CREATE_WORK_SQL)) {

            pstmt.setInt(1, masterId);
            pstmt.setString(2, work.getTitle());
            pstmt.setString(3, work.getDescription());
            pstmt.setInt(4, work.getPrice());
            pstmt.setString(5, savedImagePath);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Ошибка при создании работы: " + e.getMessage());
            return false;
        }
    }

    public List<Work> getWorksByMaster(int masterId) {
        List<Work> works = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_WORKS_BY_MASTER_SQL)) {

            pstmt.setInt(1, masterId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                works.add(new Work(
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("price"),
                        rs.getString("image_path")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении работ: " + e.getMessage());
        }
        return works;
    }
}