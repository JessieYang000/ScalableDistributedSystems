package socketProgramming;

import java.net.*;
import java.io.*;

public class TCPClient {
    public static void main(String args[]) {
        if (args.length != 2) {
            System.out.println("To start the client, pass in arguments: <hostname> <port>");
            return;
        }

        Socket s = null;
        try {
            int serverPort = Integer.parseInt(args[1]);
            // Create the client socket
            s = new Socket(args[0], serverPort);

            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            // For reading input from the user
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter text with length no more than 80: ");

            String message = userInput.readLine();
            // Sent the message to the server
            out.writeUTF(message);
            // Receive the formatted message from the server
            String data = in.readUTF();
            System.out.println("Received from server: " + data);
            s.close();
        } catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally {
            if (s != null) try {
                s.close();
            } catch (IOException e) {/*close failed*/}
        }
    }
}