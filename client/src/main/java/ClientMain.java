import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        REPLFlags flags = new REPLFlags(true, false);

        while (flags.replOne || flags.replTwo) {
            REPLOne.Main(scanner, flags);
            if (flags.replTwo) {
                REPLTwo.Main(scanner, flags);
            }
        }
    }
}