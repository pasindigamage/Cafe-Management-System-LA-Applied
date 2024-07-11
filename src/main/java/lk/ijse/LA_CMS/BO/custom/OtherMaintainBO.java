package lk.ijse.LA_CMS.BO.custom;

import lk.ijse.LA_CMS.BO.SuperBO;
import lk.ijse.LA_CMS.DAO.CrudDAO;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DTO.OtherMaintainsDTO;
import lk.ijse.LA_CMS.Entity.OtherMaintains;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OtherMaintainBO extends SuperBO {
    public boolean save(OtherMaintainsDTO otherMaintains) throws SQLException, ClassNotFoundException;
    public boolean update(OtherMaintainsDTO otherMaintains) throws SQLException, ClassNotFoundException;
    public boolean delete(String id) throws SQLException, ClassNotFoundException;
    public List<OtherMaintainsDTO> getAll() throws SQLException, ClassNotFoundException;
    public String currentId() throws SQLException, ClassNotFoundException;
}
