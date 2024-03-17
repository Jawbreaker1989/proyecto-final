package logic;

/**
 * La interfaz ActionsAccount define los métodos que una cuenta bancaria debe implementar para realizar operaciones como depositar, retirar y transferir fondos.
 */
public interface ActionsAccount {

        /**
         * Realiza un depósito en la cuenta bancaria.
         *
         * @param value Valor a consignar
         * @return Nuevo saldo de la cuenta
         */
        int deposit(int value);

        /**
         * Retira fondos de la cuenta bancaria.
         *
         * @param value Valor del retiro
         * @return true si el retiro fue exitoso, false de lo contrario
         */
        boolean withdraw(int value);

        /**
         * Transfiere fondos desde la cuenta actual a una cuenta destino.
         *
         * @param account Cuenta destino
         * @param value   Valor de la transferencia
         * @return Nuevo saldo de la cuenta origen
         */
        int transfer(Account account, int value);
}
