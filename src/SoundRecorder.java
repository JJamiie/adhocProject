import javax.sound.sampled.*;

/**
 * A sample program is to demonstrate how to record sound in Java author:
 * www.codejava.net
 */
public class SoundRecorder extends Thread {
	// the line from which audio data is captured
	TargetDataLine line;
	private String username ;
	

	public static final int BUFFER_SIZE = 10000;
	private SendingQueue sendingQueue;

	private boolean active = false;
	private int sequenceNumber = 1;

	public SoundRecorder(SendingQueue sendingQueue) {
		this.sendingQueue = sendingQueue;
	}

	/**
	 * Defines an audio format
	 */
	public static AudioFormat getAudioFormat() {
		float sampleRate = 16000;
		int sampleSizeInBits = 8;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
				channels, signed, bigEndian);
		return format;
	}

	public synchronized void run() {
		System.out.println("SoundRecoder record");
		while (true) {
			while (!active) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
			}

			try {
				AudioFormat format = SoundRecorder.getAudioFormat();
				DataLine.Info info = new DataLine.Info(TargetDataLine.class,
						format);

				// checks if system supports the data line
				if (!AudioSystem.isLineSupported(info)) {
					System.out.println("Line not supported");
					System.exit(0);
				}

				line = (TargetDataLine) AudioSystem.getLine(info);
				line.open(format);
				// moving the line.start() out of the loop hoping this would
				// make playing smoother :D
				line.start(); // start capturing
				while (active) {
					// buffering
					byte[] b = new byte[BUFFER_SIZE];
					line.read(b, 0, BUFFER_SIZE);

					// send to others
					AudioChunk sendingChunk = new AudioChunk(username,
							sequenceNumber, b);
					sequenceNumber++;
//					System.out.println();
//					System.out.println("Recorded one chunk...");
					sendingQueue.add(sendingChunk);
				}
			} catch (LineUnavailableException ex) {
				ex.printStackTrace();
			} catch (SenderNameIncorrectLengthException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public synchronized void wake() {
		active = true;
		notifyAll();
	}

	public void sleep() {
		active = false;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Entry to run the program it should be only one entry to the program and
	 * nots here, since I changed the structure of the program
	 */
	// public static void main(String[] args) {
	//
	// final JavaSoundRecorder recorder = new JavaSoundRecorder();
	// // start recording
	// recorder.run();
	// }
}