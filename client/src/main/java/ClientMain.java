import records.REPLFlags;
import client.REPL1;
import client.REPL2;
import facade.ServerFacade;
import records.REPLToken;

import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        REPLFlags flags = new REPLFlags(true, false, false, false);
        REPLToken token = new REPLToken("");
        ServerFacade facade = new ServerFacade("http://localhost:8080");

        while (flags.replOne || flags.replTwo) {
            REPL1.replMain(scanner, flags, token, facade);
            if (flags.replTwo) {
                REPL2.replMain(scanner, flags, facade, token);
            }
        }
    }
}