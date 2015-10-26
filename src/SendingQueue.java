import java.util.LinkedList;

public class SendingQueue extends Thread {
	public LinkedList<AudioChunk> sendingQueue;
	
	public SendingQueue() {
		this.sendingQueue = new LinkedList<AudioChunk>();
	}
	
	
	public synchronized void add(AudioChunk sendingChunk) {
		this.sendingQueue.add(sendingChunk);
		notifyAll();
	}
	
	/*
	 * this function will call the ad-hoc wireless sender to send a certain chunk to the network
	 * and shall not be called from the outside
	 */
	private void send(AudioChunk sendingChunk) {
		System.out.print("this has been called");
	}
	
	public synchronized void run () {
		while (true) {
//			check whether there is anything to send ? if not go sleep
//			it will be woken up by add();
			if (sendingQueue.isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			AudioChunk goingToSend = sendingQueue.removeFirst();
			this.send(goingToSend);
		}
	}

}
