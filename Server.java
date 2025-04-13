//accept incoming client connections
//validate client handshake by checking for 12345
//process factorization in separate threads for concurrency
//record connection time, provide it later in a sorted method (getConnectedTimes)

import java.util.*;
import java.net.*;
import java.io.*;
import java.time.*;

public class Server {
    private ServerSocket server;
    private List<Long> connectionTimes;
    private static int handshake = 12345;

    public Server(int port) {
        connectionTimes = new ArrayList<>();
        server = new ServerSocket(port);
        System.out.println("Server started on port " + port);
    }

    public void serve(int numClients) {
        for (int i = 0; i < numClients; i++) {
            try {
                Socket clientSocket = server.accept();
                System.out.println("Client connected");
                ClientHandler handler = new ClientHandler(clientSocket);
                handler.start(); //blocking method
            } catch (IOException e) {
                System.err.println("Error accepting client connection: " + e.getMessage());
            }
        }
    }

    private class ClientHandler extends Thread {
        private Socket clientSocket; //stores the reference from server.accept 
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

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                //validate handshake
                //reads one line, converts to integer, checks with handshake
                int ClientHandshake = Integer.parseInt(in.readLine());
                if (ClientHandshake != handshake) { //handshake corresponds to 12345
                    out.println("Invalid handshake");
                    clientSocket.close();
                    return;
                }

                String request;

                //waits for and reads a line of text, continues as long as client sends data
                while((request = in.readLine()) != null) {
                    try {
                        long number = Long.parseLong(request); //converts string to long integer
                        int factors = countFactors(number); //counts factors
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
