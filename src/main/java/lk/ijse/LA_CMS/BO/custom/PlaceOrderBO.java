package lk.ijse.LA_CMS.BO.custom;

import lk.ijse.LA_CMS.BO.SuperBO;
import lk.ijse.LA_CMS.DAO.CrudDAO;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.Entity.Order;
import lk.ijse.LA_CMS.Entity.OrderDetail;
import lk.ijse.LA_CMS.Entity.PlaceOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface PlaceOrderBO extends SuperBO {
    public boolean placeOrder(PlaceOrder po) throws ClassNotFoundException;
    public String currentId() throws SQLException;
    public boolean save(Order order) throws SQLException;
    public boolean save1(List<OrderDetail> odList) throws SQLException, ClassNotFoundException;
    public boolean save(OrderDetail od) throws SQLException, ClassNotFoundException;
}
