package pw.wp6.avocado_toast.invoker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection c;

    static {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:file:./db.db");
            c.prepareCall("CREATE TABLE IF NOT EXISTS").execute();
        } catch (SQLException e) {
            e.printStackTrace();
            c = null;
        }
    }

    public DatabaseConnection() throws SQLException {
    }
}
