import java.io.IOException;
import java.util.ArrayList;

public class SendingQueue extends Thread {

	public ArrayList<AudioChunk> sendingQueue = new ArrayList<AudioChunk>();
	Broadcaster broadcaster = new Broadcaster();

	public synchronized void add(AudioChunk sendingChunk) {
		System.out.println("sendingQueue is being added a job...");
		sendingQueue.add(sendingChunk);
		notifyAll();
	}

	/*
	 * this function will call the ad-hoc wireless sender to send a certain
	 * chunk to the network and shall not be called from the outside
	 */
	private void send(AudioChunk sendingChunk) throws InterruptedException,
			IOException {
		System.out.println("calling broadcaster to send a chuk");
		broadcaster.broadcast(sendingChunk);
	}

	public synchronized void run() {
		
		while (true) {
//			System.out.println("Sending Queue Thread:"
//					+ Thread.currentThread().getName());
			// check whether there is anything to send ? if not go sleep
			// it will be woken up by add();
			while (sendingQueue.isEmpty()) {
				try {
					wait();

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			AudioChunk goingToSend = sendingQueue.get(0);
			sendingQueue.remove(0);
			try {
				this.send(goingToSend);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
