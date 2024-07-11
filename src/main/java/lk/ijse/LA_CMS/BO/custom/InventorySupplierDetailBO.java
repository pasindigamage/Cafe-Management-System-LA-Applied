package lk.ijse.LA_CMS.BO.custom;

import lk.ijse.LA_CMS.BO.SuperBO;
import lk.ijse.LA_CMS.DAO.CrudDAO;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DTO.InventorySupplierDTO;
import lk.ijse.LA_CMS.Entity.InventorySupplier;
import lk.ijse.LA_CMS.Entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface InventorySupplierDetailBO extends SuperBO {
    public boolean save(InventorySupplierDTO inventoryDetail) throws SQLException, ClassNotFoundException;
    public boolean delete(String id) throws SQLException, ClassNotFoundException;
    public List<InventorySupplierDTO> getAll() throws SQLException, ClassNotFoundException;
    public String currentId() throws SQLException, ClassNotFoundException;
    public boolean updateQty(List<OrderDetail> odList) throws SQLException;
    public boolean updateQty(OrderDetail od) throws SQLException;
}
