import client.REPLFlags;
import client.REPLOne;
import client.REPLTwo;
import client.ServerFacade;

import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        REPLFlags flags = new REPLFlags(true, false);
        ServerFacade facade = new ServerFacade("http://localhost:8080");
        while (flags.replOne || flags.replTwo) {
            REPLOne.replMain(scanner, flags, facade);
            if (flags.replTwo) {
                REPLTwo.replMain(scanner, flags, facade);
            }
        }
    }
}