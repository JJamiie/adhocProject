import java.io.Serializable;

public class AudioChunk {
	public static final int SENDER_NAME_LENGTH = 50;
	public static final int TOTAL_LENGTH = SENDER_NAME_LENGTH + 4 + SoundRecorder.BUFFER_SIZE;
	
	public char[] senderName;
	public int sequenceNumber;
	public byte[] audioBytes;

	public AudioChunk(String senderName, int sequenceNumber, byte[] audioBytes) throws SenderNameLengthExceededException {
		if (senderName.length() > SENDER_NAME_LENGTH) {
			throw new SenderNameLengthExceededException();
		}
		
		this.senderName = senderName.toCharArray();
		this.sequenceNumber = sequenceNumber;
		this.audioBytes = audioBytes;
	}
	
	public AudioChunk(byte[] audioChunkBytes) throws AudioChunkIncorrectLengthException {
		if (audioChunkBytes.length != TOTAL_LENGTH) {
			throw new AudioChunkIncorrectLengthException();
		}
		
//		assign the senderName
		this.senderName = new char[SENDER_NAME_LENGTH];
		System.arraycopy(audioChunkBytes, 0, this.senderName, 0, SENDER_NAME_LENGTH);

//		assign the sequenceNumber
		byte[] sequenceNumberBytes = new byte[4];
		System.arraycopy(audioChunkBytes, SENDER_NAME_LENGTH, sequenceNumberBytes, 0, 4);
		this.sequenceNumber = AudioChunk.bytesToInt(sequenceNumberBytes);
		
//		assign the audioBytes
		this.audioBytes = new byte[SoundRecorder.BUFFER_SIZE];
		System.arraycopy(audioChunkBytes, SENDER_NAME_LENGTH + 4, this.audioBytes, 0, SoundRecorder.BUFFER_SIZE);
	}
	
	public static byte[] intToBytes(int intValue) {
		byte[] result = new byte[4];

		result[0] = (byte) ((intValue & 0xFF000000) >> 24);
		result[1] = (byte) ((intValue & 0x00FF0000) >> 16);
		result[2] = (byte) ((intValue & 0x0000FF00) >> 8);
		result[3] = (byte) ((intValue & 0x000000FF) >> 0);

		return result;
	}
	
	public static int bytesToInt(byte[] intBytes) {
		int result = 0;
		
		result |= intBytes[0] << 24;
		result |= intBytes[1] << 16;
		result |= intBytes[2] << 8;
		result |= intBytes[3] << 0;
		
		return result;
	}
	
	public byte[] getBytes() {
//		the returning bytes will be : 50 bytes for senderName | 4 bytes for sequenceNumber | 1000 bytes for audioBytes
		byte[] sequenceNumberBytes = AudioChunk.intToBytes(sequenceNumber);
		byte[] audioChunkBytes = new byte[TOTAL_LENGTH];
		
		System.arraycopy(senderName, 0, audioChunkBytes, 0, senderName.length);
		System.arraycopy(sequenceNumberBytes, 0, audioChunkBytes, senderName.length, 4);
		System.arraycopy(audioBytes, 0, audioChunkBytes, senderName.length + 4, SoundRecorder.BUFFER_SIZE);
		
		return audioChunkBytes;
	}

}
