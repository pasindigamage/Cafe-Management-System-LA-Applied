package lk.ijse.LA_CMS.DAO.custom.impl;

import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DAO.custom.KitchenWareDAO;
import lk.ijse.LA_CMS.Entity.KitchenWare;
import lk.ijse.LA_CMS.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KitchenWareDAOImpl implements KitchenWareDAO {
    public boolean save(KitchenWare kitchenWare) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(("INSERT INTO KitchenWare VALUES(?, ?, ?, ?)"),kitchenWare.getId(),
                kitchenWare.getSupplierId(),kitchenWare.getDescription(),
                kitchenWare.getQty());
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(("DELETE FROM KitchenWare WHERE id = ?"),id);
    }

    @Override
    public KitchenWare searchByDescription(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    public boolean update(KitchenWare kitchenWare) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(("UPDATE KitchenWare set supplierId = ?, description = ?,qty = ? where id =? "),
                kitchenWare.getSupplierId(),kitchenWare.getDescription(),kitchenWare.getQty(),kitchenWare.getId());
    }

    public List<String> getIds() throws SQLException, ClassNotFoundException {

        List<String> IdList = new ArrayList<>();
        ResultSet resultSet=SQLUtil.execute("SELECT description FROM KitchenWare");
        while(resultSet.next()) {
            IdList.add(resultSet.getString(1));
        }
        return IdList;
    }

    public KitchenWare searchByCode(String kid) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=SQLUtil.execute(("SELECT * FROM KitchenWare WHERE description = ?"),kid);
        resultSet.next();
        return new KitchenWare(resultSet.getString("id"),resultSet.getString("supplierId"),
                kid+"",resultSet.getString("qty"));
    }

    public List<KitchenWare> getAll() throws SQLException, ClassNotFoundException {
        List<KitchenWare> kitchenWareList = new ArrayList<>();
        ResultSet resultSet=SQLUtil.execute("SELECT KitchenWare.id, Supplier.name, KitchenWare.description, KitchenWare.qty " +
                "FROM KitchenWare join Supplier on KitchenWare.supplierId = Supplier.id;");

        while (resultSet.next()) {
            KitchenWare kitchenWare = new KitchenWare(resultSet.getString(1),
            resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
            kitchenWareList.add(kitchenWare);
        }
        return kitchenWareList;
    }

    public String currentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= SQLUtil.execute("SELECT id FROM KitchenWare ORDER BY id desc LIMIT 1");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}
