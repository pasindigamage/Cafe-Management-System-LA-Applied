package lk.ijse.LA_CMS.DAO.custom.impl;

import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DAO.custom.OtherMaintainDAO;
import lk.ijse.LA_CMS.db.DbConnection;
import lk.ijse.LA_CMS.Entity.OtherMaintains;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OtherMaintainDAOImpl implements OtherMaintainDAO {
    public boolean save(OtherMaintains otherMaintains) throws SQLException {
        return SQLUtil.execute(("INSERT INTO otherMaintain VALUES(?, ?, ?, ?)"),
                otherMaintains.getId(),otherMaintains.getDescription(),
                otherMaintains.getDate(),otherMaintains.getAmount());
    }

    public boolean update(OtherMaintains otherMaintains) throws SQLException {
        return SQLUtil.execute(("UPDATE otherMaintain set description = ?, date = ?, amount = ? where id =? "),
                otherMaintains.getDescription(),otherMaintains.getDate(),
                otherMaintains.getAmount(),otherMaintains.getId());
    }

    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute(("DELETE FROM otherMaintain WHERE id = ?"),id);
    }

    @Override
    public OtherMaintains searchByDescription(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    public List<OtherMaintains> getAll() throws SQLException {
        List<OtherMaintains> otherMaintainsList = new ArrayList<>();
        ResultSet resultSet=SQLUtil.execute("SELECT * FROM otherMaintain");
        while (resultSet.next()) {
            OtherMaintains otherMaintains = new OtherMaintains(resultSet.getString(1),
            resultSet.getString(2), Date.valueOf(resultSet.getString(3)),
            resultSet.getString(4));
                    otherMaintainsList.add(otherMaintains);
        }
        return otherMaintainsList;
    }

    @Override
    public OtherMaintains searchByCode(String code) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    public String currentId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT id FROM otherMaintain ORDER BY id desc LIMIT 1");
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
        }

}
