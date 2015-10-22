import javax.sound.sampled.*;
import java.io.*;
 
/**
 * A sample program is to demonstrate how to record sound in Java
 * author: www.codejava.net
 */
public class JavaSoundRecorder implements Runnable{
    // the line from which audio data is captured
    TargetDataLine line;
 
    /**
     * Defines an audio format
     */
    AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
        return format;
    }
    public void run() {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
 
            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            System.out.println("Start capturing...");
            while(true){
            line.start();   // start capturing
            //buffering 
            byte[] b = new byte[100];
            line.read(b, 0, 100);
            for(int i=0;i<100;i++){
            	System.out.print(b[i]);
            }
         
            }
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }
 
    /**
     * Closes the target data line to finish capturing and recording
     */
    void finish() {
        line.stop();
        line.close();
        System.out.println("Finished");
    }
 
    /**
     * Entry to run the program
     * it should be only one entry to the program and nots here, since I changed the structure of the program
     */
//    public static void main(String[] args) {
//        
//    	 final JavaSoundRecorder recorder = new JavaSoundRecorder();
//        // start recording
//        recorder.run();
//    }
}