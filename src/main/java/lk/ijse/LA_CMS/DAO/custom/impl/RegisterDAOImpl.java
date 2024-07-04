package lk.ijse.LA_CMS.DAO.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.LA_CMS.DAO.custom.RegisterDAO;
import lk.ijse.LA_CMS.Entity.User;
import lk.ijse.LA_CMS.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RegisterDAOImpl implements RegisterDAO {
    public boolean save(User user) {
        try {
            String sql = "INSERT INTO User VALUES(?, ?, ?, ?)";

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setObject(1, user.getId());
            pstm.setObject(2, user.getEmployeeId());
            pstm.setObject(3, user.getUserName());
            pstm.setObject(4, user.getPassword());

            int rowsUpdated= pstm.executeUpdate();

            if(rowsUpdated > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "User Saved!").show();
            }
            else {
                new Alert(Alert.AlertType.ERROR,"Something Happened!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        return false;
    }

    @Override
    public boolean update(User dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public User searchByDescription(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public User searchByCode(String code) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public String currentId() throws SQLException, ClassNotFoundException {
        return "";
    }
}
