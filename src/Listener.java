import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;

public class Listener extends Thread {
	private int oldSequenceNumber = -1;
	DatagramSocket aSocket = null;
	
	private HashMap<String, Integer> maxSequenceNumber = new HashMap<String, Integer>();
//	let each sender has his own soundPlayer (make sound playable at the same time)
	private HashMap<String, SoundPlayer> soundPlayerBySender = new HashMap<String, SoundPlayer>();
	
	SendingQueue sendQueue = new SendingQueue();
	public Listener() {
//		soundPlayer = new SoundPlayer();
//		soundPlayer.start();
	}

	public void run() {
		System.out.println("Thread Listener Start");
		int PORT = 55555;
		
		try {
			aSocket = new DatagramSocket(PORT);
			aSocket.setBroadcast(true);
			while (true) {
				byte[] buffer = new byte[AudioChunk.TOTAL_LENGTH];
				DatagramPacket receivePacket = new DatagramPacket(buffer,
						buffer.length);
				aSocket.receive(receivePacket);
				System.out.println("Receive packet:" + receivePacket.getLength());
				System.out.println("Request IP address: "
						+ receivePacket.getAddress() + " Port: "
						+ receivePacket.getPort());
				try {
					AudioChunk receiveChunk = new AudioChunk(
							receivePacket.getData());
					
					String senderName = receiveChunk.senderName;
					int sequenceNumber = receiveChunk.sequenceNumber;
					
					/*
					 * New sender is found
					 * create a new soundPlayer (each sender has his own soundPlayer)
					 */
					if (!maxSequenceNumber.containsKey(senderName)) {
						maxSequenceNumber.put(senderName, new Integer(sequenceNumber));
						
						SoundPlayer newSoundPlayer = new SoundPlayer();
						newSoundPlayer.start();
						soundPlayerBySender.put(senderName, newSoundPlayer);
					} 
					
					// the packet is the old one (we've seen this before)
					if (sequenceNumber <= maxSequenceNumber.get(senderName).intValue()) {
						// drop packet
						continue;
					}
					
					// Repeat the chunk (continue the flood)
					sendQueue.add(receiveChunk);
					
					// Play the sound
					soundPlayerBySender.get(senderName).addSoundToQueue(receiveChunk);
					
//					if (hashmap.containsKey(receiveChunk.senderName)) {
						//if (receiveChunk.sequenceNumber > hashmap
					//			.get(receiveChunk.senderName)) {
//							soundPlayer.addSoundToQueue(receiveChunk);
					//		hashmap.put(receiveChunk.senderName, receiveChunk.sequenceNumber);
					//		sendQueue.add(receiveChunk);
					//	}
					//}
				} catch (AudioChunkIncorrectLengthException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException ex) {
			System.out.print("Class Listener Socket exception: ");
			System.out.print(ex.getMessage());
		} catch (IOException ex) {
			System.out.print("Class Listener IO Exception: ");
			System.out.print(ex.getMessage());
		} finally {
			if (aSocket != null) {
				aSocket.close();
			}
		}
	}

//	public SoundPlayer getSoundPlayer() {
//		return soundPlayer;
//	}
//
//	public void setSoundPlayer(SoundPlayer soundPlayer) {
//		this.soundPlayer = soundPlayer;
//	}

	public void CloseSocket() {
		aSocket.close();
	}

}
