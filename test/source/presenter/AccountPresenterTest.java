package presenter;

import errors.HandlingErrors;
import logic.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountPresenterTest {

    private AccountPresenter accountPresenter;

    void setup(){
        accountPresenter = new AccountPresenter();
        try {
            accountPresenter.saveAccount("12345678","2024/03/15",2_500_000, AccountPresenter.SAVINGS);
            accountPresenter.saveAccount("98765432","2024/03/15",100_000, AccountPresenter.SAVINGS);
            accountPresenter.saveAccount("45678901","2024/03/16",600_000, AccountPresenter.CURRENT);
            accountPresenter.saveAccount("12312345","2024/03/16", AccountPresenter.SAVINGS);
        } catch (HandlingErrors e) {
            e.printStackTrace();
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
    void findAccount() {
        setup();
        // buscar cuenta 98765432
        //["98765432","2024/03/15","100000","0"]
        assertNotNull( accountPresenter.findAccount("98765432"));
        assertEquals("98765432", accountPresenter.findAccount("98765432")[0]);
        assertEquals("100000", accountPresenter.findAccount("98765432")[2]);
        assertNotNull( accountPresenter.findAccount("12312345"));
        assertNotNull( accountPresenter.findAccount("12312345"));
        assertNull( accountPresenter.findAccount("3252523"));
    }

    @Test
    void saveAccount() {
        setup();
        try {
            // Verifica si se puede guardar una cuenta nueva
            accountPresenter.saveAccount("99999999", "2024/03/16", 50_000, AccountPresenter.SAVINGS);
            // Verifica si se puede guardar una cuenta con una fecha de apertura en blanco
            accountPresenter.saveAccount("88888888", "", 200_000, AccountPresenter.CURRENT);
            // Verifica si se puede guardar una cuenta con un saldo negativo
            accountPresenter.saveAccount("77777777", "2024/03/16", -20_000, AccountPresenter.SAVINGS);
            // Verifica si se lanzan excepciones al guardar una cuenta con número de cuenta duplicado
            assertThrows(HandlingErrors.class, () -> accountPresenter.saveAccount("12345678", "2024/03/16", 30_000, AccountPresenter.SAVINGS));
        } catch (HandlingErrors e) {
            e.printStackTrace();
        }
    }


    @Test
    void deposit() {
        setup();
        try {
            assertEquals(130_000, accountPresenter.deposit("98765432",30_000));
            assertEquals("130000", accountPresenter.findAccount("98765432")[2]);
            assertEquals(110_000, accountPresenter.deposit("56432-314",30_000));
        } catch (Exception e) {
            System.out.println( e.getMessage());
        }
    }

    @Test
    void withdraw() {
        setup();
        try {
            assertTrue( accountPresenter.withdraw("4454534",80_000));
            assertFalse( accountPresenter.withdraw("4454534",20_000));
        } catch (Exception e) {
            System.out.println( e.getMessage( ) );
        }
    }


    @Test
    void transfer() {
        try {
            // Verifica si se puede transferir correctamente entre dos cuentas existentes
            assertTrue(accountPresenter.transfer("12345678", "98765432", 50_000));
            assertEquals("2450000", accountPresenter.findAccount("12345678")[2]); // Verifica saldo de cuenta origen
            assertEquals("150000", accountPresenter.findAccount("98765432")[2]); // Verifica saldo de cuenta destino

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
        assertEquals("12345678", accountPresenter.getAccounts()[0][0]);
        assertEquals("2024-03-15", accountPresenter.getAccounts()[0][1]);
        assertEquals("2500000", accountPresenter.getAccounts()[0][2]);
        assertEquals("1", accountPresenter.getAccounts()[0][3]);
    }

    @Test
    void averageAccount() {
        setup();
        assertEquals(802500.0, accountPresenter.averageAccount(),0.1);
    }
}
