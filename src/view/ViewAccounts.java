package view;

import errors.HandlingErrors;
import presenter.AccountPresenter;

import java.util.Scanner;

/**
 * La clase ViewAccounts es responsable de interactuar con el usuario y presentar las operaciones relacionadas con cuentas bancarias.
 */
public class ViewAccounts {
    private final AccountPresenter presenter;
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Constructor de la clase ViewAccounts que inicializa el presentador de cuentas.
     */
    public ViewAccounts() {
        this.presenter = new AccountPresenter();
    }

    public static void main(String[] args) {
        ViewAccounts view = new ViewAccounts();
        view.showMenu();
    }

    public void showMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nBienvenido al sistema de gestión de cuentas bancarias");
            System.out.println("1. Crear cuenta");
            System.out.println("2. Realizar transacciones");
            System.out.println("3. Consultar una cuenta");
            System.out.println("4. Consultar las transacciones de una cuenta");
            System.out.println("5. Visualizar información de las cuentas");
            System.out.println("6. Salir");

            System.out.print("Seleccione una opción: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    performTransactions();
                    break;
                case 3:
                    findAccount();
                    break;
                case 4:
                    viewAccountTransactions();
                    break;
                case 5:
                    viewAccountInformation();
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
            }
        }
    }

    private void createAccount() {
        System.out.println("\nCreación de nueva cuenta:");
        // Implementa la lógica para crear una cuenta aquí
    }

    private void performTransactions() {
        System.out.println("\nRealización de transacciones:");
        // Implementa la lógica para realizar transacciones aquí
    }

    private void findAccount() {
        System.out.println("\nConsulta de cuenta:");
        // Implementa la lógica para consultar una cuenta aquí
    }

    private void viewAccountTransactions() {
        System.out.println("\nConsulta de transacciones de una cuenta:");
        // Implementa la lógica para consultar las transacciones de una cuenta aquí
    }

    private void viewAccountInformation() {
        System.out.println("\nVisualización de información de las cuentas:");
        // Implementa la lógica para visualizar información de las cuentas aquí
    }
}
