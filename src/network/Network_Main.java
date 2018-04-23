package network;
import java.io.IOException;
import javax.swing.JFrame;
import gui.gameClass;
import gui.menuClass;
import network.Shooter1;


/**
 * Class starts the main thread which starts all other threads
 * @author desmondwong
 *
 */
public class Network_Main {
	public static Thread [] thread = new Thread [5];//global thread array
	public static String [] IP = new String [2];//array of IP addresses
	public static boolean[] connected = new boolean []{false, false, false ,false};
	
			
	/**
	 * The Main Thread
	 * @author s28122
	 *
	 */
	public static class Main_Thread implements Runnable{
		public static gameClass game;//links to own board
		
		/**
		 * server threads
		 */
		@Override
		public void run() {
			
			//launches the startup menu window
			menuClass menu = new menuClass();
			menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			menu.setSize(300, 225);
			menu.setVisible(true);
			
			thread[1] = new Thread (new Server_Final(6, 0));
			thread[1].start();
			System.out.println("Creating Server Socket on port " + 6);
			System.out.println("Thread 1 created");
			thread[2] = new Thread (new Server_Final(4, 1));
			thread[2].start();
			System.out.println("Creating Server Socket on port " + 4);
			System.out.println("Thread 2 created");			
		}
		/**
		 * client threads
		 * @throws IOException
		 */
		public void run2() throws IOException{

			thread[3] = new Thread (new Shooter1(IP[0], 4, 2));
			thread[4] = new Thread (new Shooter2(IP[1], 6, 3));
			game = new gameClass();
			thread[3].start();
			System.out.println("Thread 3 created");
			thread[4].start();
			System.out.println("Thread 4 created");

		}
	}

	
	/**
	 * main that initiates and joins all the threads together
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		thread [0] = new Thread (new Main_Thread());
		thread[0].start();
		System.out.println("Main Thread created");
		for (int i = 0; i< thread.length-1; i++){
			thread[i].join();//joins threads
			System.out.println("Thread " + i + " joined");
		}
		
	}
}

