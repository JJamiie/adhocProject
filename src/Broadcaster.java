import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Broadcaster{ 	
	
    final static String INET_ADDR = "192.168.1.255";
    final static int PORT = 55555;
	public void broadcast(AudioChunk audio) throws InterruptedException, IOException{
	    System.out.println("Thread Broadcaster Start");
		InetAddress addr = InetAddress.getByName(INET_ADDR);
        // Open a new DatagramSocket, which will be used to send the data.
        try (DatagramSocket serverSocket = new DatagramSocket()) {
                DatagramPacket msgPacket = new DatagramPacket(audio.getBytes(),
                        audio.getBytes().length, addr, PORT);
                serverSocket.send(msgPacket);
            }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        
	    
	}
}


