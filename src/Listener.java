import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Listener extends Thread{
	private SoundPlayer soundPlayer;
	private int oldSequenceNumber =-1;
	DatagramSocket aSocket = null;
	
	public Listener(){
		soundPlayer = new SoundPlayer();
	}
	
	public void run(){
		System.out.println("Thread Listener Start");
		int PORT = 55555;
		try{
			aSocket = new DatagramSocket(PORT);
			aSocket.setBroadcast(true);
			while(true){
				byte[] buffer = new byte[AudioChunk.TOTAL_LENGTH];
				DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(receivePacket);
				System.out.println("Request IP address: "+ receivePacket.getAddress() + " Port: "+ receivePacket.getPort());
				try {
					AudioChunk receiveChunk = new AudioChunk(receivePacket.getData());
					if(receiveChunk.sequenceNumber > oldSequenceNumber) {
						soundPlayer.addSoundToQueue(receiveChunk);
						oldSequenceNumber = receiveChunk.sequenceNumber;
					}
				} catch (AudioChunkIncorrectLengthException e) {
					e.printStackTrace();
				}
			}
		}catch(SocketException ex){
			System.out.print("Class Listener Socket exception: ");
			System.out.print(ex.getMessage());
		}catch(IOException ex){
			System.out.print("Class Listener IO Exception: ");
			System.out.print(ex.getMessage());
		}finally{
			if(aSocket != null){
				aSocket.close();		
			}
		}
	}
	public SoundPlayer getSoundPlayer() {
		return soundPlayer;
	}
	public void setSoundPlayer(SoundPlayer soundPlayer) {
		this.soundPlayer = soundPlayer;
	}
	public void CloseSocket() {
		aSocket.close();
	}
	
}
