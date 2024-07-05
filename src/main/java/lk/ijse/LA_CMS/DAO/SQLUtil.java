package lk.ijse.LA_CMS.DAO;

import lk.ijse.LA_CMS.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtil {

    public static <T>T execute(String sql, Object... args) throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject((i+1),args[i]);
        }
        if (sql.startsWith("SELECT")) {
            return (T) preparedStatement.executeQuery();
        }else {
         return (T) (Boolean) (preparedStatement.executeUpdate()>0);
        }
    }
}
