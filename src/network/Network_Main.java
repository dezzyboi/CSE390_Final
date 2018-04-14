package network;
import network.ShootingClient_Final;
import network.SocketThreads;
/**
 * 
 * @author desmondwong
 *
 */
public class Network_Main {
	public static Thread [] thread = new Thread [5];
	final static String [] IP = new String [2];
	final static int port [] = new int [2];
	
	/**
	 * 
	 * @param ip1
	 * @param ip2
	 * @param ip1Port
	 * @param ip2Port
	 */
	public Network_Main(String ip1, String ip2, int ip1Port, int ip2Port) {
		IP[0] = ip1;
		IP[1] = ip2;
		port[0] = ip1Port;
		port[1] = ip2Port;
	}
	/**
	 * 
	 * @author desmondwong
	 *
	 */
	private static class Main_Thread implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int i = 1; i < thread.length; i++) {
				if (i == 1 || i == 2) {//thread 2 & 3
					thread[i] = new Thread (new SocketThreads(port[i]));
					thread[i].start();
					System.out.println("Thread " + i + " created");
				}else{//thread 4 & 5
					thread[3] = new Thread (new ShootingClient_Final (IP[i]));
					thread[i].start();
					System.out.println("Thread 3 created");
				}
			}
		}
	}
	/**
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		thread [0] = new Thread (new Main_Thread());
		thread[0].start();
		System.out.println("Main Thread created");
		for (int i = 0; i< thread.length-1; i++){
			thread[i].join();//joins threads
		}
	}
}

