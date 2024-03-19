package logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SavingsAccountTest {

    private SavingsAccount savingsAccount;

    @BeforeEach
    public void setUp() {
        savingsAccount = new SavingsAccount("123456789", 60000); // Crear una nueva cuenta de ahorros con un saldo inicial de 60,000
    }

    @Test
    public void testConstructor() {
        assertEquals("123456789", savingsAccount.getNumber()); // Comprobar que el número de cuenta es el esperado
        assertEquals(60000, savingsAccount.getResidue()); // Comprobar que el saldo inicial es el esperado
    }

    @Test
    public void testDeposit() {
        savingsAccount.deposit(10000); // Depositar 10,000
        assertEquals(60000 + 10000+3000, savingsAccount.getResidue()); // Comprobar que el saldo se incrementó correctamente
    }

    @Test
    public void testWithdrawSufficientFunds() {
        assertTrue(savingsAccount.withdraw(2000)); // Retirar 20,000 (saldo suficiente)
        assertEquals(60000 - 2000, savingsAccount.getResidue()); // Comprobar que el saldo se redujo correctamente
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        assertFalse(savingsAccount.withdraw(70000)); // Intentar retirar 70,000 (saldo insuficiente)
        assertEquals(60000, savingsAccount.getResidue()); // Comprobar que el saldo no cambió
    }

    @Test
    public void testWithdrawBelowMinimumBalance() {
        assertFalse(savingsAccount.withdraw(59000)); // Intentar retirar 59,000 (saldo insuficiente para mantener el saldo mínimo)
        assertEquals(60000, savingsAccount.getResidue()); // Comprobar que el saldo no cambió
    }
}
