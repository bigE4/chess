import records.REPLFlags;
import client.REPLOne;
import client.REPLTwo;
import client.ServerFacade;
import records.REPLToken;

import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        REPLFlags flags = new REPLFlags(true, false);
        REPLToken token = new REPLToken("");
        ServerFacade facade = new ServerFacade("http://localhost:8080");

        while (flags.replOne || flags.replTwo) {
            REPLOne.replMain(scanner, flags, token, facade);
            if (flags.replTwo) {
                REPLTwo.replMain(scanner, flags, token, facade);
            }
        }
    }
}