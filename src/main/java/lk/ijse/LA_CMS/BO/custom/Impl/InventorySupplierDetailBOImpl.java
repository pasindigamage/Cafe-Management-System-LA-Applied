package lk.ijse.LA_CMS.BO.custom.Impl;

import lk.ijse.LA_CMS.BO.custom.InventorySupplierDetailBO;
import lk.ijse.LA_CMS.DAO.DAOFactory;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DAO.custom.InventorySupplierDetailDAO;
import lk.ijse.LA_CMS.DTO.InventorySupplierDTO;
import lk.ijse.LA_CMS.Entity.InventorySupplier;
import lk.ijse.LA_CMS.Entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventorySupplierDetailBOImpl implements InventorySupplierDetailBO {
    InventorySupplierDetailDAO inventorySupplierDetailDAO= (InventorySupplierDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INVENTORY_SUPPLIER_DETAIL);
    public boolean save(InventorySupplierDTO inventoryDetail) throws SQLException, ClassNotFoundException {
        return inventorySupplierDetailDAO.save(new InventorySupplier(inventoryDetail.getSupplierId(),
                inventoryDetail.getInventoryId(), inventoryDetail.getDate()));
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return inventorySupplierDetailDAO.delete(id);
    }


    public List<InventorySupplierDTO> getAll() throws SQLException, ClassNotFoundException {
        List<InventorySupplierDTO> InventorySupplierDetailList = new ArrayList<>();
        List<InventorySupplier> inventorySuppliers=inventorySupplierDetailDAO.getAll();
        for (InventorySupplier inventoryDetail:inventorySuppliers){
            InventorySupplierDTO inventorySupplierDTO=new InventorySupplierDTO(inventoryDetail.getSupplierId(),
                    inventoryDetail.getInventoryId(), inventoryDetail.getDate());
            InventorySupplierDetailList.add(inventorySupplierDTO);
        }

        return InventorySupplierDetailList;
    }

    public String currentId() throws SQLException, ClassNotFoundException {
            return inventorySupplierDetailDAO.currentId();
    }


    public boolean updateQty(List<OrderDetail> odList) throws SQLException{
        for (OrderDetail od : odList) {
            if(!updateQty(od)) {
                return false;
            }
        }
        return true;
    }

    public boolean updateQty(OrderDetail od) throws SQLException {
        return SQLUtil.execute(("SELECT (sub1.qty - sub2.multiplied_qty) AS result\n" +
                "FROM (\n" +
                "    SELECT inventorySupplier.qty\n" +
                "    FROM inventorySupplier\n" +
                "    JOIN Inventory ON inventorySupplier.inventoryId = Inventory.id\n" +
                "    JOIN IngrediansDetail ON Inventory.id = IngrediansDetail.inventoryId\n" +
                "    WHERE IngrediansDetail.foodItemId = ?\n" +
                ") AS sub1,\n" +
                "(\n" +
                "    SELECT IngrediansDetail.qty * 2 AS multiplied_qty\n" +
                "    FROM IngrediansDetail\n" +
                "    WHERE IngrediansDetail.foodItemId = ?\n" +
                ") AS sub2;"),od);
    }

}