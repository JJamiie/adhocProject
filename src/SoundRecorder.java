import javax.sound.sampled.*;
import javax.swing.plaf.SliderUI;

import java.io.*;

/**
 * A sample program is to demonstrate how to record sound in Java author:
 * www.codejava.net
 */
public class SoundRecorder implements Runnable {
	// the line from which audio data is captured
	TargetDataLine line;
	public static final int BUFFER_SIZE = 1000;

	private boolean active = false;
	int sequenceNumber = 1;
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

	public void run() {
		while (true) {
			while(!active){
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
				System.out.println("Start capturing...");
				while (active) {
					line.start(); // start capturing
					// buffering
					byte[] b = new byte[BUFFER_SIZE];
					line.read(b, 0, BUFFER_SIZE);

					// send to others
					AudioChunk sendingChunk = new AudioChunk("ta",
							sequenceNumber, b);
					MainActivity.sendingQueue.add(sendingChunk);
				}
			} catch (LineUnavailableException ex) {
				ex.printStackTrace();
			} catch (SenderNameIncorrectLengthException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Closes the target data line to finish capturing and recording
	 */
//	void finish() {
//		line.stop();
//		line.close();
//		System.out.println("Finished");
//	}
	public void wake(){
		active= true;
		notifyAll();
	}
	public void sleep(){
		active =false;
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