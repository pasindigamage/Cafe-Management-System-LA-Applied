package lk.ijse.LA_CMS.DAO.custom.impl;

import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DAO.custom.SupplierDAO;
import lk.ijse.LA_CMS.db.DbConnection;
import lk.ijse.LA_CMS.Entity.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    public boolean save(Supplier supplier) throws SQLException {
        return SQLUtil.execute(("INSERT INTO Supplier VALUES(?, ?, ?, ?, ?, ?)"),
                supplier.getId(),supplier.getNic(),supplier.getName(),
                supplier.getCompanyAddress(),supplier.getEmail(),supplier.getContact());
    }

    public boolean update(Supplier supplier) throws SQLException {
        return SQLUtil.execute(("UPDATE Supplier set nic = ?, name = ?, companyAddress = ?, " +
                "email = ?, contact = ? where id =? "),
                supplier.getNic(),supplier.getName(), supplier.getCompanyAddress(),
                supplier.getEmail(),supplier.getContact(),supplier.getId());
    }

    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute(("DELETE FROM Supplier WHERE id = ?"),id);
    }

    public Supplier searchByCode(String id) throws SQLException {
        ResultSet resultSet=SQLUtil.execute(("SELECT * FROM Supplier WHERE id = ?"),id);
        resultSet.next();
        return new Supplier(id+"",resultSet.getString("nic"),resultSet.getString("name"),
                resultSet.getString("companyAddress"),resultSet.getString("email"),
                resultSet.getString("contact"));
    }

    public List<Supplier> getAll() throws SQLException {
        List<Supplier> supplierList = new ArrayList<>();

        ResultSet resultSet=SQLUtil.execute("SELECT * FROM Supplier");
        while (resultSet.next()) {
            Supplier supplier = new Supplier(resultSet.getString("id"),resultSet.
                    getString("nic"),resultSet.getString("name"),
                    resultSet.getString("companyAddress"),resultSet.getString("email"),
                    resultSet.getString("contact"));
            supplierList.add(supplier);
        }
        return supplierList;
    }

    public List<String> getIds() throws SQLException {
        List<String> IdList = new ArrayList<>();
        ResultSet resultSet=SQLUtil.execute("SELECT name FROM Supplier");
        while(resultSet.next()) {
            IdList.add(resultSet.getString(1));
        }
        return IdList;
    }

    public Supplier searchByDescription(String sName) throws SQLException {
        ResultSet resultSet=SQLUtil.execute(("SELECT * FROM Supplier WHERE name = ?"),sName);
        if(resultSet.next()) {
            return new Supplier(resultSet.getString("id"),resultSet.
                    getString("nic"),sName,
                    resultSet.getString("companyAddress"),resultSet.getString("email"),
                    resultSet.getString("contact"));

        }
        return null;
    }

    public String currentId() throws SQLException {
       ResultSet resultSet=SQLUtil.execute("SELECT id FROM Supplier ORDER BY id desc LIMIT 1");
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
        }
    }
