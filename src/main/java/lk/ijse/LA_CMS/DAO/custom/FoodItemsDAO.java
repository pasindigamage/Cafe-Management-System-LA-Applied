package lk.ijse.LA_CMS.DAO.custom;

import lk.ijse.LA_CMS.DAO.CrudDAO;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.Entity.FoodItems;
import lk.ijse.LA_CMS.Entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface FoodItemsDAO extends CrudDAO<FoodItems> {
    boolean update1(List<OrderDetail> odList) throws SQLException;
    public boolean updateQty(String Id, int qty) throws SQLException;
}
