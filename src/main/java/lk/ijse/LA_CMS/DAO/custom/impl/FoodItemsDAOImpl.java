package lk.ijse.LA_CMS.DAO.custom.impl;

import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DAO.custom.FoodItemsDAO;
import lk.ijse.LA_CMS.db.DbConnection;
import lk.ijse.LA_CMS.Entity.FoodItems;
import lk.ijse.LA_CMS.Entity.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodItemsDAOImpl implements FoodItemsDAO {
    public boolean save(FoodItems foodItems) throws SQLException {
        return SQLUtil.execute(("INSERT INTO FoodItems VALUES(?, ?, ?, ?)"),
                foodItems.getId(),foodItems.getDescription(),foodItems.getUnitPrice(),foodItems.getQtyOnHand());
    }

    public boolean update(FoodItems foodItems) throws SQLException {
        return SQLUtil.execute(("UPDATE FoodItems set description = ?, unitPrice = ?, QtyOnHand = ? where id =? "),
                foodItems.getDescription(),foodItems.getUnitPrice(),foodItems.getQtyOnHand(),foodItems.getId());
    }

    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute(("DELETE FROM FoodItems WHERE id = ?"),id);
    }

    public FoodItems searchByDescription(String id) throws SQLException {
        ResultSet resultSet=SQLUtil.execute(("SELECT * FROM FoodItems WHERE description = ?"),id);
        resultSet.next();
        return new FoodItems(resultSet.getString("id"),id+"",
                resultSet.getDouble("unitPrice"),resultSet.getInt("qtyOnHand"));
    }

    public List<FoodItems> getAll() throws SQLException {
        List<FoodItems> foodItemsList = new ArrayList<>();
        ResultSet resultSet=SQLUtil.execute("SELECT * FROM FoodItems");
        while (resultSet.next()) {
            FoodItems foodItems=new FoodItems(resultSet.getString("id"),
                    resultSet.getString("description"),resultSet.getDouble("unitPrice"),
                    resultSet.getInt("qtyOnHand"));
            foodItemsList.add(foodItems);
        }
        return foodItemsList;
    }

    //have a matter
    public FoodItems searchByCode(String foodItemDValue) throws SQLException {
        ResultSet resultSet=SQLUtil.execute(("SELECT * FROM FoodItems WHERE description = ?"),foodItemDValue);
        resultSet.next();
        return new FoodItems(resultSet.getString("id"),foodItemDValue,
                resultSet.getDouble("unitPrice"),resultSet.getInt("qtyOnHand"));
    }

    public List<String> getIds() throws SQLException {
        List<String> IdList = new ArrayList<>();
        ResultSet resultSet=SQLUtil.execute("SELECT description FROM FoodItems");

        while(resultSet.next()) {
            IdList.add(resultSet.getString(1));
        }
        return IdList;
    }

    public String currentId() throws SQLException {
        ResultSet resultSet=SQLUtil.execute("SELECT id FROM FoodItems ORDER BY id desc LIMIT 1");
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
    }

    public static boolean update1(List<OrderDetail> odList) throws SQLException {
        for (OrderDetail od : odList) {
            boolean isUpdateQty = updateQty(od.getFoodItemId(), od.getQty());
            if(!isUpdateQty) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(String Id, int qty) throws SQLException {
        return SQLUtil.execute(("UPDATE FoodItems SET qtyOnHand = qtyOnHand - ? WHERE id = ?"),qty,Id);
    }
}