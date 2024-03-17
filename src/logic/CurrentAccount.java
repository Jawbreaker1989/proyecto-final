package logic;

import java.time.LocalDate;

/**
 * La clase CurrentAccount representa una cuenta corriente, que es un tipo de cuenta bancaria con algunas características específicas.
 * Las cuentas corrientes pueden estar exentas de impuestos y no tienen un límite de sobregiro.
 */
public class CurrentAccount extends Account {
    private boolean taxExempt; // Indica si la cuenta está exenta de impuestos

    /**
     * Constructor para inicializar una cuenta corriente.
     *
     * @param number      Número de la cuenta
     * @param dateOpen    Fecha de apertura de la cuenta
     * @param taxExempt   Indica si la cuenta está exenta de impuestos
     */
    public CurrentAccount(String number, LocalDate dateOpen, boolean taxExempt) {
        super(number, dateOpen, TypeAccount.CURRENT);
        this.taxExempt = taxExempt;
    }

    /**
     * Constructor para inicializar una cuenta corriente con un saldo específico.
     *
     * @param number      Número de la cuenta
     * @param dateOpen    Fecha de apertura de la cuenta
     * @param residue     Saldo inicial de la cuenta
     * @param taxExempt   Indica si la cuenta está exenta de impuestos
     */
    public CurrentAccount(String number, LocalDate dateOpen, int residue, boolean taxExempt) {
        super(number, dateOpen, residue, TypeAccount.CURRENT);
        this.taxExempt = taxExempt;
    }

    /**
     * Verifica si la cuenta está exenta de impuestos.
     *
     * @return true si la cuenta está exenta de impuestos, false de lo contrario
     */
    public boolean isTaxExempt() {
        return taxExempt;
    }

    /**
     * Establece si la cuenta está exenta de impuestos.
     *
     * @param taxExempt true si la cuenta está exenta de impuestos, false de lo contrario
     */
    public void setTaxExempt(boolean taxExempt) {
        this.taxExempt = taxExempt;
    }

    /**
     * Sobrescribe el método de retiro para aplicar impuestos si la cuenta no está exenta de impuestos.
     * No se aplica un límite de sobregiro para las cuentas corrientes.
     *
     * @param value Valor del retiro
     * @return true si el retiro fue exitoso, false de lo contrario
     */
    @Override
    public boolean withdraw(int value) {
        if (!taxExempt) {
            // Aplicar impuestos si no está exento de impuestos
            value += (value / 1000) * 4;
        }

        // No es necesario verificar el límite de sobregiro para la cuenta corriente

        setResidue(getResidue() - value);
        return true;
    }
}
