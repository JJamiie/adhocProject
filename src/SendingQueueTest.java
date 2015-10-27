import static org.junit.Assert.*;

import org.junit.Test;

public class SendingQueueTest {

	@Test
	public void beAbleToInit() {
		SendingQueue queue = new SendingQueue();
		queue.start();
	}
	
	@Test
	public void chunkCanBeAdded() throws SenderNameIncorrectLengthException {
		SendingQueue queue = new SendingQueue();		
		AudioChunk sendingChunk = new AudioChunk("ta", 1, null);
		queue.add(sendingChunk);
		
		org.junit.Assert.assertEquals("sending queue is not right", queue.sendingQueue.size(), 1);
	}

}
