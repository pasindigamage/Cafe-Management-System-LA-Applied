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
    PlaceOrderDAO placeOrderDAO= (PlaceOrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PLACE_ORDER);
    public boolean placeOrder(PlaceOrder po) throws ClassNotFoundException {
        return placeOrderDAO.placeOrder(po);
    }

    public  String currentId() throws SQLException, ClassNotFoundException {
        return ordersDAO.currentId();
    }

    public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
        return ordersDAO.save(new Order(order.getId(),
                order.getUId(),order.getDate(),order.getAmount()));
    }

    public  boolean save1(List<OrderDetail> odList) throws SQLException, ClassNotFoundException {
        return orderDetailDAO.save1(odList);
    }

    public boolean saveOrderDetail(OrderDetail od) throws SQLException, ClassNotFoundException {
        return orderDetailDAO.save(new OrderDetail(od.getOrderId(),
                od.getFoodItemId(),od.getQty()));
    }
}
