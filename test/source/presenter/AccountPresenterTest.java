package presenter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import errors.HandlingErrors;

public class AccountPresenterTest {
    
    private AccountPresenter accountPresenter;
    
    @Before
    public void setUp() {
        accountPresenter = new AccountPresenter();
    }

    @Test
    public void testSaveAccountWithInitialBalance() {
        try {
            assertTrue(accountPresenter.saveAccount("123456789", "2024/03/17", 1000, AccountPresenter.CURRENT));
        } catch (HandlingErrors e) {
            fail("Error inesperado: " + e.getMessage());
        }
    }
    
    @Test
    public void testFindAccount() {
        try {
            assertTrue(accountPresenter.saveAccount("987654321", "2024/03/17", 500, AccountPresenter.SAVINGS));
            String[] accountInfo = accountPresenter.findAccount("987654321");
            assertNotNull(accountInfo);
            assertEquals("987654321", accountInfo[0]);
        } catch (HandlingErrors e) {
            fail("Error inesperado: " + e.getMessage());
        }
    }
    
    @Test
    public void testWithdrawFromAccount() {
        try {
            assertTrue(accountPresenter.saveAccount("777777777", "2024/03/17", 1500, AccountPresenter.CURRENT));
            assertTrue(accountPresenter.withdraw("777777777", 500));
        } catch (Exception e) {
            fail("Error inesperado: " + e.getMessage());
        }
    }
    
    @Test
    public void testTransferBetweenAccounts() {
        try {
            assertTrue(accountPresenter.saveAccount("111111111", "2024/03/17", 2000, AccountPresenter.SAVINGS));
            assertTrue(accountPresenter.saveAccount("222222222", "2024/03/17", 1000, AccountPresenter.CURRENT));
            assertTrue(accountPresenter.transfer("111111111", "222222222", 500));
        } catch (Exception e) {
            fail("Error inesperado: " + e.getMessage());
        }
    }

}
