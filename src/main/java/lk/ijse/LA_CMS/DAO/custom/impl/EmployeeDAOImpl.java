package lk.ijse.LA_CMS.DAO.custom.impl;

import lk.ijse.LA_CMS.DAO.SQLUtil;
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
        return SQLUtil.execute(("INSERT INTO Employee VALUES(?, ?, ?, ?, ?, ?)"),
        employee.getId(),employee.getName(),employee.getPosition(),employee.getAddress(),employee.getEmail(),employee.getContact());
    }

    public boolean update(Employee employee) throws SQLException {
        return SQLUtil.execute(("UPDATE Employee set name = ?, position = ?, address = ?, email = ?, contact = ? where id =? "),
        employee.getName(),employee.getPosition(),employee.getAddress(),employee.getEmail(),employee.getContact(),employee.getId());
    }

    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute(("DELETE FROM Employee WHERE id = ?"),id);
    }

    @Override
    public Employee searchByDescription(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    public Employee searchByCode(String id) throws SQLException {
        ResultSet resultSet=SQLUtil.execute(("SELECT * FROM Employee WHERE id = ?"),id);
        resultSet.next();
        return new Employee(id+"",
                resultSet.getString("name"),resultSet.getString("position"),
                resultSet.getString("address"),resultSet.getString("email"),
                resultSet.getString("contact"));
    }

    public List<Employee> getAll() throws SQLException {

        List<Employee> employeeList = new ArrayList<>();
        ResultSet resultSet=SQLUtil.execute("SELECT * FROM Employee");
        while (resultSet.next()) {Employee employee=new Employee(resultSet.getString("id"),
                resultSet.getString("name"),
            resultSet.getString("position"),resultSet.getString("address"),
                resultSet.getString("email"),resultSet.getString("contact"));
            employeeList.add(employee);
        }
        return employeeList;
    }

    public List<String> getIds() throws SQLException {
        List<String> idList = new ArrayList<>();
        ResultSet resultSet=SQLUtil.execute(("SELECT id FROM Employee"));
        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public String currentId() throws SQLException {
                ResultSet resultSet=SQLUtil.execute("SELECT id FROM Employee ORDER BY id desc LIMIT 1");
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
    }
}