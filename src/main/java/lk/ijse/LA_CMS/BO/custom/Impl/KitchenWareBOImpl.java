package lk.ijse.LA_CMS.BO.custom.Impl;

import lk.ijse.LA_CMS.BO.custom.KitchenWareBO;
import lk.ijse.LA_CMS.DAO.DAOFactory;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.DAO.custom.KitchenWareDAO;
import lk.ijse.LA_CMS.DTO.KitchenWareDTO;
import lk.ijse.LA_CMS.Entity.KitchenWare;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KitchenWareBOImpl implements KitchenWareBO {
    KitchenWareDAO kitchenWareDAO= (KitchenWareDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.KITCHENWARE);

    public boolean save(KitchenWareDTO kitchenWare) throws SQLException, ClassNotFoundException {
        return kitchenWareDAO.save(new KitchenWare(kitchenWare.getId(),
                kitchenWare.getSupplierId(),kitchenWare.getDescription(),
                kitchenWare.getQty()));
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return kitchenWareDAO.delete(id);
    }

    public boolean update(KitchenWareDTO  kitchenWare) throws SQLException, ClassNotFoundException {
        return kitchenWareDAO.update(new KitchenWare(kitchenWare.getId(),
                kitchenWare.getSupplierId(),kitchenWare.getDescription(),
                kitchenWare.getQty()));
    }

    public List<String> getIds() throws SQLException, ClassNotFoundException {
        List<String> IdList = kitchenWareDAO.getIds();
        return IdList;
    }

    public KitchenWareDTO searchByCode(String kid) throws SQLException, ClassNotFoundException {
       KitchenWare kitchenWare=kitchenWareDAO.searchByCode(kid);
       return new KitchenWareDTO(kitchenWare.getId(),
               kitchenWare.getSupplierId(),kitchenWare.getDescription(),
               kitchenWare.getQty());
        }

    public List<KitchenWareDTO > getAll() throws SQLException, ClassNotFoundException {
        List<KitchenWareDTO > kitchenWareList = new ArrayList<>();
        List<KitchenWare>kitchenWares=kitchenWareDAO.getAll();
        for (KitchenWare kitchenWare: kitchenWares){
            KitchenWareDTO kitchenWareDTO=new KitchenWareDTO(kitchenWare.getId(),
                    kitchenWare.getSupplierId(),kitchenWare.getDescription(),
                    kitchenWare.getQty());
            kitchenWareList.add(kitchenWareDTO);
        }
        return kitchenWareList;
    }

    public String currentId() throws SQLException, ClassNotFoundException {
        return kitchenWareDAO.currentId();
    }
}
