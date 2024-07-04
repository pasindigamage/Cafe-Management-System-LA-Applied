package lk.ijse.LA_CMS.DAO.custom;

import lk.ijse.LA_CMS.DAO.CrudDAO;
import lk.ijse.LA_CMS.Entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDAO extends CrudDAO<OrderDetail> {
    boolean save1(List<OrderDetail> odList) throws SQLException, ClassNotFoundException;
}
