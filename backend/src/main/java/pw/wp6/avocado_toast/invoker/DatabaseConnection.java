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
                        "  id           INTEGER PRIMARY KEY,\n" +
                        "  name         TEXT UNIQUE NOT NULL,\n" +
                        "  username     TEXT NOT NULL,\n" +
                        "  password     TEXT NOT NULL,\n" +
                        "  ssn          TEXT NOT NULL,\n" +
                        "  account_type TEXT NOT NULL\n" +
                        ");\n" +
                        "CREATE TABLE IF NOT EXISTS ledger_entries\n" +
                        "(\n" +
                        "  id        INTEGER PRIMARY KEY,\n" +
                        "  user_id   INTEGER NOT NULL,\n" +
                        "  FOREIGN   KEY(user_id) REFERENCES users(id),\n" +
                        "  merchant  TEXT NOT NULL,\n" +
                        "  amount    DECIMAL NOT NULL,\n" +
                        "  date_time DATETIME NOT NULL\n" +
                        ");");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            c = null;
        }
    }

    public DatabaseConnection() throws SQLException {
    }
}
