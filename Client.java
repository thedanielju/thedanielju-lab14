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

    public boolean sendHandshake() throws IOException {
        final int handshake = 12345;
        out.println(handshake);
        String response = in.readLine();
        if (response != null && response.contains("couldn't handshake")) {
            System.out.println("Handshake failed: " + response);
            return false;
        }
        System.out.println("Connected to server successfully");
        return true;
    }

    public String factorize(long number) throws IOException {
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
}