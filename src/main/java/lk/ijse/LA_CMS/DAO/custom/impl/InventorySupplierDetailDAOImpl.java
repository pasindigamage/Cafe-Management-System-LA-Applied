package lk.ijse.LA_CMS.DAO.custom.impl;

import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DAO.custom.InventorySupplierDetailDAO;
import lk.ijse.LA_CMS.db.DbConnection;
import lk.ijse.LA_CMS.Entity.InventorySupplier;
import lk.ijse.LA_CMS.Entity.OrderDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventorySupplierDetailDAOImpl implements InventorySupplierDetailDAO {
    public boolean save(InventorySupplier inventoryDetail) throws SQLException {
        return SQLUtil.execute(("INSERT INTO inventorySupplier VALUES(?, ?, ?)"), inventoryDetail.getSupplierId(),
                inventoryDetail.getInventoryId(), inventoryDetail.getDate());
    }

    @Override
    public boolean update(InventorySupplier dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute(("DELETE FROM inventorySupplier WHERE foodItemId = ?"),id);
    }


    public List<InventorySupplier> getAll() throws SQLException {
        List<InventorySupplier> InventorySupplierDetailList = new ArrayList<>();
        ResultSet resultSet=SQLUtil.execute("SELECT * FROM inventorySupplier");
        while (resultSet.next()) {
            InventorySupplierDetailList.add(new InventorySupplier(resultSet.getString("supplierId"),
                    resultSet.getString("foodItemId"),resultSet.getDate("date")));
        }
        return InventorySupplierDetailList;
    }

    @Override
    public InventorySupplier searchByCode(String code) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    public String currentId() throws SQLException {
        ResultSet resultSet=SQLUtil.execute("SELECT id FROM inventorySupplier ORDER BY id desc LIMIT 1");
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
    }

    public InventorySupplier searchByDescription(String ingrediansIDValue) throws SQLException {
        ResultSet resultSet=SQLUtil.execute("SELECT * FROM inventorySupplier WHERE description = ?");
                if (resultSet.next()) {
                    return new InventorySupplier(
                            resultSet.getString(1), resultSet.getString(2),
                            resultSet.getDate(3)
                    );
                }

        return null;
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