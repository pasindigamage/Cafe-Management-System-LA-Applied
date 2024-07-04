package lk.ijse.LA_CMS.DAO.custom.impl;

import lk.ijse.LA_CMS.DAO.custom.OrderDetailDAO;
import lk.ijse.LA_CMS.DAO.custom.OrdersDAO;
import lk.ijse.LA_CMS.DAO.custom.PlaceOrderDAO;
import lk.ijse.LA_CMS.Entity.PlaceOrder;

import java.sql.SQLException;
import java.util.List;

public class PlaceOrderDAOImpl implements PlaceOrderDAO {

    OrderDetailDAO orderDetailDAO=new OrderDetailDAOImpl();
    OrdersDAO ordersDAO=new OrdersDAOImpl();

    public boolean placeOrder(PlaceOrder po) throws ClassNotFoundException {
        try {
            boolean isOrderSaved = ordersDAO.save(po.getOrder());

            if (isOrderSaved && orderDetailDAO.save1(po.getOdList())) {
                boolean isQtyUpdated = FoodItemsDAOImpl.update1(po.getOdList());

                if (isQtyUpdated) {
                    return true;
                }
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean save(PlaceOrder dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(PlaceOrder dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public PlaceOrder searchByDescription(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<PlaceOrder> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public PlaceOrder searchByCode(String code) throws SQLException, ClassNotFoundException {
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
