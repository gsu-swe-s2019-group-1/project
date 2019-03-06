/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.5).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package pw.wp6.avocado_toast.api;

import pw.wp6.avocado_toast.model.DailyTransactions;
import pw.wp6.avocado_toast.model.LedgerEntry;
import org.threeten.bp.LocalDate;
import pw.wp6.avocado_toast.model.TransactionInput;
import pw.wp6.avocado_toast.model.UserTransactions;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-03-04T01:09:12.686-05:00[America/New_York]")
@Api(value = "ledger", description = "the ledger API")
public interface LedgerApi {

    @ApiOperation(value = "Submit a new transaction for the user", nickname = "createUserTransaction", notes = "", response = LedgerEntry.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Transaction successfully submitted", response = LedgerEntry.class),
        @ApiResponse(code = 400, message = "Insufficent funds") })
    @RequestMapping(value = "/ledger/by-user/{userId}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<LedgerEntry> createUserTransaction(@ApiParam(value = "Created transaction object" ,required=true )  @Valid @RequestBody TransactionInput body,@ApiParam(value = "ID of a valid user",required=true) @PathVariable("userId") Long userId) throws SQLException;


    @ApiOperation(value = "Gets a list of transactions & overall cashflow for the bank on the given date", nickname = "getDayTransactions", notes = "", response = DailyTransactions.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = DailyTransactions.class) })
    @RequestMapping(value = "/ledger/by-date/{date}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<DailyTransactions> getDayTransactions(@ApiParam(value = "Day to get transactions for",required=true) @PathVariable("date") LocalDate date);


    @ApiOperation(value = "Gets a list of transactions & an overall balance for a user", nickname = "getUserTransactions", notes = "", response = UserTransactions.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = UserTransactions.class) })
    @RequestMapping(value = "/ledger/by-user/{userId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<UserTransactions> getUserTransactions(@ApiParam(value = "ID of a valid user",required=true) @PathVariable("userId") Long userId);

}
