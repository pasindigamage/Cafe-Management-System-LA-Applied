package lk.ijse.LA_CMS.BO;

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
            case CREDINTIAL:
                return null;
        }
        return null;
    }

}
