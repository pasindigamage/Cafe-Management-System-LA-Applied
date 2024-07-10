package lk.ijse.LA_CMS.BO;

import lk.ijse.LA_CMS.BO.custom.FoodItemsBO;
import lk.ijse.LA_CMS.BO.custom.Impl.EmployeeBOImpl;
import lk.ijse.LA_CMS.BO.custom.Impl.FoodItemsBOImpl;
import lk.ijse.LA_CMS.BO.custom.Impl.SupplierBOImpl;

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

        }
        return null;
    }

}
