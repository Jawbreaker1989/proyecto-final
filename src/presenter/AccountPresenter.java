package presenter;

import errors.HandlingErrors;
import logic.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase AccountPresenter actúa como una capa de presentación que interactúa con la lógica de manipulación de cuentas (HandlingAccount).
 * Proporciona métodos para buscar, crear, depositar, retirar, transferir y obtener información sobre las cuentas.
 */
public class AccountPresenter {
    // Relación: La clase AccountPresenter utiliza una instancia de HandlingAccount para interactuar con las cuentas.
    private HandlingAccount handlingAccount;

    /**
     * Constructor que inicializa una instancia de HandlingAccount.
     */
    public AccountPresenter() {
        handlingAccount = new HandlingAccount();
    }

    /**
     * Método que busca una cuenta por su número.
     * @param number Número de cuenta a buscar.
     * @return Matriz de String con la información de las cuentas (Número, Fecha de Apertura, Saldo, Tipo de Cuenta).
     * @throws HandlingErrors Si la cuenta no existe.
     */
    public String[][] findAccount(String number) throws HandlingErrors {
        Account account = handlingAccount.findAccount(number);
        if(account != null){
            return getAccounts(); // Llama al método existente para obtener la información de todas las cuentas
        } else {
            throw new HandlingErrors("La cuenta no existe.");
        }
    }

    /**
     * Método que permite crear una nueva cuenta con saldo inicial y asegura que la fecha de apertura no sea posterior a la fecha actual.
     * @param number Número de cuenta.
     * @param dateOpen Fecha de apertura en formato "yyyy/MM/dd".
     * @param residue Saldo inicial de la cuenta.
     * @param typeAccount Tipo de cuenta (corriente o ahorro).
     * @return Verdadero si se pudo crear la cuenta con éxito, falso si no.
     * @throws HandlingErrors Si la fecha de apertura no es válida o si el saldo inicial es insuficiente para una cuenta de ahorros.
     */
    public boolean saveAccount(String number, String dateOpen, int residue, TypeAccount typeAccount) throws HandlingErrors {
        LocalDate dateAux = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        try {
            dateAux = LocalDate.parse(dateOpen, formatter);
        } catch (DateTimeParseException e) {
            throw new HandlingErrors("Fecha No Válida");
        }

        if (typeAccount == TypeAccount.SAVINGS && residue < 50000) {
            throw new HandlingErrors("El saldo inicial para una cuenta de ahorros debe ser al menos de $50,000");
        }

        Account account;
        if (typeAccount == TypeAccount.CURRENT) {
            account = new CurrentAccount(number, residue);
        } else {
            account = new SavingsAccount(number, residue);
        }

        account.setDateOpen(dateAux); // Asignar la fecha de apertura
        return handlingAccount.saveAccount(account);
    }

    /**
     * Método que elimina una cuenta por su número.
     * @param number Número de la cuenta a eliminar.
     * @return La cuenta eliminada si se encontró y eliminó con éxito.
     * @throws HandlingErrors Si la cuenta no existe.
     */
    public Account deleteAccount(String number) throws HandlingErrors {
        Account account = handlingAccount.findAccount(number);
        if (account != null) {
            if (handlingAccount.deleteAccount(account)) {
                return account;
            }
        }
        throw new HandlingErrors("La cuenta no existe.");
    }


    /**
     * Método que permite consignar a una cuenta.
     * @param number Número de cuenta.
     * @param value Valor a consignar.
     * @return Nuevo saldo de la cuenta después de la consignación.
     * @throws Exception Si la cuenta no existe.
     */
    public int deposit(String number, int value) throws Exception {
        Account account = handlingAccount.findAccount(number);
        if(account != null){
            handlingAccount.deposit(account, value);
            return account.getResidue();
        }
        throw new Exception("La Cuenta no Existe");
    }

    /**
     * Método que permite retirar de una cuenta.
     * @param number Número de la cuenta.
     * @param value Valor a retirar.
     * @return Verdadero si se pudo realizar el retiro, falso cuando no hay saldo suficiente.
     * @throws Exception Si la cuenta no existe.
     */
    public boolean withdraw(String number, int value)throws Exception{

        Account account = handlingAccount.findAccount( number );
        if( account != null ){

            return handlingAccount.withdraw( account, value );
        }

        throw new Exception("Cuenta Inexistente");
    }

    /**
     * Método para realizar transferencias entre cuentas.
     * @param source Cuenta origen.
     * @param target Cuenta destino.
     * @param value Valor a transferir.
     * @return Verdadero si se pudo realizar la transferencia, falso cuando la cuenta origen no tiene saldo suficiente.
     * @throws Exception Si la cuenta origen o destino no existen.
     */
    public boolean transfer(String source, String target, int value) throws Exception {
        Account sourceAccount = handlingAccount.findAccount(source);
        Account targetAccount = handlingAccount.findAccount(target);
        if(sourceAccount != null && targetAccount != null){
            return handlingAccount.transfer(sourceAccount, targetAccount, value);
        }
        throw new Exception("Revisar Cuenta origen o Destino");
    }

    /**
     * Método que retorna las cuentas en forma de matriz de Strings.
     * @return Matriz de String con la información de las cuentas (Número, Fecha de Apertura, Saldo, Tipo de Cuenta).
     */
    public String[][] getAccounts(){
        ArrayList<Account> accounts = handlingAccount.getAccounts();
        String[][] result = new String[accounts.size()][4];
        int row = 0;
        for(Account account : accounts){
            result[row][0] = account.getNumber();
            result[row][1] = account.getDateOpen().toString();
            result[row][2] = Integer.toString(account.getResidue());
            int typeAccount = account.getTypeAccount() == TypeAccount.CURRENT ? 0 : 1;
            result[row++][3] = Integer.toString(typeAccount);
        }
        return result;
    }

    /**
     * Método que calcula el promedio de los saldos de todas las cuentas.
     * @return Promedio de los saldos de las cuentas.
     */
    public double averageAccount(){
        double average = 0.0;
        ArrayList<Account> accounts = handlingAccount.getAccounts();
        for(Account account : accounts){
            average += account.getResidue();
        }
        return average / accounts.size();
    }

    /**
     * Método que retorna las transacciones de una cuenta en forma de matriz de cadenas.
     * @param account Cuenta para la cual se desean obtener las transacciones.
     * @return Matriz de cadenas con la información de las transacciones de la cuenta.
     */
    public String[][] getTransactions(Account account) {
        List<Transaction> transactions = account.getTransactions();
        String[][] result = new String[transactions.size()][4];
        int row = 0;
        for(Transaction transaction : transactions) {
            result[row][0] = Integer.toString(transaction.getId());
            result[row][1] = transaction.getDate().toString();
            result[row][2] = transaction.getType().toString();
            result[row][3] = Integer.toString(transaction.getAmount());
            row++;
        }
        return result;
    }
}
