package lk.ijse.LA_CMS.DAO;

import lk.ijse.LA_CMS.DAO.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){

    }
    
    public enum DAOType{
        CHANGE_PASSWORD, EMPLOYEE, FOODITEMS, INVENTORY_SUPPLIER_DETAIL,
        KITCHENWARE, KITCHENWARE_MAINTAINS, ORDER_DETAIL, ORDERS,
        OTHER_MAINTAINS, PLACE_ORDER, REGISTER, SUPPLIER
    }

    public static DAOFactory getInstance(){
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public CrudDAO getDAO(DAOType daoType){
        switch (daoType){
            case CHANGE_PASSWORD:
                return new ChangePasswordDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case FOODITEMS:
                return new FoodItemsDAOImpl();
            case INVENTORY_SUPPLIER_DETAIL:
                return new InventorySupplierDetailDAOImpl();
            case KITCHENWARE:
                return new KitchenWareDAOImpl();
            case KITCHENWARE_MAINTAINS:
                return new KitchenWareMaintainDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailDAOImpl();
            case ORDERS:
                return new OrdersDAOImpl();
            case OTHER_MAINTAINS:
                return new OtherMaintainDAOImpl();
            case PLACE_ORDER:
                return new PlaceOrderDAOImpl();
            case REGISTER:
                return new RegisterDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            default:
                return null;
        }
    }
}
