package logic;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class HandlingAccountTest {
    private HandlingAccount handlingAccount;

    void setup() {
        handlingAccount = new HandlingAccount();
        handlingAccount.saveAccount(new Account("3856", LocalDate.of(2023, Month.JULY, 25),50_000, TypeAccount.SAVINGS));
        handlingAccount.saveAccount(new Account("9172", LocalDate.of(2022, Month.AUGUST, 10),30_000, TypeAccount.SAVINGS));
        handlingAccount.saveAccount(new Account("2345", LocalDate.of(2018, Month.MARCH, 15), 150_000, TypeAccount.CURRENT));
        handlingAccount.saveAccount(new Account("6789", LocalDate.of(2019, Month.DECEMBER, 20), 200_000, TypeAccount.CURRENT));


    }

    @Test
    void findAccount() {
        setup();
        assertNotNull(handlingAccount.findAccount("3856"));
        assertNotNull(handlingAccount.findAccount("9172"));
        assertNotNull(handlingAccount.findAccount("2345"));
        assertNotNull(handlingAccount.findAccount("6789"));
        assertNull(handlingAccount.findAccount("0000"));
        assertEquals("3856", handlingAccount.findAccount("3856").getNumber());
        assertEquals(150_000, handlingAccount.findAccount("2345").getResidue());
    }

    @Test
    void saveAccount() {
        setup();
        assertEquals(4, handlingAccount.getAccounts().size());
        assertFalse(handlingAccount.saveAccount(new Account("3856", LocalDate.of(2023, Month.JULY, 25),1000, TypeAccount.SAVINGS)));
        assertFalse(handlingAccount.saveAccount(new Account("3856", LocalDate.of(2022, Month.OCTOBER, 5), 100_000, TypeAccount.SAVINGS)));
        assertTrue(handlingAccount.saveAccount(new Account("1234", LocalDate.of(2024, Month.JANUARY, 16),1000, TypeAccount.SAVINGS)));
        assertEquals(5, handlingAccount.getAccounts().size());
    }
    @Test
    void deleteAccount() {
        setup();

        Account accountToDelete = handlingAccount.findAccount("3856");
        assertTrue(handlingAccount.deleteAccount(accountToDelete));
        assertNull(handlingAccount.findAccount("3856"));
    }

    @Test
    void deposit() {
        setup();
        int value = 40_000;
        int aux = handlingAccount.findAccount("3856").getResidue() + value;
        assertEquals(aux, handlingAccount.deposit(handlingAccount.findAccount("3856"), 40_000));
        Account account = handlingAccount.findAccount("6789");
        value = 250_000;
        aux = account.getResidue() + value;
        assertEquals(aux, handlingAccount.deposit(account, 250_000));
    }

    @Test
    void withdraw() {
        setup();
        assertTrue(handlingAccount.withdraw(handlingAccount.findAccount("3856"), 10));

        Account account = handlingAccount.findAccount("2345");
        assertTrue(handlingAccount.withdraw(account, 50_000));
        assertEquals(100_000, account.getResidue());
    }

    @Test
    void transfer() {
        setup();

        Account savingsAccount = handlingAccount.findAccount("3856");
        Account currentAccount = handlingAccount.findAccount("2345");

        // Intentamos transferir 10_000 unidades desde la cuenta de ahorros a la cuenta corriente
        assertTrue(handlingAccount.transfer(savingsAccount, currentAccount, 10_000));
        assertEquals(40_000, savingsAccount.getResidue()); // Verificamos que el saldo de la cuenta de ahorros sea 10,000 después de la transferencia
        assertEquals(160_000, currentAccount.getResidue()); // Verificamos que el saldo de la cuenta corriente sea 160,000 después de la transferencia
    }


    @Test
    void payRate() {
        setup();

        Account savingsAccount = new SavingsAccount("34343",50000);
        handlingAccount.saveAccount(savingsAccount);

        int initialResidue = savingsAccount.getResidue();
        int interestRate = 5; // Supongamos una tasa de interés del 5%
        int expectedInterest = (int) (initialResidue * (interestRate / 100.0));

        assertEquals(expectedInterest, handlingAccount.payRate(savingsAccount));
    }

    @Test
    void averageAccount() {
        setup();
        double value = (double) handlingAccount.getSumAccounts() / handlingAccount.getAccounts().size();
        assertEquals(value, handlingAccount.averageAccount(), 0.1);
        handlingAccount.saveAccount(new Account("7777", LocalDate.now(),1000, TypeAccount.SAVINGS));
        value = (double) handlingAccount.getSumAccounts() / handlingAccount.getAccounts().size();
        assertEquals(value, handlingAccount.averageAccount());
    }


}
