import org.json.JSONArray;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final File file = new File("C:\\Java\\Diplom\\pcs-final-diplom1-main\\pdfs");

    public void start(int port) {
        JSONArray jsonArray = new JSONArray();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String word = null;
        try {
            word = in.readLine();
            BooleanSearchEngine booleanSearchEngine = new BooleanSearchEngine(file);
            ArrayList<PageEntry> pages = (ArrayList<PageEntry>) booleanSearchEngine.search(word);
            for (int i = 0; i < pages.size(); ++i) {
                jsonArray.put(pages.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.println(jsonArray.toString());
    }

    public void stop() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(9898);
    }

}