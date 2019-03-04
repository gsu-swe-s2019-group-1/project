package pw.wp6.avocado_toast.api;

import pw.wp6.avocado_toast.model.DailyTransactions;
import pw.wp6.avocado_toast.model.LedgerEntry;
import org.threeten.bp.LocalDate;
import pw.wp6.avocado_toast.model.TransactionInput;
import pw.wp6.avocado_toast.model.UserTransactions;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-03-03T23:33:49.816-05:00[America/New_York]")
@Controller
public class LedgerApiController implements LedgerApi {

    private static final Logger log = LoggerFactory.getLogger(LedgerApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public LedgerApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<LedgerEntry> createUserTransaction(@ApiParam(value = "Created transaction object" ,required=true )  @Valid @RequestBody TransactionInput body,@ApiParam(value = "ID of a valid user",required=true) @PathVariable("userId") Long userId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<LedgerEntry>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<DailyTransactions> getDayTransactions(@ApiParam(value = "Day to get transactions for",required=true) @PathVariable("date") LocalDate date) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<DailyTransactions>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<UserTransactions> getUserTransactions(@ApiParam(value = "ID of a valid user",required=true) @PathVariable("userId") Long userId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<UserTransactions>(HttpStatus.NOT_IMPLEMENTED);
    }

}
