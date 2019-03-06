package pw.wp6.avocado_toast.api;

import pw.wp6.avocado_toast.invoker.DatabaseConnection;
import pw.wp6.avocado_toast.model.AccountType;
import pw.wp6.avocado_toast.model.CreateUserObject;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-03-03T23:33:49.816-05:00[America/New_York]")
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

    public ResponseEntity<User> createUser(@ApiParam(value = "Created user object", required = true) @Valid @RequestBody CreateUserObject body) throws SQLException {
        String accept = request.getHeader("Accept");
        PreparedStatement addUser = DatabaseConnection.c.prepareStatement(
                "INSERT INTO user (name, username, password, ssn, accountType) " +
                        "OUTPUT Inserted.ID " + //  https://stackoverflow.com/a/7917874/2299084
                        "VALUES (?, ?, ?, ?, ?);");

        addUser.setString(1, body.getName());
        addUser.setString(2, body.getUsername());
        addUser.setString(3, body.getPassword());
        addUser.setString(4, body.getSsn());
        addUser.setString(5, body.getAccountType().name());

        ResultSet results = addUser.executeQuery();
        results.first();

        User resultUser = (new User())
                .id(results.getLong(0))
                .name(body.getName())
                .username(body.getUsername())
                .password(body.getPassword())
                .ssn(body.getSsn())
                .accountType(body.getAccountType());

        return new ResponseEntity<User>(resultUser, HttpStatus.OK);
    }

    public ResponseEntity<List<User>> getUserList(@NotNull @ApiParam(value = "the type of user account to look up", required = true) @Valid @RequestParam(value = "accountType", required = true) AccountType accountType) throws SQLException {
        String accept = request.getHeader("Accept");
        PreparedStatement getUsers = DatabaseConnection.c.prepareStatement(
                "SELECT *" +
                        "FROM user" +
                        "WHERE accountType = " +
                        "VALUES (?) ");

        getUsers.setString(1, accountType.name());

        ResultSet results = getUsers.executeQuery();
        results.first();

        return new ResponseEntity<List<User>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<User> loginUser(@ApiParam(value = "Created user object", required = true) @Valid @RequestBody LoginParameters body) throws SQLException {
        String accept = request.getHeader("Accept");
        PreparedStatement loginUsers = DatabaseConnection.c.prepareStatement(
                "SELECT *" +
                        "FROM user" +
                        "WHERE username = " +
                        "WHERE password = " +
                        "VALUES (?, ?) ");

        loginUsers.setString(1, body.getUserName());
        loginUsers.setString(1, body.getPassword());

        ResultSet results = loginUsers.executeQuery();
        results.first();

        return new ResponseEntity<User>(HttpStatus.NOT_IMPLEMENTED);
    }

}
