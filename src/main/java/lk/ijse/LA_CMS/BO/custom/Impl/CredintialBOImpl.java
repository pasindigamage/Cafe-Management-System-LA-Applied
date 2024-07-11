package lk.ijse.LA_CMS.BO.custom.Impl;

import lk.ijse.LA_CMS.BO.custom.CredintialBO;
import lk.ijse.LA_CMS.DAO.DAOFactory;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DAO.custom.ChangePasswordDAO;
import lk.ijse.LA_CMS.DAO.custom.RegisterDAO;
import lk.ijse.LA_CMS.DTO.ChangePasswordDTO;
import lk.ijse.LA_CMS.Entity.User;

import java.sql.SQLException;

public class CredintialBOImpl implements CredintialBO {
    ChangePasswordDAO changePasswordDAO = (ChangePasswordDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CHANGE_PASSWORD);
    RegisterDAO registerDAO= (RegisterDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.REGISTER);


    public boolean changePassword(ChangePasswordDTO dto) throws SQLException, ClassNotFoundException {
        return changePasswordDAO.save(new ChangePasswordDTO(dto.getNewPassword(), dto.getUserName(), dto.getId()));
    }

    public boolean saveUser(User user) throws SQLException, ClassNotFoundException {
        return registerDAO.save(new User(
                user.getEmployeeId(),
                user.getId(),
                user.getUserName(),
                user.getPassword()));
    }
}