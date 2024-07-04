package lk.ijse.LA_CMS.DAO.custom.impl;

import lk.ijse.LA_CMS.DAO.custom.KitchenWareMaintainDAO;
import lk.ijse.LA_CMS.db.DbConnection;
import lk.ijse.LA_CMS.Entity.KitchenWareMaintains;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KitchenWareMaintainDAOImpl implements KitchenWareMaintainDAO {

    public boolean save(KitchenWareMaintains kitchenWareMaintains) throws SQLException, ClassNotFoundException {
        String sql ="INSERT INTO kitchenWareMaintain VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, kitchenWareMaintains.getId());
        pstm.setObject(2, kitchenWareMaintains.getKitchenWareId());
        pstm.setObject(3, kitchenWareMaintains.getDescription());
        pstm.setObject(4, kitchenWareMaintains.getDate());
        pstm.setObject(5, kitchenWareMaintains.getAmount());

        return pstm.executeUpdate() > 0;
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM kitchenWareMaintain WHERE id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    @Override
    public KitchenWareMaintains searchByDescription(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    public boolean update(KitchenWareMaintains kitchenWareMaintains) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE kitchenWareMaintain set kitchenWareId = ?, description = ?, amount = ? where id =? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, kitchenWareMaintains.getKitchenWareId());
        pstm.setObject(2, kitchenWareMaintains.getDescription());
        pstm.setObject(3, kitchenWareMaintains.getAmount());
        pstm.setObject(4, kitchenWareMaintains.getId());
        return pstm.executeUpdate() > 0;

    }

    public List<KitchenWareMaintains> getAll() throws SQLException, ClassNotFoundException {
        String sql = "select * from kitchenWareMaintain";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<KitchenWareMaintains> kitchenWareMaintainsList = new ArrayList<>();
        while (resultSet.next()) {
            String Id = resultSet.getString(1);
            String kwId = resultSet.getString(2);
            String description = resultSet.getString(3);
            String date = resultSet.getString(4);
            String amount = resultSet.getString(5);

            KitchenWareMaintains kitchenWareMaintains = new KitchenWareMaintains(Id,kwId,description,date,amount);
            kitchenWareMaintainsList.add(kitchenWareMaintains);
        }
        return kitchenWareMaintainsList;
    }

    @Override
    public KitchenWareMaintains searchByCode(String code) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    public String currentId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id FROM kitchenWareMaintain ORDER BY id desc LIMIT 1";

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
