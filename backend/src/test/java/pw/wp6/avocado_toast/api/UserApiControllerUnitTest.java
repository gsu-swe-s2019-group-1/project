package pw.wp6.avocado_toast.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pw.wp6.avocado_toast.invoker.Swagger2SpringBoot;
import pw.wp6.avocado_toast.model.AccountType;
import pw.wp6.avocado_toast.model.CreateUserObject;
import pw.wp6.avocado_toast.model.LoginParameters;
import pw.wp6.avocado_toast.model.User;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Swagger2SpringBoot.class)
public class UserApiControllerUnitTest {

    @Autowired
    private UserApi api;

    @Test
    public void userCreationAndLogin() throws Exception {
        var body = new CreateUserObject()
                .accountType(AccountType.CUSTOMER)
                .name("Test")
                .username("abc")
                .password("123")
                .ssn("000-11-2222");
        ResponseEntity<User> responseEntity = api.createUser(body);
        User user = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user.getName(), "Test");
        assertEquals(user.getUsername(), "abc");
        assertEquals(user.getSsn(), "000-11-2222");

        ResponseEntity<User> loginResponse = api.loginUser(
                new LoginParameters()
                        .userName("abc")
                        .password("123"));
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertEquals(user.getId(), loginResponse.getBody().getId());
    }

    @Test(expected = ApiException.class)
    public void invalidLoginFails() throws Exception {
        ResponseEntity<User> loginResponse = api.loginUser(
                new LoginParameters()
                        .userName("oaefikjff")
                        .password("aj9gvre"));
        // throws UNAUTHORIZED exception at this point
    }

}
