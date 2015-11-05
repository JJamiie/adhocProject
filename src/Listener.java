import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;

public class Listener extends Thread {
	private int oldSequenceNumber = -1;
	DatagramSocket aSocket = null;

	public static HashMap<String, Integer> maxSequenceNumber = new HashMap<String, Integer>();
	// let each sender has his own DoubleSoundPlayer (make sound playable at the
	// same
	// time)
	private HashMap<String, DoubleSoundPlayer> DoubleSoundPlayerBySender = new HashMap<String, DoubleSoundPlayer>();

	SendingQueue sendQueue = new SendingQueue();

	public Listener() {
		// DoubleSoundPlayer = new DoubleSoundPlayer();
		// DoubleSoundPlayer.start();
		sendQueue.start();
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

				// if packet is sent to itself
				String address = receivePacket.getAddress().toString();
				if (address.equals("/192.168.1." + MainActivity.IP)) {
					System.out.println("repeat IP.....................");
					continue;
				}

				try {
					AudioChunk receiveChunk = new AudioChunk(
							receivePacket.getData());

					String senderName = receiveChunk.senderName;
					int sequenceNumber = receiveChunk.sequenceNumber;

					/*
					 * New sender is found create a new DoubleSoundPlayer (each
					 * sender has his owFn DoubleSoundPlayer)
					 */

					System.out.println("Request IP address: "
							+ receivePacket.getAddress() + " Port: "
							+ receivePacket.getPort());

					System.out.println(senderName + " :" + sequenceNumber);

					if (!maxSequenceNumber.containsKey(senderName)) {
						maxSequenceNumber.put(senderName, new Integer(
								sequenceNumber));

						DoubleSoundPlayer newDoubleSoundPlayer = new DoubleSoundPlayer();

						DoubleSoundPlayerBySender.put(senderName,
								newDoubleSoundPlayer);
					}

					System.out.println("sequenceNumber" + sequenceNumber);
					System.out.println("sendername" + senderName);
					System.out.println(maxSequenceNumber.get(senderName));
					// the packet is the old one (we've seen this before)
					if (sequenceNumber <= maxSequenceNumber.get(senderName)
							.intValue()) {
						// drop packet
						System.out.println("ininininininininin");
						continue;
					}
					
					maxSequenceNumber.put(senderName, new Integer(sequenceNumber));

					// Repeat the chunk (continue the flood)
					sendQueue.add(receiveChunk);

					// Play the sound
					DoubleSoundPlayerBySender.get(senderName).playSound(
							receiveChunk);

					// if (hashmap.containsKey(receiveChunk.senderName)) {
					// if (receiveChunk.sequenceNumber > hashmap
					// .get(receiveChunk.senderName)) {
					// DoubleSoundPlayer.addSoundToQueue(receiveChunk);
					// hashmap.put(receiveChunk.senderName,
					// receiveChunk.sequenceNumber);
					// sendQueue.add(receiveChunk);
					// }
					// }
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

	// public DoubleSoundPlayer getDoubleSoundPlayer() {
	// return DoubleSoundPlayer;
	// }
	//
	// public void setDoubleSoundPlayer(DoubleSoundPlayer DoubleSoundPlayer) {
	// this.DoubleSoundPlayer = DoubleSoundPlayer;
	// }

	public void CloseSocket() {
		aSocket.close();
	}

}
