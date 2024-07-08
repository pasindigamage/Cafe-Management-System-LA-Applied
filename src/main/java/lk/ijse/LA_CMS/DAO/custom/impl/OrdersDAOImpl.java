package lk.ijse.LA_CMS.DAO.custom.impl;

import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DAO.custom.OrdersDAO;
import lk.ijse.LA_CMS.db.DbConnection;
import lk.ijse.LA_CMS.Entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrdersDAOImpl implements OrdersDAO {
    public  String currentId() throws SQLException {
        String sql = "SELECT id FROM Orders ORDER BY id desc LIMIT 1";

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
        }
    }

    public boolean save(Order order) throws SQLException {
        return SQLUtil.execute(("INSERT INTO Orders  VALUES (?, ?, ?, ?)"),order.getId(),
                order.getUId(),order.getDate(),order.getAmount());
    }

    @Override
    public boolean update(Order dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order searchByDescription(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Order> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public Order searchByCode(String code) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return List.of();
    }
}
