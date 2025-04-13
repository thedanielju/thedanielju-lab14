//establish server connection
//send initial handshake
//send factorization message
//recieve and display result


import java.io.*;
import java.net.*;

public class Client{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
}