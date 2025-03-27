import client.REPLFlags;
import client.REPLOne;
import client.REPLTwo;

import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        REPLFlags flags = new REPLFlags(true, false);
        while (flags.replOne || flags.replTwo) {
            REPLOne.replMain(scanner, flags);
            if (flags.replTwo) {
                REPLTwo.replMain(scanner, flags);
            }
        }
    }
}