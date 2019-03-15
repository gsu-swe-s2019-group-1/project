package pw.wp6.avocado_toast.api;

import pw.wp6.avocado_toast.model.DailyTransactions;
import pw.wp6.avocado_toast.model.LedgerEntry;
import pw.wp6.avocado_toast.model.TransactionInput;
import pw.wp6.avocado_toast.model.UserTransactions;

import java.time.LocalDate;
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
public class LedgerApiControllerIntegrationTest {

    @Autowired
    private LedgerApi api;

    @Test
    public void createUserTransactionTest() throws Exception {
        TransactionInput body = new TransactionInput();
        Long userId = 789L;
        ResponseEntity<LedgerEntry> responseEntity = api.createUserTransaction(body, userId);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void getDayTransactionsTest() throws Exception {
        LocalDate date = LocalDate.of(1990, 1, 1);
        ResponseEntity<DailyTransactions> responseEntity = api.getDayTransactions(date);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void getUserTransactionsTest() throws Exception {
        Long userId = 789L;
        ResponseEntity<UserTransactions> responseEntity = api.getUserTransactions(userId);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

}
