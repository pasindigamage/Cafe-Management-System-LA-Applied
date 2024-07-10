package lk.ijse.LA_CMS.BO.custom;

import lk.ijse.LA_CMS.BO.SuperBO;
import lk.ijse.LA_CMS.DAO.CrudDAO;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DTO.FoodItemsDTO;
import lk.ijse.LA_CMS.Entity.FoodItems;
import lk.ijse.LA_CMS.Entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface FoodItemsBO extends SuperBO {
    public boolean save(FoodItemsDTO foodItems) throws SQLException, ClassNotFoundException;
    public boolean update(FoodItemsDTO foodItems) throws SQLException, ClassNotFoundException;
    public boolean delete(String id) throws SQLException, ClassNotFoundException;
    public FoodItemsDTO searchByDescription(String id) throws SQLException, ClassNotFoundException;
    public FoodItemsDTO searchByCode(String foodItemDValue) throws SQLException, ClassNotFoundException;
    public List<FoodItemsDTO> getAll() throws SQLException, ClassNotFoundException;
    public List<String> getIds() throws SQLException, ClassNotFoundException;
    public String currentId() throws SQLException, ClassNotFoundException;
    public  boolean update1(List<OrderDetail> odList) throws SQLException;
    public boolean updateQty(String Id, int qty) throws SQLException;
}
