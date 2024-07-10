package lk.ijse.LA_CMS.BO.custom.Impl;

import lk.ijse.LA_CMS.BO.custom.KitchenWareMaintainBO;
import lk.ijse.LA_CMS.DAO.DAOFactory;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DAO.custom.KitchenWareMaintainDAO;
import lk.ijse.LA_CMS.DTO.KitchenWareMaintainsDTO;
import lk.ijse.LA_CMS.Entity.KitchenWare;
import lk.ijse.LA_CMS.Entity.KitchenWareMaintains;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KitchenWareMaintainBOImpl implements KitchenWareMaintainBO {

    KitchenWareMaintainDAO kitchenWareMaintainDAO= (KitchenWareMaintainDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.KITCHENWARE_MAINTAINS);
    public boolean save(KitchenWareMaintainsDTO kitchenWareMaintains) throws SQLException, ClassNotFoundException {
        return kitchenWareMaintainDAO.save(new KitchenWareMaintains(
                kitchenWareMaintains.getId(),kitchenWareMaintains.getKitchenWareId(),
                kitchenWareMaintains.getDescription(),kitchenWareMaintains.getDate(),kitchenWareMaintains.getAmount()));
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return kitchenWareMaintainDAO.delete(id);
        }

    public boolean update(KitchenWareMaintainsDTO kitchenWareMaintains) throws SQLException, ClassNotFoundException {
      return kitchenWareMaintainDAO.update(new KitchenWareMaintains(
              kitchenWareMaintains.getId(),kitchenWareMaintains.getKitchenWareId(),
              kitchenWareMaintains.getDescription(),kitchenWareMaintains.getDate(),kitchenWareMaintains.getAmount()));
    }

    public List<KitchenWareMaintainsDTO> getAll() throws SQLException, ClassNotFoundException {
        List<KitchenWareMaintainsDTO> kitchenWareMaintainsList = new ArrayList<>();
        List<KitchenWareMaintains> kwm=kitchenWareMaintainDAO.getAll();
        for (KitchenWareMaintains kitchenWareMaintains:kwm){
            KitchenWareMaintainsDTO kitchenWareMaintainsDTO=new KitchenWareMaintainsDTO(
                    kitchenWareMaintains.getId(),kitchenWareMaintains.getKitchenWareId(),
                    kitchenWareMaintains.getDescription(),kitchenWareMaintains.getDate(),kitchenWareMaintains.getAmount());
            kitchenWareMaintainsList.add(kitchenWareMaintainsDTO);
        }
        return kitchenWareMaintainsList;
    }

    public String currentId() throws SQLException, ClassNotFoundException {
            return kitchenWareMaintainDAO.currentId();
        }
    }
