package lk.ijse.LA_CMS.BO.custom.Impl;

import lk.ijse.LA_CMS.BO.custom.PlaceOrderBO;
import lk.ijse.LA_CMS.DAO.DAOFactory;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DAO.custom.FoodItemsDAO;
import lk.ijse.LA_CMS.DAO.custom.OrderDetailDAO;
import lk.ijse.LA_CMS.DAO.custom.OrdersDAO;
import lk.ijse.LA_CMS.DAO.custom.PlaceOrderDAO;
import lk.ijse.LA_CMS.DAO.custom.impl.FoodItemsDAOImpl;
import lk.ijse.LA_CMS.DAO.custom.impl.OrderDetailDAOImpl;
import lk.ijse.LA_CMS.DAO.custom.impl.OrdersDAOImpl;
import lk.ijse.LA_CMS.Entity.Order;
import lk.ijse.LA_CMS.Entity.OrderDetail;
import lk.ijse.LA_CMS.Entity.PlaceOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    OrderDetailDAO orderDetailDAO= (OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER_DETAIL);
    OrdersDAO ordersDAO= (OrdersDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDERS);
    FoodItemsDAO foodItemsDAO= (FoodItemsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.FOODITEMS);

    public boolean placeOrder(PlaceOrder po) throws ClassNotFoundException {
        try {
            boolean isOrderSaved = ordersDAO.save(po.getOrder());

            if (isOrderSaved && orderDetailDAO.save1(po.getOdList())) {
                boolean isQtyUpdated = foodItemsDAO.update1(po.getOdList());

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

    public  String currentId() throws SQLException {
        ResultSet resultSet= SQLUtil.execute("SELECT id FROM Orders ORDER BY id desc LIMIT 1");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public boolean save(Order order) throws SQLException {
        return SQLUtil.execute(("INSERT INTO Orders  VALUES (?, ?, ?, ?)"),order.getId(),
                order.getUId(),order.getDate(),order.getAmount());
    }

    public  boolean save1(List<OrderDetail> odList) throws SQLException, ClassNotFoundException {
        for (OrderDetail od : odList) {
            if(!save(od)) {
                return false;
            }
        }
        return true;
    }

    public boolean save(OrderDetail od) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(("INSERT INTO orderDetails  VALUES (?, ?, ?)"),od.getOrderId(),
                od.getFoodItemId(),od.getQty());
    }

}
