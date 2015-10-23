import java.io.Serializable;

public class AudioChunk implements Serializable{
	
	public String senderName;
	public int sequenceNumber;
	public byte[] audioBytes;

	public AudioChunk(String senderName, int sequenceNumber, byte[] audioBytes) {
		this.senderName = senderName;
		this.sequenceNumber = sequenceNumber;
		this.audioBytes = audioBytes;
	}
}
