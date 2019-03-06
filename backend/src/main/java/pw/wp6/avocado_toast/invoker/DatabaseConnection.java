package pw.wp6.avocado_toast.invoker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection c;

    static {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:file:./db.db");
            c.prepareCall("CREATE TABLE IF NOT EXISTS users\n" +
                    "(\n" +
                    "  id          INTEGER PRIMARY KEY,\n" +
                    "  name        TEXT,\n" +
                    "  username    TEXT,\n" +
                    "  password    TEXT,\n" +
                    "  ssn         TEXT,\n" +
                    "  account_type TEXT\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS ledger_entries\n" +
                    "(\n" +
                    "  id       INTEGER PRIMARY KEY,\n" +
                    "  user_id FOREIGN KEY REFERENCES users,\n" +
                    "  merchant TEXT,\n" +
                    "  amount   DECIMAL,\n" +
                    "  date_time DATETIME\n" +
                    ");").execute();
        } catch (SQLException e) {
            e.printStackTrace();
            c = null;
        }
    }

    public DatabaseConnection() throws SQLException {
    }
}
