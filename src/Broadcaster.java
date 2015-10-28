import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Broadcaster{

    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;
	public static void main(String[] arg) throws UnknownHostException, InterruptedException {
		InetAddress addr = InetAddress.getByName(INET_ADDR);

        // Open a new DatagramSocket, which will be used to send the data.
        try (DatagramSocket serverSocket = new DatagramSocket()) {
            for (int i = 0; i < 100; i++) {
                String msg = "Sent message no " + i;

                // Create a packet that will contain the data
                // (in the form of bytes) and send it.
                DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
                        msg.getBytes().length, addr, PORT);
                serverSocket.send(msgPacket);

                System.out.println("Server sent packet with msg: " + msg);
                Thread.sleep(500);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		
	}/*
	public void broadcast(AudioChunk audio) throws InterruptedException, IOException{
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
        ssChannel.configureBlocking(true);
        int port = 8000;
        
        ssChannel.socket().bind(new InetSocketAddress(port));
        while (true) {
            SocketChannel sChannel = ssChannel.accept();
            ObjectOutputStream  oos = new 
            ObjectOutputStream(sChannel.socket().getOutputStream());
            oos.writeObject(audio);
            oos.close();

            System.out.println("Connection ended");
	    }
	}*/
}


