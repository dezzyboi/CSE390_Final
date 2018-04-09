import network.ShootingClient_Final;
import network.SocketThreads;

public class Main {
	
	public static Runnable MainThread (Thread [] array, int size) {
		int[] ports = new int[]{4,6};//port numbers for each thread invoked
		for (int i = 0; i< 2; i++){
			array[i] = new Thread (new SocketThreads(ports[i]));//creates a new thread with port numbers
			array[i].start();
			System.out.println("Creating server socket on port " + ports[i]);	
		}
		
		for (int i = 2; i < 5; i++) {
			array[i]= new Thread (new ShootingClient_Final(ports[i-2]));//need to find new ports
			System.out.println("Creating server socket on port " + ports[i]);	
		}
		return null;
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread[] thread = new Thread[5];//creates new Thread array
		thread[0] = new Thread (MainThread(thread, 5));
		for (int i = 0; i< thread.length-1; i++){
			thread[i].join();//joins threads
		}
	}
	
}
