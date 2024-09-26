package socketProgramming;

import java.net.*;
import java.io.*;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(6789); // Listen on port 6789
            byte[] buffer = new byte[1000];
            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request); // Receive request
                System.out.println("Received: " + new String(request.getData(), 0, request.getLength()));

                // Prepare and send the reply
                String replyMessage = "Reply from server";
                byte[] replyData = replyMessage.getBytes();
                DatagramPacket reply = new DatagramPacket(replyData, replyData.length,
                        request.getAddress(), request.getPort());
                aSocket.send(reply); // Send reply back to client
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null) aSocket.close();
        }
    }
}
