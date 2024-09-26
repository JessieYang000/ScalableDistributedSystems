package socketProgramming;

import java.net.*;
import java.io.*;

public class MulticastPeer {
    public static void main(String[] args) {
        MulticastSocket s = null;
        try {
            InetAddress group = InetAddress.getByName(args[1]);
            s = new MulticastSocket(6789);

            // Get the network interface to use
            NetworkInterface netIf = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());

            // Use the new joinGroup method with SocketAddress and NetworkInterface
            SocketAddress groupAddress = new InetSocketAddress(group, 6789);
            s.joinGroup(groupAddress, netIf);

            byte[] m = args[0].getBytes();
            DatagramPacket messageOut = new DatagramPacket(m, m.length, groupAddress);
            s.send(messageOut);

            byte[] buffer = new byte[1000];
            for (int i = 0; i < 3; i++) {  // Get messages from others in the group
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                s.receive(messageIn);
                System.out.println("Received: " + new String(messageIn.getData()));
            }

            // Use the new leaveGroup method with SocketAddress and NetworkInterface
            s.leaveGroup(groupAddress, netIf);
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (s != null) s.close();
        }
    }
}
