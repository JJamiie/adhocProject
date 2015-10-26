import java.applet.AudioClip;
import java.io.File;
import java.util.LinkedList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class SoundPlayer extends Thread {
	
	public LinkedList<AudioChunk> playingQueue;
	
	public synchronized void run() {
		while (true) {
//			check whether there is anything to send ? if not go sleep
//			it will be woken up by add();
			if (playingQueue.isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			AudioChunk goingToPlay = playingQueue.removeFirst();
//			@TODO
//			convert goingToPlay to audioInputStream
//			this.playSound(audioInputStream);
		}
	}
	
	public synchronized void addSoundToQueue(AudioChunk receivingChunk) {
		this.playingQueue.add(receivingChunk);
		notifyAll();
	}
	
	public void playSound(AudioInputStream audioInputStream) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			
			while (!clip.isRunning()) {
				Thread.sleep(10);
			}
			    
			while (clip.isRunning()) {
				Thread.sleep(10);
			}
			    
			clip.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

//	public void playSound(String filePath) {
//		System.out.println("playing sound from file:" + filePath);
//		try {
//			File fileIn = new File(filePath);
//			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);
//
//			this.playSound(audioInputStream);
//
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//
//		System.out.println("playing sound ends ...");
//	}
}
