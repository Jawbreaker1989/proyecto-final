package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private Account account01;
    private Account account02;

    @BeforeEach
    void setup(){
        account01 = new Account("123987", LocalDate.of(2021, Month.JULY,17),TypeAccount.SAVINGS);
        account02 = new Account("289867", LocalDate.of(2019, Month.JANUARY,13),50_000,TypeAccount.CURRENT);
    }

    @Test
    void testStatic(){
        assertEquals(10000, Account.getMinResidue());
    }

    @Test
    void deposit() {
        assertEquals(10000,account01.getResidue());
        assertEquals(30000,account01.deposit(20000));

        assertEquals(50_000,account02.getResidue());
        assertEquals(60_000,account02.deposit(10_000));
    }


    @Test
    void withdraw() {
        setup();
        //No se puede retirar de la cuenta porque el saldo = saldo mínimo
        assertFalse( account01.withdraw(100000));
        //El saldo está en $50.000
        assertEquals(10_000,account01.getResidue());
        //Si se consignan $30.000, el nuevo saldo es $40.000
        assertEquals(40_000,account01.deposit(30_000));
        //Se puede hacer retiros en account01, el saldo es $40.000
        //Retirar $20.000, entonces el nuevo saldo es $20.000
        assertTrue(account01.withdraw(20_000));
        assertEquals(20_000,account01.getResidue());
        //No se puede retirar $20.001, excede el máximo permitido($20.000)
        assertFalse(account01.withdraw(20_001));
    }

    @Test
    void transfer( ) {
        setup();
        assertEquals(10_000, account01.transfer( account02,10000 ) );
        assertEquals(40_000,account02.transfer(account01,10_000));
        assertEquals(40_000,account02.getResidue());
        assertEquals(20_000,account01.getResidue());
    }
}