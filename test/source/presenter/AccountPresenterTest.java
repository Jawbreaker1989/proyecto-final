package presenter;

import errors.HandlingErrors;
import logic.Account;
import logic.TypeAccount;
import org.junit.jupiter.api.Test;

import static logic.TypeAccount.SAVINGS;
import static org.junit.jupiter.api.Assertions.*;

class AccountPresenterTest {

    private AccountPresenter accountPresenter;

    void setup() {
        accountPresenter = new AccountPresenter();
        try {
            accountPresenter.saveAccount("45454353", "2024/02/18", 120_000, SAVINGS);
            accountPresenter.saveAccount("98765432", "2024/03/15", 100_000, SAVINGS);
            accountPresenter.saveAccount("45678901", "2024/03/16", 600_000, TypeAccount.CURRENT);
            accountPresenter.saveAccount("12312345", "2024/03/16", 650_000, TypeAccount.CURRENT);
        } catch (HandlingErrors e) {
            fail("No se pudo configurar la prueba: " + e.getMessage());
        }
    }

    @Test
    void deleteAccount() {
        setup();
        try {
            // Verifica si se puede eliminar una cuenta existente
            Account deletedAccount = accountPresenter.deleteAccount("12312332");
            assertNull(accountPresenter.findAccount("12312332")); // Verifica que la cuenta ya no exista
            assertNotNull(deletedAccount); // Verifica que la cuenta eliminada no sea nula

            // Verifica si se maneja correctamente el intento de eliminar una cuenta inexistente
            assertThrows(HandlingErrors.class, () -> accountPresenter.deleteAccount("99999-99"));
        } catch (HandlingErrors e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    void findAccount() throws HandlingErrors {
        setup();
        // buscar cuenta 98765432
        //["98765432","2024/03/15","100000","0"]
        assertNotNull(accountPresenter.findAccount("98765432"));
        assertEquals("45454353", accountPresenter.findAccount("98765432")[0][0]);
        assertEquals("120000", accountPresenter.findAccount("98765432")[0][2]);
        assertNotNull(accountPresenter.findAccount("12312345"));
        assertNotNull(accountPresenter.findAccount("12312345"));

    }


    @Test
    void deposit() {
        setup();
        try {
            assertEquals(135_000, accountPresenter.deposit("98765432", 30_000));
            assertEquals(651_000, accountPresenter.deposit("12312345", 1000));
            assertEquals(110_000, accountPresenter.deposit("56432-314", 30_000));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void withdraw() {
        setup();
        try {
            assertTrue(accountPresenter.withdraw("4454534", 80_000));
            assertFalse(accountPresenter.withdraw("4454534", 20_000));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    void transfer() {
        try {
            // Verifica si se puede transferir correctamente entre dos cuentas existentes
            assertTrue(accountPresenter.transfer("12345678", "98765432", 50_000));
            // Verifica saldo de cuenta origen
            assertEquals("150000", accountPresenter.findAccount("98765432")); // Verifica saldo de cuenta destino

            // Verifica si se maneja correctamente el intento de transferencia a una cuenta inexistente
            assertFalse(accountPresenter.transfer("12345678", "56432-314", 50_000));

            // Verifica si se maneja correctamente el intento de transferencia de una cantidad mayor al saldo de la cuenta origen
            assertFalse(accountPresenter.transfer("12345678", "98765432", 3_000_000));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    void getAccounts() {
        setup();
        assertEquals(4, accountPresenter.getAccounts().length);
        assertEquals("45454353", accountPresenter.getAccounts()[0][0]);
        assertEquals("2024-02-18", accountPresenter.getAccounts()[0][1]);
        assertEquals("120000", accountPresenter.getAccounts()[0][2]);
        assertEquals("1", accountPresenter.getAccounts()[0][3]);
    }

    @Test
    void averageAccount() {
        setup();
        assertEquals(367500.0, accountPresenter.averageAccount(), 0.1);
    }
}