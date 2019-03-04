/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.5).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package pw.wp6.avocado_toast.api;

import pw.wp6.avocado_toast.model.AccountType;
import pw.wp6.avocado_toast.model.LoginParameters;
import pw.wp6.avocado_toast.model.User;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-03-02T16:08:55.885-05:00[America/New_York]")
@Api(value = "user", description = "the user API")
public interface UserApi {

    @ApiOperation(value = "Create user", nickname = "createUser", notes = "This can only be done by the logged in banker.", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation") })
    @RequestMapping(value = "/user",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> createUser(@ApiParam(value = "Created user object" ,required=true )  @Valid @RequestBody User body) throws SQLException;


    @ApiOperation(value = "get a list of all availible users", nickname = "getUserList", notes = "", response = User.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = User.class, responseContainer = "List") })
    @RequestMapping(value = "/user",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<User>> getUserList(@NotNull @ApiParam(value = "the type of user account to look up", required = true) @Valid @RequestParam(value = "accountType", required = true) AccountType accountType);


    @ApiOperation(value = "Logs user into the system", nickname = "loginUser", notes = "", response = User.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = User.class),
        @ApiResponse(code = 400, message = "Invalid username/password supplied") })
    @RequestMapping(value = "/user/login",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<User> loginUser(@ApiParam(value = "Created user object" ,required=true )  @Valid @RequestBody LoginParameters body);

}
