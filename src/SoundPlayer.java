import java.applet.AudioClip;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer extends Thread {

	private LinkedList<AudioChunk> playingQueue = new LinkedList<AudioChunk>();

	public void run() {
		while (true) {
			// check whether there is anything to play ? if not go sleep
			// it will be woken up by add();
			if (playingQueue.isEmpty()) {
				try {
					synchronized (this) {
						wait();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			AudioChunk goingToPlay = playingQueue.removeFirst();
			try {
				this.playSound(goingToPlay.audioBytes);
			} catch (UnsupportedAudioFileException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // invalid format
		}
	}

	public synchronized void addSoundToQueue(AudioChunk receivingChunk) {
		this.playingQueue.add(receivingChunk);
		notifyAll();
	}

	public void playSound(byte[] audioBytes) throws UnsupportedAudioFileException, IOException {
		InputStream inputStream = new ByteArrayInputStream(audioBytes);
		AudioFormat audioFormat = SoundRecorder.getAudioFormat();
		// length is the number of frames in the audioBytes
		int length = SoundRecorder.BUFFER_SIZE / audioFormat.getFrameSize();
		AudioInputStream audioInputStream = new AudioInputStream(inputStream,
				audioFormat, length);
		
//		String filePath = "/home/jamie/workspace/wirelessProject/src/1.wav";
//		File fileIn = new File(filePath);
//		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);
		
		playSound(audioInputStream);
	}

	public void playSound(AudioInputStream audioInputStream) {
//		System.out.println("playing sound...");
		try {			
			
			AudioFormat format = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioInputStream);
			clip.start();
			
			while (!clip.isRunning()) {
				Thread.sleep(1);
			}

			while (clip.isRunning()) {
				Thread.sleep(1);
			}

			clip.close();
			
//			System.out.println("playing sound ends..");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
