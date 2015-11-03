import org.junit.Test;

public class AudioChunkTest {

	@Test
	public void beAbleToInit() throws SenderNameIncorrectLengthException {
		byte[] bytes = new byte[SoundRecorder.BUFFER_SIZE];
		AudioChunk chunk = new AudioChunk("ta", 1, bytes);
	}
	
	@Test
	public void initCorrectly() throws SenderNameIncorrectLengthException {
		byte[] bytes = new byte[SoundRecorder.BUFFER_SIZE];
		AudioChunk chunk = new AudioChunk("ta", 1, bytes);
		
		org.junit.Assert.assertEquals("sender name should be the same", "ta", chunk.senderName);
		org.junit.Assert.assertEquals("sequence nmuber should be the same", 1, chunk.sequenceNumber);
		org.junit.Assert.assertArrayEquals("audio bytes should be the same", bytes, chunk.audioBytes);
	}
	
	@Test
	public void canBeTurnedIntoBytesAndTurnedBackSafely() throws SenderNameIncorrectLengthException, AudioChunkIncorrectLengthException {
		byte[] bytes = new byte[SoundRecorder.BUFFER_SIZE];
		int sq = 512;
		AudioChunk chunk = new AudioChunk("ta", sq, bytes);
		
		byte[] audioChunkBytes = chunk.getBytes();
		AudioChunk turnedBackChunk = new AudioChunk(audioChunkBytes);
		
		org.junit.Assert.assertEquals("turned back senderName should be of the same length", chunk.senderName.length(), turnedBackChunk.senderName.length());
		org.junit.Assert.assertEquals("turned back senderName should be the same", chunk.senderName, turnedBackChunk.senderName);
		org.junit.Assert.assertEquals("sequence number should be the same", chunk.sequenceNumber, turnedBackChunk.sequenceNumber);
		org.junit.Assert.assertArrayEquals("turned back audio bytes should be the same", chunk.audioBytes, turnedBackChunk.audioBytes);
	}

}
