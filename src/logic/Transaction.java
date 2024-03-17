package logic;

import java.time.LocalDate;

/**
 * La clase Transaction representa una transacción realizada en una cuenta bancaria.
 */
public class Transaction {

    private int id; // Identificador de la transacción
    private LocalDate date; // Fecha de la transacción
    private TypeTransaction type; // Tipo de transacción
    private int amount; // Monto de la transacción

    /**
     * Constructor para inicializar una transacción.
     *
     * @param id     Identificador de la transacción
     * @param date   Fecha de la transacción
     * @param type   Tipo de transacción
     * @param amount Monto de la transacción
     */
    public Transaction(int id, LocalDate date, TypeTransaction type, int amount) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

    /**
     * Obtiene el identificador de la transacción.
     *
     * @return Identificador de la transacción
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la fecha de la transacción.
     *
     * @return Fecha de la transacción
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Obtiene el tipo de transacción.
     *
     * @return Tipo de transacción
     */
    public TypeTransaction getType() {
        return type;
    }

    /**
     * Obtiene el monto de la transacción.
     *
     * @return Monto de la transacción
     */
    public int getAmount() {
        return amount;
    }
}
