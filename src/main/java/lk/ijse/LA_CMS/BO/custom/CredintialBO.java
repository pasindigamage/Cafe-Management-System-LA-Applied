package lk.ijse.LA_CMS.BO.custom;

import lk.ijse.LA_CMS.BO.SuperBO;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DTO.ChangePasswordDTO;
import lk.ijse.LA_CMS.Entity.User;

import java.sql.SQLException;

public interface CredintialBO extends SuperBO {

    public boolean changePassword(ChangePasswordDTO dto) throws SQLException, ClassNotFoundException;
    public boolean saveUser(User user) throws SQLException, ClassNotFoundException;

}
