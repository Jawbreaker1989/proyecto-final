package view;

import java.time.LocalDate;
import java.util.Scanner;

import logic.Account;
import logic.CurrentAccount;
import logic.HandlingAccount;
import logic.SavingsAccount;
import logic.Transaction;

public class Menu {
    private HandlingAccount handlingAccount;

    public Menu() {
        this.handlingAccount = new HandlingAccount();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("=== MENU ===");
            System.out.println("1. Crear cuenta de ahorros");
            System.out.println("2. Crear cuenta corriente");
            System.out.println("3. Realizar transacciones");
            System.out.println("4. Consultar una cuenta");
            System.out.println("5. Consultar transacciones de una cuenta");
            System.out.println("6. Visualizar información de las cuentas");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createSavingsAccount();
                    break;
                case 2:
                    createCurrentAccount();
                    break;
                case 3:
                    performTransactions();
                    break;
                case 4:
                    viewAccount();
                    break;
                case 5:
                    viewTransactions();
                    break;
                case 6:
                    viewAllAccounts();
                    break;
                case 7:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione nuevamente.");
            }
        } while (choice != 7);
    }

    private void createSavingsAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número de la cuenta: ");
        String number = scanner.nextLine();
        System.out.print("Ingrese la fecha de apertura (AAAA-MM-DD): ");
        LocalDate dateOpen = LocalDate.parse(scanner.nextLine());
        SavingsAccount account = new SavingsAccount(number, dateOpen);
        handlingAccount.saveAccount(account);
        System.out.println("Cuenta de ahorros creada con éxito.");
    }

    private void createCurrentAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número de la cuenta: ");
        String number = scanner.nextLine();
        System.out.print("Ingrese la fecha de apertura (AAAA-MM-DD): ");
        LocalDate dateOpen = LocalDate.parse(scanner.nextLine());
        System.out.print("¿La cuenta está exenta de impuestos? (true/false): ");
        boolean taxExempt = scanner.nextBoolean();
        CurrentAccount account = new CurrentAccount(number, dateOpen, taxExempt);
        handlingAccount.saveAccount(account);
        System.out.println("Cuenta corriente creada con éxito.");
    }

    private void performTransactions() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número de la cuenta de origen: ");
        String sourceNumber = scanner.nextLine();
        Account sourceAccount = handlingAccount.findAccount(sourceNumber);
        if (sourceAccount == null) {
            System.out.println("La cuenta de origen no existe.");
            return;
        }

        System.out.print("Ingrese el tipo de transacción (depositar/retirar/transferir): ");
        String transactionType = scanner.nextLine();

        switch (transactionType.toLowerCase()) {
            case "depositar":
                System.out.print("Ingrese el monto a depositar: ");
                int depositAmount = scanner.nextInt();
                handlingAccount.deposit(sourceAccount, depositAmount);
                System.out.println("Depósito realizado con éxito.");
                break;
            case "retirar":
                System.out.print("Ingrese el monto a retirar: ");
                int withdrawAmount = scanner.nextInt();
                handlingAccount.withdraw(sourceAccount, withdrawAmount);
                System.out.println("Retiro realizado con éxito.");
                break;
            case "transferir":
                System.out.print("Ingrese el número de la cuenta de destino: ");
                String targetNumber = scanner.next();
                Account targetAccount = handlingAccount.findAccount(targetNumber);
                if (targetAccount == null) {
                    System.out.println("La cuenta de destino no existe.");
                    return;
                }
                System.out.print("Ingrese el monto a transferir: ");
                int transferAmount = scanner.nextInt();
                handlingAccount.transfer(sourceAccount, targetAccount, transferAmount);
                System.out.println("Transferencia realizada con éxito.");
                break;
            default:
                System.out.println("Tipo de transacción inválido.");
        }
    }

    private void viewAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número de la cuenta: ");
        String number = scanner.nextLine();
        Account account = handlingAccount.findAccount(number);
        if (account == null) {
            System.out.println("La cuenta no existe.");
        } else {
            System.out.println("Información de la cuenta:");
            System.out.println("Número de cuenta: " + account.getNumber());
            System.out.println("Fecha de apertura: " + account.getDateOpen());
            System.out.println("Tipo de cuenta: " + account.getTypeAccount());
            System.out.println("Saldo disponible: " + account.getResidue());
        }
    }

    private void viewTransactions() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número de la cuenta: ");
        String number = scanner.nextLine();
        Account account = handlingAccount.findAccount(number);
        if (account == null) {
            System.out.println("La cuenta no existe.");
        } else {
            System.out.println("Transacciones de la cuenta:");
            for (Transaction transaction : handlingAccount.getTransactions(account)) {
                System.out.println("ID: " + transaction.getId());
                System.out.println("Fecha: " + transaction.getDate());
                System.out.println("Tipo: " + transaction.getType());
                System.out.println("Monto: " + transaction.getAmount());
            }
        }
    }

    private void viewAllAccounts() {
        System.out.println("Información de todas las cuentas:");
        for (Account account : handlingAccount.getAccounts()) {
            System.out.println("Número de cuenta: " + account.getNumber());
            System.out.println("Fecha de apertura: " + account.getDateOpen());
            System.out.println("Tipo de cuenta: " + account.getTypeAccount());
            System.out.println("Saldo disponible: " + account.getResidue());
            System.out.println("-------------------------");
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start();
    }
}
