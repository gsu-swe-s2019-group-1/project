package pw.wp6.avocado_toast.api;

import pw.wp6.avocado_toast.model.AccountType;
import pw.wp6.avocado_toast.model.CreateUserObject;
import pw.wp6.avocado_toast.model.LoginParameters;
import pw.wp6.avocado_toast.model.User;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserApiControllerIntegrationTest {

    @Autowired
    private UserApi api;

    @Test
    public void createUserTest() throws Exception {
        var body = new CreateUserObject();
        ResponseEntity<User> responseEntity = api.createUser(body);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void getUserListTest() throws Exception {
        AccountType accountType = AccountType.ANALYSIST;
        ResponseEntity<List<User>> responseEntity = api.getUserList(accountType.name());
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void loginUserTest() throws Exception {
        LoginParameters body = new LoginParameters();
        ResponseEntity<User> responseEntity = api.loginUser(body);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

}
