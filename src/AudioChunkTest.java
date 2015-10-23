import static org.junit.Assert.*;

import org.junit.Test;

public class AudioChunkTest {

	@Test
	public void beAbleToInit() {
		byte[] bytes = new byte[100];
		AudioChunk chunk = new AudioChunk("ta", 1, bytes);
	}

}
