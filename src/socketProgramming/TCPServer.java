package socketProgramming;

import java.net.*;
import java.io.*;

public class TCPServer {
    public static void main(String[] args) {
        int serverPort = 7896;  // Server listens on this port
        try (ServerSocket listenSocket = new ServerSocket(serverPort)) {
            System.out.println("Server is listening on port " + serverPort);
            while (true) {
                // Accept incoming client connection
                Socket clientSocket = listenSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress());
                // Handle the client connection in a separate thread
                new Thread(new Connection(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}

class Connection implements Runnable {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket clientSocket;

    public Connection(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            // Initialize the input and output streams
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Connection initialization error: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            // Echo server: read input from client and send it back
            String data = in.readUTF();
            System.out.println("Received from client: " + data);
            out.writeUTF("Echo: " + data);
        } catch (EOFException e) {
            System.err.println("Client disconnected: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        } finally {
            try {
                // Close input/output streams and socket
                if (in != null) in.close();
                if (out != null) out.close();
                if (clientSocket != null) clientSocket.close();
                System.out.println("Connection closed");
            } catch (IOException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
    }
}
