import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class REPLTwo {
    public static void Main(Scanner scanner, REPLFlags flags) {

        if (flags.replTwo) {
            System.out.println("Login Successful ");
        }

    }

    private static List<List<String>> InitMenus() {
        List<String> helpMenu = List.of(
                "Options: ",
                "Login: (L, l, Login, login)",
                "Register: (R, r, Register, register)",
                "Help: (H, h, Help, help)",
                "Quit: (Q, q, Quit, quit)"
        );
        List<String> loginMenu = List.of(
                "Username: ",
                "Password: "
        );
        List<String> registerMenu = List.of(
                "Username: ",
                "Password: ",
                "Email: "
        );

        return List.of(helpMenu, loginMenu, registerMenu);
    }

    private static void printMenu(List<String> menu) {
        for (String line : menu) {
            System.out.println(line);
        }
    }

    private static List<String> queryMenu(List<String> menu, Scanner scanner) {
        ArrayList<String> responses = new ArrayList<>();
        for (String line : menu) {
            System.out.println(line);
            responses.add(scanner.nextLine());
        }
        System.out.println("You input: " + responses);
        return responses;
    }
}
