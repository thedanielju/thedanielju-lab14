//accept incoming client connections
//validate client handshake by checking for 12345
//process factorization in separate threads for concurrency
//record connection time, provide it later in a sorted method (getConnectedTimes)

import java.util.*;
import java.net.*;
import java.io.*;

public class Server {
    private ServerSocket server;
    private List<Long> connectionTimes;
    private static int handshake = 12345;

    public Server(int port) {
        connectionTimes = new ArrayList<>();
        server = new ServerSocket(port);
        System.out.println("Server started on port " + port);
    }

    public void accept(int numClients) {
        for (int i = 0; i < numClients; i++) {
            ClientHandler handler = new ClientHandler();
            handler.start();
        }
    }

    private class ClientHandler extends Thread {
        private Socket clientSocket;
        public ClientHandler(Socket socket) { //initalizes client socket, corresponding to server socket
            this.clientSocket = socket;
        }
        @Override

        public void run() { //inherited run method, modified
            try {
                long connectionTime = System.currentTimeMillis();
                synchronized (connectionTimes) { //corresponds to List connectionTimes
                    connectionTimes.add(connectionTime);
                    
                }

                BufferedReader in = new BufferedReader(new InputStream(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                //validate handshake
                int ClientHandshake = Integer.parseInt(in.readLine());
                if (ClientHandshake != handshake) { //handshake corresponds to 12345
                    out.println("Invalid handshake");
                    clientSocket.close();
                    return;
                }

                String request;
                while((request = in.readLine()) != null) {
                    try {
                        long number = Long.parseLong(request);
                        int factors = countFactors(number);
                        out.println("The number " + number + " has " + factors + " factors"); //unit testing match
                    } catch (NumberFormatException e) {
                        out.println("Invalid number format");
                    } 
                }

                clientSocket.close();

                } catch(IOException e) {
                e.printStackTrace();
            } 
        }
    }

    private int countFactors(long number) {
        int count = 0;
        for (long i = 1; i <= number; i++) {
            if (number % i == 0) {
                count++;
            }
        }
        return count;
    }

}
