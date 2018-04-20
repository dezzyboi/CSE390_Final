package network;
import java.util.Scanner;

import network.ShootingClient_Final;
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
	public Network_Main() {
		IP[0] = protocol.Methods.getIP1();
		IP[1] = protocol.Methods.getIP2();
		port[0] = 6;
		port[1] = 4;
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
					thread[i] = new Thread (new Server_Final(port[i-1]));
					thread[i].start();
					System.out.println("Thread " + i + " created");
				}else{//thread 4 & 5
					Scanner scn = new Scanner (System.in);
					System.out.println("IPaddress");
					String IP = scn.nextLine();
					thread[i] = new Thread (new ShootingClient_Final (IP));
					thread[i].start();
					System.out.println("Thread " + i + " created");
					/*boolean check = false;
					while (check != true){
						if (IP[i].equalsIgnoreCase("")){
							check = false;
						}else{
							
							check = true;
						}
					}*/
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
			System.out.println("Thread 1 joined");
		}
	}
}

