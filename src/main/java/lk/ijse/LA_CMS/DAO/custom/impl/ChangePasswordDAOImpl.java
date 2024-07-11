package lk.ijse.LA_CMS.DAO.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DAO.custom.ChangePasswordDAO;
import lk.ijse.LA_CMS.DTO.ChangePasswordDTO;
import lk.ijse.LA_CMS.Entity.User;
import lk.ijse.LA_CMS.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ChangePasswordDAOImpl implements ChangePasswordDAO {

    public boolean save(ChangePasswordDTO dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE User SET password = ? WHERE userName = ?", dto.getNewPassword(), dto.getUserName());
    }

    @Override
    public boolean update(ChangePasswordDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ChangePasswordDTO searchByDescription(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<ChangePasswordDTO> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public ChangePasswordDTO searchByCode(String code) throws SQLException, ClassNotFoundException {
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