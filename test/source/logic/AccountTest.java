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
        account01 = new Account("1234243",LocalDate.now(),100_000,TypeAccount.SAVINGS);
        account02 = new Account("289867", LocalDate.of(2019, Month.JANUARY,13),5_000_000,TypeAccount.CURRENT);
    }


    @Test
    void deposit() {
        assertEquals(100_000,account01.getResidue());
        assertEquals(300_000,account01.deposit(200_000));

        assertEquals(5_000_000,account02.getResidue());
        assertEquals(6_000_000,account02.deposit(1_000_000));
    }


    @Test
    void withdraw() {
        setup();

        assertTrue(account01.withdraw(50_000));
        assertEquals(50_000,account01.getResidue());
        //No se puede retirar $20.005, excede el máximo permitido($20.004)
        assertFalse(account01.withdraw(50_005));

    }

    @Test
    void transfer( ) {
        setup();
        assertEquals(80_000, account01.transfer( account02,20_000 ) );
        assertEquals(5_010_000,account02.transfer(account01,10_000));
        assertEquals(5_010_000,account02.getResidue());
        assertEquals(90_000,account01.getResidue());
    }
}