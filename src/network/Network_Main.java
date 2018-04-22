package network;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;

import gui.gameClass;
import gui.menuClass;
import network.ShootingClient_Final;
/**
 * 
 * @author desmondwong
 *
 */
public class Network_Main {
	public static Thread [] thread = new Thread [5];
	public static String [] IP = new String [2];

	
	/**
	 * 
	 * @param ip1
	 * @param ip2
	 * @param ip1Port
	 * @param ip2Port
	 */
	public Network_Main() {

	}
	
	/**
	 * 
	 * @author desmondwong
	 *
	 */
	public static class Main_Thread implements Runnable{
		public static gameClass game;
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			menuClass menu = new menuClass();
			menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			menu.setSize(300, 225);
			menu.setVisible(true);

			thread[1] = new Thread (new Server_Final.ServerSocketThreads(6));
			thread[1].start();
			System.out.println("Creating Server Socket on port " + 6);
			System.out.println("Thread 1 created");
			
			
			thread[2] = new Thread (new Server_Final.ServerSocketThreads(4));
			thread[2].start();
			System.out.println("Creating Server Socket on port " + 4);
			System.out.println("Thread 2 created");
			
		}
		
		public void run2() throws IOException{
			
			thread[3] = new Thread (new ShootingClient_Final.SocketThreads(IP[0], 4));
			thread[4] = new Thread (new ShootingClient_Final.SocketThreads(IP[1], 6));
			game = new gameClass();
			thread[3].start();
			System.out.println("Thread 3 created");
			thread[4].start();
			System.out.println("Thread 4 created");
			
		}
	}
			/*
			for (int i = 1; i < thread.length; i++) {
				if (i == 1 || i == 2) {//thread 2 & 3

					thread[i].start();
					System.out.println("Thread " + i + " created");
				}else{//thread 4 & 5
/*					Scanner scn = new Scanner (System.in);
					System.out.println("IPaddress");
					String IP = scn.nextLine();
					thread[i] = new Thread (new ShootingClient_Final (getIP(IP[0])));
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

	
	
	/**
	 * 
	 * @param args
	 * @throws InterruptedException90IOIOIOIOIO\890	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		thread [0] = new Thread (new Main_Thread());
		thread[0].start();
		System.out.println("Main Thread created");
		for (int i = 0; i< thread.length-1; i++){
			thread[i].join();//joins threads
			System.out.println("Thread 1 joined");
		}
		
	}
}

