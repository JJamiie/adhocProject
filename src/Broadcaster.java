import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import javax.swing.JLabel;

public class Broadcaster{ 	

    final static String INET_ADDR = "192.168.1.255";
    final static int PORT = 55555;
	public void broadcast(AudioChunk audio) throws InterruptedException, IOException{
	    InetAddress addr = InetAddress.getByName(INET_ADDR);
        // Open a new DatagramSocket, which will be used to send the data.
        try (DatagramSocket serverSocket = new DatagramSocket()) {
                DatagramPacket msgPacket = new DatagramPacket(audio.getBytes(),
                        audio.getBytes().length, addr, PORT);
                serverSocket.send(msgPacket);

                Thread.sleep(500);
            }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        
	    
	}
}


