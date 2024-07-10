package lk.ijse.LA_CMS.BO.custom;

import lk.ijse.LA_CMS.BO.SuperBO;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DTO.KitchenWareMaintainsDTO;
import lk.ijse.LA_CMS.Entity.KitchenWareMaintains;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface KitchenWareMaintainBO extends SuperBO {
    public boolean save(KitchenWareMaintainsDTO kitchenWareMaintains) throws SQLException, ClassNotFoundException;
    public boolean delete(String id) throws SQLException, ClassNotFoundException;
    public boolean update(KitchenWareMaintainsDTO kitchenWareMaintains) throws SQLException, ClassNotFoundException;
    public List<KitchenWareMaintainsDTO> getAll() throws SQLException, ClassNotFoundException;
    public String currentId() throws SQLException, ClassNotFoundException;
}