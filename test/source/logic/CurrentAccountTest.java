package logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrentAccountTest {

    @Test
    public void testWithdrawTaxExemption() {
        // Crear una cuenta corriente con saldo inicial de 1100000 pesos
        CurrentAccount account = new CurrentAccount("123456789", 1100000);

        // Realizar un retiro de 50000 pesos
        account.withdraw(50000);

        // Verificar que el saldo final sea 1049800 pesos (1100000 - 50000 - 2000)
        assertEquals(1049800, account.getResidue());
    }
    @Test
    public void testWithdrawNoTaxExemption() {
        // Crear una cuenta corriente con saldo inicial de 900000 pesos
        CurrentAccount account = new CurrentAccount("987654321", 900000);

        // Realizar un retiro de 50000 pesos
        account.withdraw(50000);

        // Verificar que el saldo final sea 850000 pesos (900000 - 50000)
        assertEquals(850000, account.getResidue());
    }

    @Test
    void transfer() {
    }
}