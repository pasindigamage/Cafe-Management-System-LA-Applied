package lk.ijse.LA_CMS.DAO.custom.impl;

import lk.ijse.LA_CMS.DAO.custom.OtherMaintainDAO;
import lk.ijse.LA_CMS.db.DbConnection;
import lk.ijse.LA_CMS.Entity.OtherMaintains;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OtherMaintainDAOImpl implements OtherMaintainDAO {
    public boolean save(OtherMaintains otherMaintains) throws SQLException {
        String sql ="INSERT INTO otherMaintain VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, otherMaintains.getId());
        pstm.setObject(2, otherMaintains.getDescription());
        pstm.setObject(3, otherMaintains.getDate());
        pstm.setObject(4, otherMaintains.getAmount());

        return pstm.executeUpdate() > 0;
    }

    public boolean update(OtherMaintains otherMaintains) throws SQLException {
        String sql ="UPDATE otherMaintain set description = ?, date = ?, amount = ? where id =? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, otherMaintains.getDescription());
        pstm.setObject(2, otherMaintains.getDate());
        pstm.setObject(3, otherMaintains.getAmount());
        pstm.setObject(4, otherMaintains.getId());

        return pstm.executeUpdate() > 0;

    }

    public boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM otherMaintain WHERE id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;

    }

    @Override
    public OtherMaintains searchByDescription(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    public List<OtherMaintains> getAll() throws SQLException {
        String sql = "SELECT * FROM otherMaintain";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<OtherMaintains> otherMaintainsList = new ArrayList<>();
        while (resultSet.next()) {
            String omid = resultSet.getString(1);
            String omdescription = resultSet.getString(2);
            Date date = Date.valueOf(resultSet.getString(3));
            String amount = resultSet.getString(4);

            OtherMaintains otherMaintains = new OtherMaintains(omid,omdescription,date,amount);
            otherMaintainsList.add(otherMaintains);
        }
        return otherMaintainsList;

    }

    @Override
    public OtherMaintains searchByCode(String code) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    public String currentId() throws SQLException {
        String sql = "SELECT id FROM otherMaintain ORDER BY id desc LIMIT 1";

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
        }
    }
}
