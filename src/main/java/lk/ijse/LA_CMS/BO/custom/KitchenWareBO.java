package lk.ijse.LA_CMS.BO.custom;

import lk.ijse.LA_CMS.BO.SuperBO;
import lk.ijse.LA_CMS.DAO.CrudDAO;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DTO.KitchenWareDTO;
import lk.ijse.LA_CMS.Entity.KitchenWare;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface KitchenWareBO extends SuperBO {
    public boolean save(KitchenWareDTO kitchenWare) throws SQLException, ClassNotFoundException;
    public boolean delete(String id) throws SQLException, ClassNotFoundException;
    public boolean update(KitchenWareDTO kitchenWare) throws SQLException, ClassNotFoundException;
    public List<String> getIds() throws SQLException, ClassNotFoundException;
    public KitchenWareDTO searchByCode(String kid) throws SQLException, ClassNotFoundException;
    public List<KitchenWareDTO> getAll() throws SQLException, ClassNotFoundException;
    public String currentId() throws SQLException, ClassNotFoundException;
}
