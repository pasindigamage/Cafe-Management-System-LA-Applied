package lk.ijse.LA_CMS.BO.custom.Impl;

import lk.ijse.LA_CMS.BO.custom.FoodItemsBO;
import lk.ijse.LA_CMS.DAO.DAOFactory;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DAO.custom.FoodItemsDAO;
import lk.ijse.LA_CMS.DAO.custom.impl.FoodItemsDAOImpl;
import lk.ijse.LA_CMS.DTO.FoodItemsDTO;
import lk.ijse.LA_CMS.Entity.FoodItems;
import lk.ijse.LA_CMS.Entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodItemsBOImpl implements FoodItemsBO {
    FoodItemsDAO foodItemsDAO= (FoodItemsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.FOODITEMS);
    public boolean save(FoodItemsDTO foodItems) throws SQLException, ClassNotFoundException {
        return foodItemsDAO.save(new FoodItems(foodItems.getId(),foodItems.getDescription(),
                foodItems.getUnitPrice(),foodItems.getQtyOnHand()));
    }

    public boolean update(FoodItemsDTO foodItems) throws SQLException, ClassNotFoundException {
        return foodItemsDAO.update(new FoodItems(foodItems.getId(),foodItems.getDescription(),
                foodItems.getUnitPrice(),foodItems.getQtyOnHand()));
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return foodItemsDAO.delete(id);
    }

    public FoodItemsDTO searchByDescription(String id) throws SQLException, ClassNotFoundException {
        FoodItems foodItems=foodItemsDAO.searchByDescription(id);
        return new FoodItemsDTO(foodItems.getId(),foodItems.getDescription(),
                foodItems.getUnitPrice(),foodItems.getQtyOnHand());
    }

    public List<FoodItemsDTO> getAll() throws SQLException, ClassNotFoundException {
        List<FoodItemsDTO> foodItemsList = new ArrayList<>();
        List<FoodItems>foodItems=foodItemsDAO.getAll();
        for (FoodItems foodItem:foodItems){
            FoodItemsDTO foodItemsDTO=new FoodItemsDTO(foodItem.getId(),foodItem.getDescription(),
                    foodItem.getUnitPrice(),foodItem.getQtyOnHand());
            foodItemsList.add(foodItemsDTO);
        }
        return foodItemsList;
    }

    public FoodItemsDTO searchByCode(String foodItemDValue) throws SQLException, ClassNotFoundException {
        FoodItems foodItems=foodItemsDAO.searchByCode(foodItemDValue);
        return new FoodItemsDTO(foodItems.getId(),foodItems.getDescription(),
                foodItems.getUnitPrice(),foodItems.getQtyOnHand());
    }

    public List<String> getIds() throws SQLException, ClassNotFoundException {
        List<String> IdList = foodItemsDAO.getIds();
        return IdList;
    }

    public String currentId() throws SQLException, ClassNotFoundException {
            return foodItemsDAO.currentId();
    }

    @Override
    public boolean update1(List<OrderDetail> odList) throws SQLException {
        return false;
    }

    @Override
    public boolean updateQty(String Id, int qty) throws SQLException {
        return false;
    }

  /*  public  boolean update1(List<OrderDetail> odList) throws SQLException {
        return foodItemsDAO.update1();
    }

   public boolean updateQty(String Id, int qty) throws SQLException {
        return foodItemsDAO.updateQty(Id,qty);
    }*/
}