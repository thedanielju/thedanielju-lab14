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

    public void handshake() throws IOException {
        int handshake = 12345;
        out.println(handshake);
    }

    public String request(String number) throws IOException {
        out.println(number);
        String response = in.readLine();
        return response;
    }

    public void disconnect() throws IOException {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
            System.out.println("Disconnected from server");
        } catch (IOException e) {
            System.err.println("Error while disconnecting: " + e.getMessage());
            throw e;
        }
    }

    public Socket getSocket() {
        return socket;
    }
}