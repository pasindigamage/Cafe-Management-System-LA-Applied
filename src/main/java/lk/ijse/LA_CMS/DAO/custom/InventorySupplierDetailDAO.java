package lk.ijse.LA_CMS.DAO.custom;

import lk.ijse.LA_CMS.DAO.CrudDAO;
import lk.ijse.LA_CMS.Entity.InventorySupplier;

import java.sql.SQLException;

public interface InventorySupplierDetailDAO extends CrudDAO<InventorySupplier> {
    public InventorySupplier searchByDate(String id) throws SQLException;
    }
