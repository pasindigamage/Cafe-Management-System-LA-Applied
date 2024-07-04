package lk.ijse.LA_CMS.DAO.custom.impl;

import lk.ijse.LA_CMS.DAO.custom.PlaceOrderDAO;
import lk.ijse.LA_CMS.Entity.PlaceOrder;

import java.sql.SQLException;

public class PlaceOrderDAOImpl implements PlaceOrderDAO {

    public static boolean placeOrder(PlaceOrder po) throws ClassNotFoundException {
        try {
            boolean isOrderSaved = OrdersDAOImpl.save(po.getOrder());

            if (isOrderSaved && OrderDetailDAOImpl.save1(po.getOdList())) {
                boolean isQtyUpdated = FoodItemsDAOImpl.update1(po.getOdList());

                if (isQtyUpdated) {
                    return true;
                }
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
