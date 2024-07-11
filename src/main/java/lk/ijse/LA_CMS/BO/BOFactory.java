package lk.ijse.LA_CMS.BO;

import lk.ijse.LA_CMS.BO.custom.Impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){}

    public enum BOType{
        CREDINTIAL, EMPLOYEE, FOODITEMS, INVENTORY,
        KITCHENWARE, KITCHENWARE_MAINTAINS, PLACE_ORDER,
        OTHER_MAINTAINS, SUPPLIER
    }

    public static BOFactory getBoFactory(){return boFactory==null ? boFactory=new BOFactory() :boFactory;}

    public SuperBO getBO(BOType boType){
        switch (boType){
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case FOODITEMS:
                return new FoodItemsBOImpl();
            case KITCHENWARE:
                return new KitchenWareBOImpl();
            case KITCHENWARE_MAINTAINS:
                return new KitchenWareMaintainBOImpl();
            case OTHER_MAINTAINS:
                return new OtherMaintainBOImpl();
            case CREDINTIAL:
                return new CredintialBOImpl();

        }
        return null;
    }

}
