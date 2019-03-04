package pw.wp6.avocado_toast.invoker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection c;

    static {
        try {
            c = DriverManager.getConnection("jdbc:hsqldb:file:./db", "SA", "");
        } catch (SQLException e) {
            e.printStackTrace();
            c = null;
        }
    }

    public DatabaseConnection() throws SQLException {
    }
}
