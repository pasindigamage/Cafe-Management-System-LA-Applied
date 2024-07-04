package lk.ijse.LA_CMS.DAO.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.LA_CMS.DAO.custom.ChangePasswordDAO;
import lk.ijse.LA_CMS.DTO.ChangePasswordDTO;
import lk.ijse.LA_CMS.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ChangePasswordDAOImpl implements ChangePasswordDAO {

    public boolean save(ChangePasswordDTO changePasswordDTO) {
        try {
            String sql = "UPDATE User SET password = ? WHERE userName = ?";

            // Update based on userName only (assuming unique constraint)
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, changePasswordDTO.getNewPassword());
            pstm.setString(2, changePasswordDTO.getUserName());

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "New Password Saved!").show();
            } else {
                // No rows updated, handle potential errors (e.g., user not found)
                new Alert(Alert.AlertType.ERROR, "Password update failed. User not found.").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return false;
    }

    @Override
    public boolean update(ChangePasswordDTO dto) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public ChangePasswordDTO searchByDescription(String id) {
        return null;
    }

    @Override
    public List<ChangePasswordDTO> getAll() {
        return List.of();
    }

    @Override
    public ChangePasswordDTO searchByCode(String code) {
        return null;
    }

    @Override
    public List<String> getIds() {
        return List.of();
    }

    @Override
    public String currentId() {
        return "";
    }

}
