package lk.ijse.LA_CMS.BO.custom.Impl;

import lk.ijse.LA_CMS.BO.custom.EmployeeBO;
import lk.ijse.LA_CMS.DAO.DAOFactory;
import lk.ijse.LA_CMS.DAO.custom.EmployeeDAO;
import lk.ijse.LA_CMS.DTO.EmployeeDTO;
import lk.ijse.LA_CMS.Entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO= (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    public boolean save(EmployeeDTO employee) throws SQLException, ClassNotFoundException {
       return employeeDAO.save(new Employee(employee.getId(),employee.getName(),employee.getPosition(),
               employee.getAddress(),employee.getEmail(),employee.getContact()));
    }

    public boolean update(EmployeeDTO employee) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(employee.getName(),employee.getPosition(),employee.getAddress(),
                employee.getEmail(),employee.getContact(),employee.getId()));
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    public EmployeeDTO searchByCode(String id) throws SQLException, ClassNotFoundException {
        Employee employee=employeeDAO.searchByCode(id);
        return new EmployeeDTO(employee.getId(),employee.getName(),employee.getPosition(),
                employee.getAddress(),employee.getEmail(),employee.getContact());
    }

    public List<EmployeeDTO> getAll() throws SQLException, ClassNotFoundException {
       List<Employee>employees=employeeDAO.getAll();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee employee:employees){
            EmployeeDTO employeeDTO=new EmployeeDTO(employee.getId(),employee.getName(),employee.getPosition(),
                    employee.getAddress(),employee.getEmail(),employee.getContact());
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }

    public String currentId() throws SQLException, ClassNotFoundException {
            return employeeDAO.currentId();
    }
}