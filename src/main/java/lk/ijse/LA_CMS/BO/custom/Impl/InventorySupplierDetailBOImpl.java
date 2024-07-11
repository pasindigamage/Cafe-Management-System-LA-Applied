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
        return inventorySupplierDetailDAO.updateQty(odList);
    }

    public boolean updateQty(OrderDetail od) throws SQLException {
        return inventorySupplierDetailDAO.updateQty(od);
    }
}