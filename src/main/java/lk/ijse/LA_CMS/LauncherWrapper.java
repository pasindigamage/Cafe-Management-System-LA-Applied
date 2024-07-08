package lk.ijse.LA_CMS;

import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.Entity.KitchenWare;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LauncherWrapper {
    public static void main(String[] args) {
        Launcher.main(args);
    }
}




/*
public List<KitchenWare> getAll() throws SQLException, ClassNotFoundException {
    List<KitchenWare> kitchenWareList = new ArrayList<>();

        /*String sql = "\n" +
                " SELECT KitchenWare.id, Supplier.name, KitchenWare.description, KitchenWare.qty " +
                "FROM KitchenWare join Supplier on KitchenWare.supplierId = Supplier.id;\n";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

    ResultSet resultSet=SQLUtil.execute(" SELECT KitchenWare.id, Supplier.name, KitchenWare.description, " +
            "KitchenWare.qty FROM KitchenWare join Supplier on KitchenWare.supplierId = Supplier.id;"
    );
    while (resultSet.next()) {
            /*String Id = resultSet.getString(1);
            String supName = resultSet.getString(2);
            String description = resultSet.getString(3);
            String qty = resultSet.getString(4);

        KitchenWare kitchenWare = new KitchenWare(resultSet.getString("id"),
                resultSet.getString("name"),resultSet.getString("description"),
                resultSet.getString("qty"));
        kitchenWareList.add(kitchenWare);
    }
    return kitchenWareList;
}


KitchenWareMaintains kitchenWareMaintains =
                    new KitchenWareMaintains(resultSet.getString("id"),
                            resultSet.getString("kitchenWareId"),resultSet.getString("description"),
                            resultSet.getString("date"),resultSet.getString("amount"));

 */
