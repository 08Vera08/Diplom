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
    private final File file = new File("pdfs");

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
            //Scanner is = new Scanner(System.in);
            word = in.readLine();
            BooleanSearchEngine booleanSearchEngine = new BooleanSearchEngine(file);
            ArrayList<PageEntry> pages = (ArrayList<PageEntry>) booleanSearchEngine.search(word);
            for(int i = 0; i < pages.size(); ++i){
                jsonArray.put(pages.get(i));
            }
            System.out.println(jsonArray);
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
}