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
                stmt.execute("PRAGMA foreign_keys = 3");
                stmt.execute("CREATE TABLE IF NOT EXISTS Banker\n" +
                        "(\n" +
                        "  id                            INTEGER  PRIMARY KEY,\n" +
                        "  username               TEXT     UNIQUE NOT NULL,\n" +
                        "  password               TEXT     NOT NULL,\n" +
                        "  name                   TEXT     NOT NULL,\n" +
                        "  account_type           TEXT     NOT NULL\n" +
                        ");");
                stmt.execute("CREATE TABLE IF NOT EXISTS Analyst\n" +
                        "(\n" +
                        "  id                            INTEGER  PRIMARY KEY,\n" +
                        "  username                      TEXT     UNIQUE NOT NULL,\n" +
                        "  password                      TEXT     NOT NULL,\n" +
                        "  name                          TEXT     NOT NULL,\n" +
                        "  account_type                  TEXT     NOT NULL\n" +
                        ");");
                 stmt.execute("CREATE TABLE IF NOT EXISTS Customer\n" +
                        "(\n" +
                        "  id                            INTEGER  PRIMARY KEY,\n" +
                        "  username                      TEXT     UNIQUE NOT NULL,\n" +
                        "  password                      TEXT     NOT NULL,\n" +
                        "  name                          TEXT     NOT NULL,\n" +
                        "  ssn                           INTEGER  NOT NULL,\n" +
                        "  account_type                  TEXT     NOT NULL\n" +
                        ");");
                stmt.execute("CREATE TABLE IF NOT EXISTS Transactions\n" +
                        "(\n" +
                        "  id                            INTEGER     PRIMARY KEY,\n" +
                        "  customer_user_id              INTEGER     NOT NULL REFERENCES Customer (id),\n" +
                        "  banker_user_id                INTEGER     NOT NULL REFERENCES Banker (id),\n" +
                        "  analyst_user_id               INTEGER     NOT NULL REFERENCES Analyst (id),\n" +
                        "  merchant                      TEXT        NOT NULL,\n" +
                        "  amount                        INTEGER     NOT NULL,\n" +
                        "  date_time                     DATETIME    NOT NULL\n" +
                        ");");
                stmt.executeUpdate(
                        "INSERT OR IGNORE INTO Banker (id, username, password, name, account_type)\n" +
                                "VALUES (0, 'Admin',
                                '" +new BCryptPasswordEncoder().encode("admin") +"'
                                ', 'admin', 'BANKER');");
            }
        } catch (SQLException | UnknownHostException e) {
            e.printStackTrace();
            c = null;
        }
    }

    private DatabaseConnection() {
    }
}
