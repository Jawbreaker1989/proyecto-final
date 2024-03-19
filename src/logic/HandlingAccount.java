package logic;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase HandlingAccount proporciona métodos para gestionar cuentas bancarias.
 */
public class HandlingAccount {
    private ArrayList<Account> accounts;

    /**
     * Constructor que inicializa la lista de cuentas.
     */
    public HandlingAccount() {
        this.accounts = new ArrayList<>();
    }

    /**
     * Método que permite buscar una cuenta por número.
     * @param number Número de la cuenta a buscar.
     * @return La cuenta si existe, si no, retorna null.
     */
    public Account findAccount(String number) {
        for (Account account : accounts) {
            if (account.getNumber().equals(number)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Método que permite adicionar una nueva cuenta al arreglo de cuentas.
     * @param account Instancia de la cuenta nueva a agregar.
     * @return Verdadero si la cuenta se agrega con éxito, falso si ya existe una cuenta con el mismo número.
     */
    public boolean saveAccount(Account account) {
        if (findAccount(account.getNumber()) == null) {
            accounts.add(account);
            return true;
        }
        return false;
    }

    /**
     * Método que permite borrar una cuenta del arreglo de cuentas.
     * @param account Cuenta a ser borrada del arreglo de cuentas.
     * @return Verdadero si la cuenta se borra con éxito, falso si la cuenta no existe en el arreglo.
     */
    public boolean deleteAccount(Account account) {
        return accounts.remove(account);
    }

    /**
     * Método que permite consignar a una cuenta.
     * Precondición: la cuenta existe.
     * @param account La cuenta a la cual se la va a consignar.
     * @param value Valor a consignar.
     * @return El nuevo saldo de la cuenta después de la consignación.
     */
    public int deposit(Account account, int value) {
        return account.deposit(value);
    }

    /**
     * Método que permite retirar de la cuenta.
     * Precondición: la cuenta existe.
     * @param account Cuenta de la cual se va a realizar el retiro.
     * @param value Valor a retirar.
     * @return Verdadero si el retiro se realiza con éxito, falso si no es posible realizar el retiro.
     */
    public boolean withdraw(Account account, int value) {
        return account.withdraw(value);
    }

    /**
     * Método que transfiere de una cuenta origen a otra destino.
     * Precondición: Las cuentas origen y destino existen.
     * @param source Cuenta origen.
     * @param target Cuenta destino.
     * @param value Valor a transferir.
     * @return Verdadero si se puede realizar la transferencia, falso si no.
     */
    public boolean transfer(Account source, Account target, int value) {
        if (source.getResidue() >= value) { // Verificar si la cuenta origen tiene suficientes fondos
            source.withdraw(value); // Retirar el monto de la cuenta origen
            target.deposit(value); // Depositar el monto en la cuenta destino
            return true; // Devolver true para indicar que la transferencia fue exitosa
        }
        return false; // Devolver false si la cuenta origen no tiene suficientes fondos
    }


    /**
     * Método que permite consignar intereses a una cuenta de ahorros.
     * Precondición: la cuenta existe y es una cuenta de ahorros.
     * @param account Cuenta a la que se le consignarán los intereses.
     * @return El nuevo saldo de la cuenta después de abonar los intereses.
     */
    public int payRate(Account account) {
        if (account instanceof SavingsAccount savingsAccount) {
            int oldResidue = savingsAccount.getResidue();
            savingsAccount.payRate();
            return savingsAccount.getResidue() - oldResidue;
        }
        return 0; // No se abonan intereses para cuentas que no son de ahorros.
    }

    /**
     * Método que calcula el promedio del saldo de las cuentas.
     * @return Promedio del saldo de las cuentas.
     */
    public double averageAccount() {
        double sum = 0.0;
        for (Account account : accounts) {
            sum += account.getResidue();
        }
        return sum / accounts.size();
    }

    /**
     * Método que retorna un clon de la colección de cuentas.
     * @return Un clon de la colección de cuentas.
     */
    public ArrayList<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    /**
     * Método que calcula la suma del saldo de todas las cuentas.
     * @return La suma del saldo de todas las cuentas.
     */
    public int getSumAccounts() {
        int sum = 0;
        for (Account account : accounts) {
            sum += account.getResidue();
        }
        return sum;
    }

    /**
     * Método que obtiene las transacciones de una cuenta.
     * @param account Cuenta de la cual se obtienen las transacciones.
     * @return Lista de transacciones de la cuenta.
     */
    public List<Transaction> getTransactions(Account account) {
        return account.getTransactions();
    }
}
