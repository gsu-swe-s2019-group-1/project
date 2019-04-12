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
                stmt.execute("CREATE TABLE IF NOT EXISTS Transactions\n" +
                        "(\n" +
                        "  id                   INTEGER     PRIMARY KEY,\n" +
                        "  merchant             TEXT        NOT NULL,\n" +
                        "  amount               INTEGER     NOT NULL,\n" +
                        "  date_time            DATETIME    NOT NULL\n" +
                        ");");
                stmt.execute("CREATE TABLE IF NOT EXISTS Banker\n" +
                        "(\n" +
                        "  id                   INTEGER  PRIMARY KEY,\n" +
                        "  banker_user_id       INTEGER  NOT NULL REFERENCES Transactions (id),\n" +
                        "  banker_username      TEXT     UNIQUE NOT NULL,\n" +
                        "  banker_password      TEXT     NOT NULL,\n" +
                        "  name                 TEXT     NOT NULL,\n" +
                        "  account_type         TEXT     NOT NULL\n" +
                        ");");
                stmt.execute("CREATE TABLE IF NOT EXISTS Analyst\n" +
                        "(\n" +
                        "  id                   INTEGER  PRIMARY KEY,\n" +
                        "  analyst_user_id      INTEGER  NOT NULL REFERENCES Transactions (id),\n" +
                        "  analyst_username     TEXT     UNIQUE NOT NULL,\n" +
                        "  analyst_password     TEXT     NOT NULL,\n" +
                        "  name                 TEXT     NOT NULL,\n" +
                        "  account_type         TEXT     NOT NULL\n" +
                        ");");
                 stmt.execute("CREATE TABLE IF NOT EXISTS Customer\n" +
                        "(\n" +
                        "  id                   INTEGER  PRIMARY KEY,\n" +
                        "  customer_user_id     INTEGER  NOT NULL REFERENCES Transactions (id),\n" +
                        "  Customer_username    TEXT     UNIQUE NOT NULL,\n" +
                        "  customer_password    TEXT     NOT NULL,\n" +
                        "  name                 TEXT     NOT NULL,\n" +
                        "  balance              INTEGER  NOT NULL,\n" +
                        "  overdraft            BOOLEAN  NOT NULL,\n" +
                        "  ssn                  INTEGER  NOT NULL,\n" +
                        "  account_type         TEXT     NOT NULL\n" +
                        ");");
                stmt.executeUpdate(
                        "INSERT OR IGNORE INTO Transactions (id, merchant, amount, date_time)\n" +
                                "VALUES (0, 'Employee', 0,'2019-01-01 01:00:00'");
            }
        } catch (SQLException | UnknownHostException e) {
            e.printStackTrace();
            c = null;
        }
    }

    private DatabaseConnection() {
    }
}
