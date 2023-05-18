import org.json.JSONArray;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Server server = new Server();
        server.start(8989);
    }
}
