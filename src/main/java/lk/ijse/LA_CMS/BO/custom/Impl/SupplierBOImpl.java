package lk.ijse.LA_CMS.BO.custom.Impl;

import lk.ijse.LA_CMS.BO.custom.SupplierBO;
import lk.ijse.LA_CMS.DAO.DAOFactory;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DAO.custom.SupplierDAO;
import lk.ijse.LA_CMS.DTO.SupplierDTO;
import lk.ijse.LA_CMS.Entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);

    public boolean save(SupplierDTO supplier) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new Supplier(supplier.getId(), supplier.getNic(), supplier.getName(),
                supplier.getCompanyAddress(), supplier.getEmail(), supplier.getContact()));
    }

    public boolean update(SupplierDTO supplier) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(supplier.getNic(), supplier.getName(), supplier.getCompanyAddress(),
                supplier.getEmail(), supplier.getContact(), supplier.getId()));
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }

    public SupplierDTO searchByCode(String id) throws SQLException, ClassNotFoundException {
        Supplier supplier = supplierDAO.searchByCode(id);
        return new SupplierDTO(supplier.getId(), supplier.getNic(), supplier.getName(),
                supplier.getCompanyAddress(), supplier.getEmail(), supplier.getContact()
        );
    }

    public List<SupplierDTO> getAll() throws SQLException, ClassNotFoundException {
        List<SupplierDTO> supplierList = new ArrayList<>();
        List<Supplier> suppliers = supplierDAO.getAll();
        for (Supplier supplier : suppliers) {
            SupplierDTO supplierDTO = new SupplierDTO(supplier.getId(), supplier.getNic(), supplier.getName(),
                    supplier.getCompanyAddress(), supplier.getEmail(), supplier.getContact());
            supplierList.add(supplierDTO);
        }

        return supplierList;
    }

    public List<String> getIds() throws SQLException, ClassNotFoundException {
        List<String> IdList = supplierDAO.getIds();
        return IdList;
    }

    public SupplierDTO searchByDescription(String sName) throws SQLException, ClassNotFoundException {
        Supplier supplier = supplierDAO.searchByDescription(sName);
        return new SupplierDTO(supplier.getId(), supplier.getNic(), supplier.getName(),
                supplier.getCompanyAddress(), supplier.getEmail(), supplier.getContact()
        );
    }

    public String currentId() throws SQLException, ClassNotFoundException {
        return supplierDAO.currentId();
    }
}