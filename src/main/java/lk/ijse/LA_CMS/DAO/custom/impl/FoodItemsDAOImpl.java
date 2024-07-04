package lk.ijse.LA_CMS.DAO.custom.impl;

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

        String sql ="INSERT INTO FoodItems VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, foodItems.getId());
        pstm.setObject(2, foodItems.getDescription());
        pstm.setObject(3, foodItems.getUnitPrice());
        pstm.setObject(4, foodItems.getQtyOnHand());

        return pstm.executeUpdate() > 0;
    }

    public boolean update(FoodItems foodItems) throws SQLException {
        String sql ="UPDATE FoodItems set description = ?, unitPrice = ?, QtyOnHand = ? where id =? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, foodItems.getDescription());
        pstm.setObject(2, foodItems.getUnitPrice());
        pstm.setObject(3, foodItems.getQtyOnHand());
        pstm.setObject(4, foodItems.getId());

        return pstm.executeUpdate() > 0;
    }

    public boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM FoodItems WHERE id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;

    }

    public FoodItems searchByDescription(String id) throws SQLException {
        String sql = "SELECT * FROM FoodItems WHERE description = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        FoodItems foodItems = null;

        if (resultSet.next()) {
            String fid = resultSet.getString(1);
            String description = resultSet.getString(2);
            double amount = Double.valueOf(resultSet.getString(3));
            int qty = Integer.parseInt(resultSet.getString(4));


            foodItems = new FoodItems(fid,description,amount,qty);
        }
        return foodItems;
    }

    public List<FoodItems> getAll() throws SQLException {
        String sql = "SELECT * FROM FoodItems";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<FoodItems> foodItemsList = new ArrayList<>();
        while (resultSet.next()) {
            String fid = resultSet.getString(1);
            String description = resultSet.getString(2);
            double amount = Double.parseDouble(resultSet.getString(3));
            int qty = Integer.parseInt(resultSet.getString(4));

            FoodItems foodItems = new FoodItems(fid,description,amount,qty);
            foodItemsList.add(foodItems);
        }
        return foodItemsList;
    }

    public FoodItems searchByCode(String foodItemDValue) throws SQLException {
        String sql = "SELECT * FROM FoodItems WHERE description = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, foodItemDValue);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new FoodItems(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            );
        }
        return null;
    }

    public List<String> getIds() throws SQLException {
        String sql = "SELECT description FROM FoodItems";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> IdList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while(resultSet.next()) {
            IdList.add(resultSet.getString(1));
        }
        return IdList;
    }

    public String currentId() throws SQLException {
        String sql = "SELECT id FROM FoodItems ORDER BY id desc LIMIT 1";

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
        }

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
        String sql = "UPDATE FoodItems SET qtyOnHand = qtyOnHand - ? WHERE id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, Id);

        return pstm.executeUpdate() > 0;
    }
}