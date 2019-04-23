package pw.wp6.avocado_toast.invoker;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
                stmt.execute("CREATE TABLE IF NOT EXISTS banker\n" +
                        "(\n" +
                        "  id        INTEGER  PRIMARY KEY,\n" +
                        "  username  TEXT     UNIQUE NOT NULL,\n" +
                        "  password  TEXT     NOT NULL,\n" +
                        "  name      TEXT     NOT NULL,\n" +
                        "  ssn       INTEGER  NOT NULL\n" +
                        ");");
                stmt.execute("CREATE TABLE IF NOT EXISTS analyst\n" +
                        "(\n" +
                        "  id        INTEGER  PRIMARY KEY,\n" +
                        "  username  TEXT     UNIQUE NOT NULL,\n" +
                        "  password  TEXT     NOT NULL,\n" +
                        "  name      TEXT     NOT NULL,\n" +
                        "  ssn       INTEGER  NOT NULL\n" +
                        ");");
                 stmt.execute("CREATE TABLE IF NOT EXISTS customer\n" +
                        "(\n" +
                        "  id        INTEGER  PRIMARY KEY,\n" +
                        "  username  TEXT     UNIQUE NOT NULL,\n" +
                        "  password  TEXT     NOT NULL,\n" +
                        "  name      TEXT     NOT NULL,\n" +
                        "  ssn       INTEGER  NOT NULL\n" +
                        ");");
                stmt.execute("CREATE TABLE IF NOT EXISTS transactions\n" +
                        "(\n" +
                        "  id           INTEGER  PRIMARY KEY,\n" +
                        "  customer_id  INTEGER  NOT NULL REFERENCES customer(id),\n" +
                        "  merchant     TEXT     NOT NULL,\n" +
                        "  amount       INTEGER  NOT NULL,\n" +
                        "  date_time    TEXT     NOT NULL\n" +
                        ");");
                stmt.executeUpdate(
                        "INSERT OR IGNORE INTO banker (id, name, password, username, ssn)\n" +
                                "VALUES (0, 'Admin','" + 
                                new BCryptPasswordEncoder().encode("admin") +
                                "', 'admin', '000-11-1111');");
            }
        } catch (SQLException | UnknownHostException e) {
            e.printStackTrace();
            c = null;
        }
    }

    private DatabaseConnection() {
    }
}
