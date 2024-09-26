package socketProgramming;

import java.net.*;
import java.io.*;

public class TCPServer2 {
    public static void main(String args[]) throws IOException {
        if (args.length != 1) {
            System.out.println("Please pass in the port number as argument and try again");
            return;
        }
        int port = Integer.parseInt(args[0]);
        ServerSocket listenSocket = null;

        try {
            //Create a server socket
            listenSocket = new ServerSocket(port);
            System.out.println("Server listening on port: " + port);

            // Accept a client connection
            Socket clientSocket = listenSocket.accept();

            // Create input and output streams for the client
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            // Receive the message from the client
            String message = in.readUTF();
            System.out.println("Received from client: " + message);

            // Reverse the capitalization of the characters in the message
            char[] charArray = message.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                char c = charArray[i];
                if (Character.isLowerCase(c))
                    charArray[i] = Character.toUpperCase(c);
                if (Character.isUpperCase(c))
                    charArray[i] = Character.toLowerCase(c);
            }
            // Reverse the string
            String replyMessage = new StringBuilder(new String(charArray)).reverse().toString();

            // Send a response to the client
            out.writeUTF(replyMessage);

            // Close the client and server
            clientSocket.close();
            listenSocket.close();

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        } finally {
            if (listenSocket != null) {
                try {
                    listenSocket.close();
                } catch (IOException e) {
                    System.out.println("Close failed: " + e.getMessage());
                }
            }
        }
    }
}
