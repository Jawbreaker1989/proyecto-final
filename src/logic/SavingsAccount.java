package logic;

import java.time.LocalDate;

/**
 * La clase SavingsAccount representa una cuenta de ahorros, que es un tipo de cuenta bancaria que ofrece intereses sobre el saldo.
 * Las cuentas de ahorros tienen una tasa de interés y un saldo mínimo requerido.
 */
public class SavingsAccount extends Account {
    private static final double INTEREST_RATE = 0.05; // Tasa de interés de ejemplo
    private static final int MINIMUM_BALANCE = 50_000; // Saldo mínimo de ejemplo

    /**
     * Constructor para inicializar una cuenta de ahorros.
     *
     * @param number   Número de la cuenta
     * @param dateOpen Fecha de apertura de la cuenta
     */
    public SavingsAccount(String number, LocalDate dateOpen) {
        super(number, dateOpen, TypeAccount.SAVINGS);
    }

    /**
     * Constructor para inicializar una cuenta de ahorros con un saldo específico.
     *
     * @param number   Número de la cuenta
     * @param dateOpen Fecha de apertura de la cuenta
     * @param residue  Saldo inicial de la cuenta
     */
    public SavingsAccount(String number, LocalDate dateOpen, int residue) {
        super(number, dateOpen, residue, TypeAccount.SAVINGS);
    }

    /**
     * Sobrescribe el método de depósito para pagar la tasa de interés antes de agregar el valor al saldo.
     *
     * @param value Valor a consignar
     * @return Nuevo saldo de la cuenta
     */
    @Override
    public int deposit(int value) {
        payRate();
        setResidue(getResidue() + value);
        return getResidue();
    }

    /**
     * Sobrescribe el método de retiro para garantizar que el saldo no caiga por debajo del saldo mínimo requerido.
     *
     * @param value Valor del retiro
     * @return true si el retiro fue exitoso, false de lo contrario
     */
    @Override
    public boolean withdraw(int value) {
        if (getResidue() - value >= MINIMUM_BALANCE) {
            setResidue(getResidue() - value);
            return true;
        }
        return false;
    }

    // Método privado para calcular y pagar la tasa de interés
    public void payRate() {
        double interest = getResidue() * INTEREST_RATE;
        setResidue((int) (getResidue() + interest));
    }
}
