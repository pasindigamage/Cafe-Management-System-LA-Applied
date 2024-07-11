package lk.ijse.LA_CMS.DAO.custom;

import lk.ijse.LA_CMS.DAO.CrudDAO;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.Entity.InventorySupplier;
import lk.ijse.LA_CMS.Entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface InventorySupplierDetailDAO extends CrudDAO<InventorySupplier> {
    public boolean updateQty(List<OrderDetail> odList) throws SQLException;
    public boolean updateQty(OrderDetail od) throws SQLException;
    }
