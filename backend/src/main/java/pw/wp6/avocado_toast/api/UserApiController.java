package pw.wp6.avocado_toast.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pw.wp6.avocado_toast.invoker.DatabaseConnection;
import pw.wp6.avocado_toast.model.AccountType;
import pw.wp6.avocado_toast.model.CreateUserObject;
import pw.wp6.avocado_toast.model.LoginParameters;
import pw.wp6.avocado_toast.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                "INSERT INTO users (name, username, password, ssn, account_type) " +
                        "VALUES (?, ?, ?, ?, ?);");

        addUser.setString(1, body.getName());
        addUser.setString(2, body.getUsername());
        // adding a password to the database like this is basically the worst thing ever, but
        // fuck it. Don't reuse passwords between this app and anywhere else.
        addUser.setString(3, body.getPassword());
        addUser.setString(4, body.getSsn());
        addUser.setString(5, body.getAccountType().name());

        addUser.executeUpdate();
        long key = addUser.getGeneratedKeys().getLong(1);

        User resultUser = (new User())
                .id(key)
                .name(body.getName())
                .username(body.getUsername())
                .password(body.getPassword())
                .ssn(body.getSsn())
                .accountType(body.getAccountType());

        return new ResponseEntity<User>(resultUser, HttpStatus.OK);
    }

    public ResponseEntity<List<User>> getUserList(
            @NotNull
            @ApiParam(value = "the type of user account to look up", required = true)
            @Valid
            @RequestParam(value = "accountType", required = true)
                    String accountType) throws SQLException {
        String accept = request.getHeader("Accept");

        AccountType accountType1 = AccountType.fromValue(accountType);
        PreparedStatement getUsers = DatabaseConnection.c.prepareStatement(
                "SELECT id, name, username, ssn, account_type " +
                        "FROM users " +
                        "WHERE account_type = ?;");

        getUsers.setString(1, accountType1.name());

        ResultSet results = getUsers.executeQuery();

        List<User> response = new ArrayList<>();
        while (results.next()) {
            response.add(new User()
                    .id(results.getLong(1))
                    .name(results.getString(2))
                    .username(results.getString(3))
                    .ssn(results.getString(4))
                    .accountType(AccountType.fromValue(results.getString(5))));
        }

        return new ResponseEntity<List<User>>(response, HttpStatus.OK);
    }

    public ResponseEntity<User> loginUser(@ApiParam(value = "Created user object", required = true) @Valid @RequestBody LoginParameters body) throws SQLException {
        String accept = request.getHeader("Accept");
        PreparedStatement loginUsers = DatabaseConnection.c.prepareStatement(
                "SELECT id, name, username, ssn, account_type " +
                        "FROM users " +
                        "WHERE username IS ? AND password IS ?;");

        loginUsers.setString(1, body.getUserName());
        loginUsers.setString(2, body.getPassword());

        ResultSet results = loginUsers.executeQuery();

        results.next();

        return new ResponseEntity<User>(new User()
                .id(results.getLong(1))
                .name(results.getString(2))
                .username(results.getString(3))
                .ssn(results.getString(4))
                .accountType(AccountType.fromValue(results.getString(5))), HttpStatus.OK);
    }

}
