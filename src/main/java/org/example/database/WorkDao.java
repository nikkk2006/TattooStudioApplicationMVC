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
    private static final String DELETE_WORK_SQL =
            "DELETE FROM works WHERE id = ? AND user_id = ?";
    private static final String UPDATE_WORK_SQL =
            "UPDATE works SET title = ?, description = ?, price = ?, image_path = ? WHERE id = ?";

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

    public boolean deleteWork(int workId, int masterId) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_WORK_SQL)) {

            pstmt.setInt(1, workId);
            pstmt.setInt(2, masterId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении работы: " + e.getMessage());
            return false;
        }
    }

    public boolean updateWork(int workId, Work updatedWork, String savedImagePath) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_WORK_SQL)) {

            pstmt.setString(1, updatedWork.getTitle());
            pstmt.setString(2, updatedWork.getDescription());
            pstmt.setInt(3, updatedWork.getPrice());
            pstmt.setString(4, savedImagePath);
            pstmt.setInt(5, workId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении работы: " + e.getMessage());
            return false;
        }
    }
}