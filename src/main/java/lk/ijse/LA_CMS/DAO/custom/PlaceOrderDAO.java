package lk.ijse.LA_CMS.DAO.custom;

import lk.ijse.LA_CMS.DAO.CrudDAO;
import lk.ijse.LA_CMS.Entity.PlaceOrder;

public interface PlaceOrderDAO extends CrudDAO<PlaceOrder> {
    public boolean placeOrder(PlaceOrder po) throws ClassNotFoundException;
    }
