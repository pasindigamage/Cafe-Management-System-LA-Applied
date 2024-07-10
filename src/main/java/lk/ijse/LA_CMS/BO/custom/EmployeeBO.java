package lk.ijse.LA_CMS.BO.custom;

import lk.ijse.LA_CMS.BO.SuperBO;
import lk.ijse.LA_CMS.DTO.EmployeeDTO;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    public boolean save(EmployeeDTO employee) throws SQLException, ClassNotFoundException;
    public boolean update(EmployeeDTO employee) throws SQLException, ClassNotFoundException;
    public boolean delete(String id) throws SQLException, ClassNotFoundException;
    public EmployeeDTO searchByCode(String id) throws SQLException, ClassNotFoundException;
    public List<EmployeeDTO> getAll() throws SQLException, ClassNotFoundException;
    public String currentId() throws SQLException, ClassNotFoundException;
}