package view;

import errors.HandlingErrors;
import presenter.AccountPresenter;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * La clase ViewAccounts es responsable de interactuar con el usuario y presentar las operaciones relacionadas con cuentas bancarias.
 */
public class ViewAccounts {
    private final AccountPresenter presenter;

    /**
     * Constructor de la clase ViewAccounts que inicializa el presentador de cuentas.
     */
    public ViewAccounts() {
        this.presenter = new AccountPresenter();
    }

    /**
     * Método principal de la clase ViewAccounts que inicia la aplicación.
     * @param args Argumentos de la línea de comandos (no se utilizan en este caso).
     */
    public static void main(String[] args) {
        new ViewAccounts().mainMenu();
    }

    /**
     * Muestra el menú principal de la aplicación y realiza una operación basada en la entrada del usuario.
     */
    private void mainMenu() {
        // Crear una cuenta bancaria utilizando la entrada del usuario
        createAccountFromUserInput();
    }

    /**
     * Crea una nueva cuenta bancaria utilizando la entrada del usuario.
     */
    private void createAccountFromUserInput() {
        Scanner scanner = new Scanner(System.in);

        // Solicitar al usuario que ingrese la fecha
        System.out.println("Ingrese la fecha (formato yyyy/MM/dd):");
        String date = scanner.nextLine();

        // Expresión regular para validar el formato de la fecha (yyyy/MM/dd)
        Pattern pattern = Pattern.compile("^\\d{4}\\/\\d{2}\\/\\d{2}$");

        // Matcher para realizar la coincidencia con la expresión regular
        Matcher matcher = pattern.matcher(date);

        // Si el formato de fecha es válido, continuar con la creación de la cuenta
        if (matcher.find()) {
            try {
                // Solicitar al usuario que ingrese el número de cuenta
                System.out.println("Ingrese el número de cuenta:");
                String accountNumber = scanner.nextLine();

                // Intentar guardar una cuenta de ahorros con la fecha y el número de cuenta proporcionados
                System.out.println(presenter.saveAccount(accountNumber, date, AccountPresenter.SAVINGS));
            } catch (HandlingErrors e) {
                // Capturar y mostrar cualquier error relacionado con la gestión de errores
                System.out.println(e.getMessage());
            }
        } else {
            // Si el formato de fecha no es válido, mostrar un mensaje de error
            System.out.println("Formato de fecha no válido.");
        }
        
        scanner.close();
    }
}
