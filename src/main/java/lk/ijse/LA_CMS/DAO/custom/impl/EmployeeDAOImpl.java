package lk.ijse.LA_CMS.DAO.custom.impl;

import lk.ijse.LA_CMS.DAO.custom.EmployeeDAO;
import lk.ijse.LA_CMS.db.DbConnection;
import lk.ijse.LA_CMS.Entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO{
    public boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO Employee VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, employee.getId());
        pstm.setObject(2, employee.getName());
        pstm.setObject(3, employee.getPosition());
        pstm.setObject(4, employee.getAddress());
        pstm.setObject(5, employee.getEmail());
        pstm.setObject(6, employee.getContact());

        return pstm.executeUpdate() > 0;
    }

    public boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE Employee set name = ?, position = ?, address = ?, email = ?, contact = ? where id =? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, employee.getName());
        pstm.setObject(2, employee.getPosition());
        pstm.setObject(3, employee.getAddress());
        pstm.setObject(4, employee.getEmail());
        pstm.setObject(5, employee.getContact());
        pstm.setObject(6, employee.getId());

        return pstm.executeUpdate() > 0;
    }

    public boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Employee WHERE id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    @Override
    public Employee searchByDescription(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    public Employee searchByCode(String id) throws SQLException {
        String sql = "SELECT * FROM Employee WHERE id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        Employee employee = null;

        if (resultSet.next()) {
            String eid = resultSet.getString(1);
            String position = resultSet.getString(2);
            String name = resultSet.getString(3);
            String address = resultSet.getString(4);
            String email = resultSet.getString(5);
            String contact = resultSet.getString(6);


            employee = new Employee(eid, position, name, address, email, contact);
        }
        return employee;
    }

    public List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM Employee";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            String eid = resultSet.getString(1);
            String position = resultSet.getString(2);
            String name = resultSet.getString(3);
            String address = resultSet.getString(4);
            String email = resultSet.getString(5);
            String contact = resultSet.getString(6);

            Employee employee = new Employee(eid, position, name, address, email, contact);
            employeeList.add(employee);
        }
        return employeeList;
    }

    public List<String> getIds() throws SQLException {
        String sql = "SELECT id FROM Employee";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public String currentId() throws SQLException {
        String sql = "SELECT id FROM Employee ORDER BY id desc LIMIT 1";

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