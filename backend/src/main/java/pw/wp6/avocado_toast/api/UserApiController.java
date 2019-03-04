package pw.wp6.avocado_toast.api;

import pw.wp6.avocado_toast.invoker.DatabaseConnection;
import pw.wp6.avocado_toast.model.AccountType;
import pw.wp6.avocado_toast.model.LoginParameters;
import pw.wp6.avocado_toast.model.User;
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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-03-02T16:08:55.885-05:00[America/New_York]")
@Controller
public class UserApiController implements UserApi {

    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UserApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> createUser(@ApiParam(value = "Created user object" ,required=true )  @Valid @RequestBody User body) throws SQLException {
        String accept = request.getHeader("Accept");
        PreparedStatement addUser = DatabaseConnection.c.prepareStatement("INSERT INTO user VALUES (id, name, username, password, ssn, accountType");
        addUser.setLong(1, body.getId());
        addUser.setString(2, body.getName());
        addUser.setString(3, body.getUsername());
        addUser.setString(4, body.getPassword());
        addUser.setString(5, body.getSsn());
        addUser.setString(6, body.getAccountType().name());
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<User>> getUserList(@NotNull @ApiParam(value = "the type of user account to look up", required = true) @Valid @RequestParam(value = "accountType", required = true) AccountType accountType) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<List<User>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<User> loginUser(@ApiParam(value = "Created user object" ,required=true )  @Valid @RequestBody LoginParameters body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<User>(HttpStatus.NOT_IMPLEMENTED);
    }

}
