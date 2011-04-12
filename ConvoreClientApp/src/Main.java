
import convore.api.*;

public class Main {

    //private static Client client;

    public static void main(String[] args) {
        User user = Client.initialize("<username>", "<password>");
        System.out.println(user.username);
    }
    
}
