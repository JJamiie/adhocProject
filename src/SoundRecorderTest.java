import static org.junit.Assert.*;

import org.junit.Test;

public class SoundRecorderTest {

	@Test
	public void RecorderAndSendingQueueIntegration() throws InterruptedException {
		SendingQueue sendingQueue = new SendingQueue();
		SoundRecorder soundRecorder = new SoundRecorder(sendingQueue);
		
		soundRecorder.start();
		sendingQueue.start();
		
		System.out.println("waking up...");
		soundRecorder.wake();
		Thread.sleep(5000);
		soundRecorder.sleep();
		System.out.println("sleeping...");
	}

}
