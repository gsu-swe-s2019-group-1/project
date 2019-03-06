package pw.wp6.avocado_toast.invoker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    public static Connection c;

    static {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:file:./db.db");
            try (Statement stmt = c.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS users\n" +
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
                        "  user_id  INTEGER,\n" +
                        "  FOREIGN KEY(user_id) REFERENCES users(id),\n" +
                        "  merchant TEXT,\n" +
                        "  amount   DECIMAL,\n" +
                        "  date_time DATETIME\n" +
                        ");");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            c = null;
        }
    }

    public DatabaseConnection() throws SQLException {
    }
}
