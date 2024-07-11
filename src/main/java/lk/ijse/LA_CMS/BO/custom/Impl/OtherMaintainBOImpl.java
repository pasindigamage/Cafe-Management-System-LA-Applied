package lk.ijse.LA_CMS.BO.custom.Impl;

import lk.ijse.LA_CMS.BO.custom.OtherMaintainBO;
import lk.ijse.LA_CMS.DAO.DAOFactory;
import lk.ijse.LA_CMS.DAO.custom.OtherMaintainDAO;
import lk.ijse.LA_CMS.DTO.OtherMaintainsDTO;
import lk.ijse.LA_CMS.Entity.OtherMaintains;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OtherMaintainBOImpl implements OtherMaintainBO {
    OtherMaintainDAO otherMaintainDAO= (OtherMaintainDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.OTHER_MAINTAINS);
    public boolean save(OtherMaintainsDTO otherMaintains) throws SQLException, ClassNotFoundException {
        return otherMaintainDAO.save(new OtherMaintains(
                otherMaintains.getId(),otherMaintains.getDescription(),
                otherMaintains.getDate(),otherMaintains.getAmount()));
                }

    public boolean update(OtherMaintainsDTO otherMaintains) throws SQLException, ClassNotFoundException {
        return otherMaintainDAO.update(new OtherMaintains(
                otherMaintains.getId(),otherMaintains.getDescription(),
                otherMaintains.getDate(),otherMaintains.getAmount()));
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return otherMaintainDAO.delete(id);
    }

    public List<OtherMaintainsDTO> getAll() throws SQLException, ClassNotFoundException {
        List<OtherMaintainsDTO> otherMaintainsList = new ArrayList<>();
        List<OtherMaintains> otherMaintain=otherMaintainDAO.getAll();
        for (OtherMaintains otherMaintains:otherMaintain){
            OtherMaintainsDTO otherMaintainsDTO=new OtherMaintainsDTO(
                    otherMaintains.getId(),otherMaintains.getDescription(),
                    otherMaintains.getDate(),otherMaintains.getAmount());
            otherMaintainsList.add(otherMaintainsDTO);
        }
        return otherMaintainsList;
    }
    public String currentId() throws SQLException, ClassNotFoundException {
        return otherMaintainDAO.currentId();
    }
}
