import chess.*;
import server.Server;

public class Main {
    public static void main(String[] args) {
        Server s = new Server();
        s.run(8080);
        System.out.println("♕ 240 Chess Server: Running ");
    }
}