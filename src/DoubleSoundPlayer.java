
public class DoubleSoundPlayer {
	private static final int PLAYER_COUNT = 3;
	
	private SoundPlayer[] players = new SoundPlayer[PLAYER_COUNT];
	
	public DoubleSoundPlayer() {
		for (int i = 0; i < PLAYER_COUNT; ++i) {
			players[i] = new SoundPlayer();
			players[i].start();
		}
	}
	
	public void playSound(AudioChunk audioChunk) {
		players[audioChunk.sequenceNumber % PLAYER_COUNT].addSoundToQueue(audioChunk);
	}
}
