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

    public void 


}
