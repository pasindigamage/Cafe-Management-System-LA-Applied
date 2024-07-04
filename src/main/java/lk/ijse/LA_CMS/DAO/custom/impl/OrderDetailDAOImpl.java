package lk.ijse.LA_CMS.DAO.custom.impl;

import lk.ijse.LA_CMS.DAO.custom.OrderDetailDAO;
import lk.ijse.LA_CMS.db.DbConnection;
import lk.ijse.LA_CMS.Entity.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    public  boolean save1(List<OrderDetail> odList) throws SQLException, ClassNotFoundException {
        for (OrderDetail od : odList) {
            if(!save(od)) {
                return false;
            }
        }
        return true;
    }

    public boolean save(OrderDetail od) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orderDetails  VALUES (?, ?, ?)"; // Specify column names explicitly
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, od.getOrderId());
        pstm.setObject(2, od.getFoodItemId());
        pstm.setObject(3, od.getQty());

        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean update(OrderDetail dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetail searchByDescription(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public OrderDetail searchByCode(String code) throws SQLException, ClassNotFoundException {
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
