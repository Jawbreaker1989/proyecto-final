package logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase Account representa una cuenta bancaria con métodos para realizar operaciones como depositar, retirar y transferir fondos.
 */
public class Account implements ActionsAccount {
    protected String number; // Número de cuenta
    protected LocalDate dateOpen; // Fecha de apertura de la cuenta
    protected int residue; // Saldo disponible en la cuenta
    private TypeAccount typeAccount; // Tipo de cuenta
    private static int minResidue = 0; // Saldo mínimo permitido en la cuenta
    private List<Transaction> transactions; // Lista de transacciones realizadas en la cuenta
    private int nextTransactionId; // Identificador para la próxima transacción

    /**
     * Constructor para inicializar una cuenta bancaria.
     *
     * @param number      Número de la cuenta
     * @param dateOpen    Fecha de apertura de la cuenta
     * @param typeAccount Tipo de cuenta
     */
    public Account(String number, LocalDate dateOpen, TypeAccount typeAccount) {
        this.number = number;
        this.dateOpen = dateOpen;
        this.typeAccount = typeAccount;
        this.residue = minResidue;
        this.transactions = new ArrayList<>();
        this.nextTransactionId = 1;
    }

    /**
     * Constructor para inicializar una cuenta bancaria con un saldo específico.
     *
     * @param number      Número de la cuenta
     * @param dateOpen    Fecha de apertura de la cuenta
     * @param residue     Saldo inicial de la cuenta
     * @param typeAccount Tipo de cuenta
     */
    public Account(String number, LocalDate dateOpen, int residue, TypeAccount typeAccount) {
        this.number = number;
        this.dateOpen = dateOpen;
        this.typeAccount = typeAccount;
        this.residue = residue;
    }

    /**
     * Obtiene el número de la cuenta.
     *
     * @return Número de la cuenta
     */
    public String getNumber() {
        return number;
    }

    /**
     * Establece el número de la cuenta.
     *
     * @param number Número de la cuenta
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Obtiene la fecha de apertura de la cuenta.
     *
     * @return Fecha de apertura de la cuenta
     */
    public LocalDate getDateOpen() {
        return dateOpen;
    }

    /**
     * Establece la fecha de apertura de la cuenta.
     *
     * @param dateOpen Fecha de apertura de la cuenta
     */
    public void setDateOpen(LocalDate dateOpen) {
        this.dateOpen = dateOpen;
    }

    /**
     * Establece el saldo de la cuenta.
     *
     * @param residue Nuevo saldo de la cuenta
     */
    public void setResidue(int residue) {
        this.residue = residue;
    }

    /**
     * Obtiene el saldo actual de la cuenta.
     *
     * @return Saldo de la cuenta
     */
    public int getResidue() {
        return residue;
    }

    /**
     * Obtiene el saldo mínimo permitido en la cuenta.
     *
     * @return Saldo mínimo permitido en la cuenta
     */
    public static int getMinResidue() {
        return minResidue;
    }

    /**
     * Establece el saldo mínimo permitido en la cuenta.
     *
     * @param minResidue Nuevo saldo mínimo permitido en la cuenta
     */
    public static void setMinResidue(int minResidue) {
        Account.minResidue = minResidue;
    }

    /**
     * Obtiene el tipo de cuenta.
     *
     * @return Tipo de cuenta
     */
    public TypeAccount getTypeAccount() {
        return typeAccount;
    }

    /**
     * Establece el tipo de cuenta.
     *
     * @param typeAccount Nuevo tipo de cuenta
     */
    public void setTypeAccount(TypeAccount typeAccount) {
        this.typeAccount = typeAccount;
    }

    /**
     * Deposita una cantidad en la cuenta.
     *
     * @param value Valor a consignar
     * @return Nuevo saldo de la cuenta
     */
    public int deposit(int value) {
        residue += value;
        addTransaction(TypeTransaction.DEPOSIT, value);
        return residue;
    }

    /**
     * Retira una cantidad de la cuenta, garantizando que el nuevo saldo no sea inferior al saldo mínimo permitido.
     *
     * @param value Valor del retiro
     * @return true si el retiro fue exitoso, false de lo contrario
     */
    public boolean withdraw(int value) {
        if (residue - value >= minResidue) {
            residue -= value;
            addTransaction(TypeTransaction.WITHDRAW, -value);
            return true;
        }
        return false;
    }

    /**
     * Transfiere fondos desde la cuenta actual a una cuenta destino.
     *
     * @param account Cuenta destino
     * @param value   Valor de la transferencia
     * @return Nuevo saldo de la cuenta origen
     */
    public int transfer(Account account, int value) {
        if (this.withdraw(value)) {
            account.deposit(value);
            addTransaction(TypeTransaction.TRANSFER, -value);
        }
        return this.residue;
    }

    /**
     * Obtiene la lista de transacciones realizadas en la cuenta.
     *
     * @return Lista de transacciones
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    // Método privado para agregar una transacción a la lista de transacciones
    private void addTransaction(TypeTransaction type, int amount) {
        Transaction transaction = new Transaction(nextTransactionId++, LocalDate.now(), type, amount);
        transactions.add(transaction);
    }
}
