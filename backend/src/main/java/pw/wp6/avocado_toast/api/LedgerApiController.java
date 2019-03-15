package pw.wp6.avocado_toast.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.threetenbp.ThreeTenModule;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.threeten.bp.LocalDate;
import pw.wp6.avocado_toast.Util;
import pw.wp6.avocado_toast.invoker.DatabaseConnection;
import pw.wp6.avocado_toast.model.DailyTransactions;
import pw.wp6.avocado_toast.model.LedgerEntry;
import pw.wp6.avocado_toast.model.TransactionInput;
import pw.wp6.avocado_toast.model.UserTransactions;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-03-04T01:09:12.686-05:00[America/New_York]")
@Controller
public class LedgerApiController implements LedgerApi {

    private static final Logger log = LoggerFactory.getLogger(LedgerApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public LedgerApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
        objectMapper.registerModule(new ThreeTenModule());
    }

    public ResponseEntity<LedgerEntry> createUserTransaction(@ApiParam(value = "Created transaction object", required = true) @Valid @RequestBody TransactionInput body, @ApiParam(value = "ID of a valid user", required = true) @PathVariable("userId") Long userId) throws SQLException {
        String accept = request.getHeader("Accept");
        PreparedStatement createUserTrans = DatabaseConnection.c.prepareStatement(
                "INSERT INTO ledger_entries (user_id, merchant, amount, date_time)\n" +
                        "VALUES (?, ?, ?, current_timestamp);");

        createUserTrans.setLong(1, userId);
        createUserTrans.setString(2, body.getMerchant());
        createUserTrans.setBigDecimal(3, body.getAmount());

        createUserTrans.executeUpdate();
        long key = createUserTrans.getGeneratedKeys().getLong(1);

        PreparedStatement readDate = DatabaseConnection.c.prepareStatement(
                "SELECT date_time\n" +
                        "FROM ledger_entries\n" +
                        "WHERE id = ?;");
        readDate.setLong(1, key);
        ResultSet date = readDate.executeQuery();
        date.next();

        return new ResponseEntity<LedgerEntry>(new LedgerEntry()
                .id(key)
                .userId(userId)
                .amount(body.getAmount())
                .merchant(body.getMerchant())
                .dateTime(Util.dbStringToOffsetDateTime(date.getString(1))
                ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DailyTransactions> getDayTransactions(@ApiParam(value = "Day to get transactions for", required = true) @PathVariable("date") String date) throws SQLException {
        String accept = request.getHeader("Accept");
        PreparedStatement getDayTrans = DatabaseConnection.c.prepareStatement(
                "SELECT id, user_id, amount, merchant, date_time\n" +
                        "FROM ledger_entries\n" +
                        "WHERE DATE(date_time) = ?;");
        getDayTrans.setString(1, date);
        ResultSet transactions = getDayTrans.executeQuery();

        PreparedStatement getVolume = DatabaseConnection.c.prepareStatement(
                "SELECT DATE(date_time) date, SUM(ABS(amount)) total_amout\n" +
                        "FROM ledger_entries\n" +
                        "WHERE date = ?\n" +
                        "GROUP BY date;");
        getVolume.setString(1, date);
        ResultSet volume = getVolume.executeQuery();

        DailyTransactions response = new DailyTransactions();
        if (volume.next()) {
            response.cashFlow(volume.getBigDecimal(2));
        }
        while (transactions.next()) {
            response.addTransactionsItem(new LedgerEntry()
                    .id(transactions.getLong(1))
                    .userId(transactions.getLong(2))
                    .amount(transactions.getBigDecimal(3))
                    .merchant(transactions.getString(4))
                    .dateTime(Util.dbStringToOffsetDateTime(
                            transactions.getString(5)))
            );
        }

        return new ResponseEntity<DailyTransactions>(response, HttpStatus.OK);
    }

    public ResponseEntity<UserTransactions> getUserTransactions(@ApiParam(value = "ID of a valid user", required = true) @PathVariable("userId") Long userId) throws SQLException {
        String accept = request.getHeader("Accept");

        PreparedStatement getUserTrans = DatabaseConnection.c.prepareStatement(
                "SELECT id, user_id, amount, merchant, date_time\n" +
                        "FROM ledger_entries\n" +
                        "WHERE user_id = ?;");
        getUserTrans.setLong(1, userId);
        ResultSet results = getUserTrans.executeQuery();


        PreparedStatement getBalance = DatabaseConnection.c.prepareStatement(
                "SELECT SUM(amount) balance\n" +
                        "FROM ledger_entries\n" +
                        "WHERE user_id = ?;");
        getBalance.setLong(1, userId);
        ResultSet balance = getBalance.executeQuery();
        balance.next();

        UserTransactions response = new UserTransactions();
        if (balance.getBigDecimal(1) != null) {
            response.balance(balance.getBigDecimal(1));
        }
        while (results.next()) {
            response.addTransactionsItem(new LedgerEntry()
                    .id(results.getLong(1))
                    .userId(results.getLong(2))
                    .amount(results.getBigDecimal(3))
                    .merchant(results.getString(4))
                    .dateTime(Util.dbStringToOffsetDateTime(
                            results.getString(5)))
            );
        }

        return new ResponseEntity<UserTransactions>(response, HttpStatus.OK);
    }

}
