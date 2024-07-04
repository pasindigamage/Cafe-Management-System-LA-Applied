package lk.ijse.LA_CMS.DAO.custom.impl;

import lk.ijse.LA_CMS.DAO.custom.KitchenWareDAO;
import lk.ijse.LA_CMS.db.DbConnection;
import lk.ijse.LA_CMS.Entity.KitchenWare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KitchenWareDAOImpl implements KitchenWareDAO {
    public boolean save(KitchenWare kitchenWare) throws SQLException, ClassNotFoundException {
        String sql ="INSERT INTO KitchenWare VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, kitchenWare.getId());
        pstm.setObject(2, kitchenWare.getSupplierId());
        pstm.setObject(3, kitchenWare.getDescription());
        pstm.setObject(4, kitchenWare.getQty());
        return pstm.executeUpdate() > 0;
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM KitchenWare WHERE id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    @Override
    public KitchenWare searchByDescription(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    public boolean update(KitchenWare kitchenWare) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE KitchenWare set supplierId = ?, description = ?,qty = ? where id =? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, kitchenWare.getSupplierId());
        pstm.setObject(2, kitchenWare.getDescription());
        pstm.setObject(3, kitchenWare.getQty());
        pstm.setObject(4, kitchenWare.getId());

        return pstm.executeUpdate() > 0;
    }

    public List<String> getIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT description FROM KitchenWare";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> IdList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while(resultSet.next()) {
            IdList.add(resultSet.getString(1));
        }
        return IdList;
    }

    public KitchenWare searchByCode(String kid) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM KitchenWare WHERE description = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, kid);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new KitchenWare(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return null;
    }

    public List<KitchenWare> getAll() throws SQLException, ClassNotFoundException {
        String sql = "\n" +
                " SELECT KitchenWare.id, Supplier.name, KitchenWare.description, KitchenWare.qty " +
                "FROM KitchenWare join Supplier on KitchenWare.supplierId = Supplier.id;\n";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<KitchenWare> kitchenWareList = new ArrayList<>();
        while (resultSet.next()) {
            String Id = resultSet.getString(1);
            String supName = resultSet.getString(2);
            String description = resultSet.getString(3);
            String qty = resultSet.getString(4);

            KitchenWare kitchenWare = new KitchenWare(Id,supName,description,qty);
            kitchenWareList.add(kitchenWare);
        }
        return kitchenWareList;
    }

    public String currentId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id FROM KitchenWare ORDER BY id desc LIMIT 1";

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
        }
    }
}
