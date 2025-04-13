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
                Socket client = server.accept(); //runs accept method on client socket
                //code for client handling
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
