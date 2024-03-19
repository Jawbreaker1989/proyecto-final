package logic;

import java.time.LocalDate;

public class CurrentAccount extends Account {
    private boolean taxExempt; // Indica si la cuenta está exenta de impuestos

    /**
     * Constructor para inicializar una cuenta corriente con un saldo específico.
     *
     * @param number      Número de la cuenta
     * @param residue     Saldo inicial de la cuenta
     */
    public CurrentAccount(String number, int residue) {
        super(number, LocalDate.now(), residue, TypeAccount.CURRENT);
        this.taxExempt = residue <= 1000000;
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
     * Sobrescribe el método de retiro para aplicar impuestos si la cuenta no está exenta de impuestos.
     * No se aplica un límite de sobregiro para las cuentas corrientes.
     *
     * @param value Valor del retiro
     * @return true si el retiro fue exitoso, false de lo contrario
     */
    @Override
    public boolean withdraw(int value) {
        if (!taxExempt) {
            // Verificar si el saldo después del retiro sigue siendo menor o igual a 1000000
            if (getResidue() - value <= 1000000) {
                // Verificar si el retiro es superior a 999999
                if (value > 999999) {
                    taxExempt = true; // La cuenta se vuelve exenta
                }
            }
        }

        // Aplicar impuestos si no está exenta de impuestos
        if (!taxExempt) {
            value += (value / 1000) * 4;
        }

        // Actualizar el saldo
        setResidue(getResidue() - value);
        return true;
    }

    /**
     * Sobrescribe el método de transferencia para aplicar la lógica de exención de impuestos.
     *
     * @param account Cuenta destino
     * @param value   Valor de la transferencia
     * @return Nuevo saldo de la cuenta origen después de la transferencia
     */
    @Override
    public int transfer(Account account, int value) {
        // Verificar si la transferencia es superior a 999999
        if (value > 999999) {
            taxExempt = true; // La cuenta se vuelve exenta
        }

        // Realizar la transferencia
        super.transfer(account, value);

        // Si la transferencia fue exitosa, actualizar el estado de exención de impuestos
        if (getResidue() <= 1000000 && value > 999999) {
            taxExempt = true; // La cuenta se vuelve exenta
        }

        // Devolver el nuevo saldo de la cuenta origen
        return getResidue();
    }
}
