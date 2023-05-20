import org.json.JSONArray;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        File file = new File("pdfs");
        try (ServerSocket serverSocket = new ServerSocket(8989)) { // стартуем сервер один(!) раз
            while (true) { // в цикле(!) принимаем подключения
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    JSONArray jsonArray = new JSONArray();
                    String word = in.readLine();
                    BooleanSearchEngine booleanSearchEngine = new BooleanSearchEngine(file);
                    ArrayList<PageEntry> pages = (ArrayList<PageEntry>) booleanSearchEngine.search(word);
                    for (int i = 0; i < pages.size(); ++i) {
                        jsonArray.put(pages.get(i));
                    }
                    out.print(jsonArray.toString());
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}