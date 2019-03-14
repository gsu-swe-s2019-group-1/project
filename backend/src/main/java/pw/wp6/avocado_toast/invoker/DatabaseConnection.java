package pw.wp6.avocado_toast.invoker;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    public static Connection c;

    static {
        try {
            String dbPath = "./db.db";  // local dir during dev
            if (InetAddress.getLocalHost().getHostName().equals("avocado-toast")) {
                // another path during deploy
                dbPath = "/var/db/db.db";
            }
            c = DriverManager.getConnection("jdbc:sqlite:file:" + dbPath);
            try (Statement stmt = c.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = 1");
                stmt.execute("CREATE TABLE IF NOT EXISTS users\n" +
                        "(\n" +
                        "  id           INTEGER PRIMARY KEY,\n" +
                        "  name         TEXT UNIQUE NOT NULL,\n" +
                        "  username     TEXT NOT NULL,\n" +
                        "  password     TEXT NOT NULL,\n" +
                        "  ssn          TEXT NOT NULL,\n" +
                        "  account_type TEXT NOT NULL\n" +
                        ");");
                stmt.execute("CREATE TABLE IF NOT EXISTS ledger_entries\n" +
                        "(\n" +
                        "  id        INTEGER PRIMARY KEY,\n" +
                        "  user_id   INTEGER  NOT NULL REFERENCES users (id),\n" +
                        "  merchant  TEXT     NOT NULL,\n" +
                        "  amount    DECIMAL  NOT NULL,\n" +
                        "  date_time DATETIME NOT NULL\n" +
                        ");");
                stmt.executeUpdate(
                        "INSERT OR IGNORE INTO users (id, name, username, password, ssn, account_type)\n" +
                                "VALUES (0, 'Admin', 'admin', 'admin', '000-00-0000', 'banker');");
            }
        } catch (SQLException | UnknownHostException e) {
            e.printStackTrace();
            c = null;
        }
    }

    private DatabaseConnection() {
    }
}
