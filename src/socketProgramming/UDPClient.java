package socketProgramming;

import java.net.*;
import java.io.*;

public class UDPClient {
    public static void main(String args[]) {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket();
            String message = "Hello, server!";
            byte[] m = message.getBytes();
            InetAddress aHost = InetAddress.getByName("localhost"); // Server hostname
            int serverPort = 6789;
            DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
            aSocket.send(request); // Send the request

            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply); // Receive the reply
            System.out.println("Reply: " + new String(reply.getData(), 0, reply.getLength()));
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null) aSocket.close();
        }
    }
}