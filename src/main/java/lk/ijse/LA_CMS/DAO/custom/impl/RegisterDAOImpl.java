package lk.ijse.LA_CMS.DAO.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.LA_CMS.DAO.custom.RegisterDAO;
import lk.ijse.LA_CMS.Entity.User;
import lk.ijse.LA_CMS.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterDAOImpl implements RegisterDAO {
    public void saveUser(User user) {
        try {
            String sql = "INSERT INTO User VALUES(?, ?, ?, ?)";

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setObject(1, user.getId());
            pstm.setObject(2, user.getEmployeeId());
            pstm.setObject(3, user.getUserName());
            pstm.setObject(4, user.getPassword());

            if(pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "User Saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something Happened!").show();
        }
    }
}
