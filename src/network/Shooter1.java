package network;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

import javax.swing.JFrame;


/**
 * 1st Shooter Client Class with individual input and output streams 
 * from 2nd Shooter Client Class. Class requests shot results from server
 * and updates the player's own board while sending back a "hit or miss"
 * message back to the server.
 * 
 * @author desmondwong
 *
 */
public class Shooter1 implements Runnable{
	private static ObjectOutputStream output; //Data to be sent by Client
	private static ObjectInputStream input; //Data received by Client
	private String Server; //server name
	private static Socket clientSocket; //client socket
	private int port;//server port number
	private static int index;

	
	/**
	 * Constructor that stores server address and port number
	 * @param IP server address
	 * @param portnum server port number
	 */
	public Shooter1(String IP, int portnum, int rindex){
		Server = IP;
		port = portnum;
		index = rindex;
	}
	
	/**
	 * starts Client application
	 */
	public void run() {
		try {
			connectToServer(); //attempts to connect to server
			getStreams(); //receives input & output streams
			processConnection(); //processes Data	
		}catch(EOFException eofException) {
			System.out.println("\nClient terminating connection");//tells user that connection will terminate
		}catch (IOException ioException) {
			ioException.printStackTrace();//show errors
		}finally {
			closeConnection();//terminates connection
		}
	}
	
	/**
	 * Connects to Server
	 * @throws IOException
	 */
	private void connectToServer() throws IOException{
		System.out.println(("Attempting to connect to: " + Server + " at port " + port));
		clientSocket = new Socket (InetAddress.getByName(Server), port);//new socket created based on server name and port number
		System.out.println("Connected to: " + clientSocket.getInetAddress().getHostName());
		network.Network_Main.connected[index] = true;
	}
	
	/**
	 * Stores streams to send and receive data in
	 * class variables
	 * @throws IOException
	 */
	private void getStreams() throws IOException{
		output = new ObjectOutputStream(clientSocket.getOutputStream());//overwrites output stream with new Data
		output.flush();//sends output stream
		input = new ObjectInputStream(clientSocket.getInputStream());//overwrites input stream with new Data
		System.out.println("\nGot I/O streams from Server: " + clientSocket.getInetAddress().getHostName() );
	}
	
	/**
	 * Interprets data from streams and updates the player's own board
	 * then sends and outgoing message to server indicating hit or miss
	 * @throws IOException
	 */
	private void processConnection() throws IOException{

		//Displays own game board
		network.Network_Main.Main_Thread.game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		network.Network_Main.Main_Thread.game.setSize(600, 425);
		network.Network_Main.Main_Thread.game.setVisible(true);
		
		int [] recmessage = new int [4];
		do{
			try{
				System.out.println("<" + Server + port + "> fired a shot on you");
				recmessage = (int []) input.readObject();
				recmessage = Network_Main.Main_Thread.game.shotRec(recmessage); //RENAME
				sendData(recmessage);//sends outgoing int[] variable to Server
			}catch(ClassNotFoundException classNotFoundException){
				System.out.println("\nUnknown object type received\n"); //exception if object type received is unknown 
			}
		}while (recmessage[3] != 1);//does above while own board is not dead
		Network_Main.thread[1].interrupt();//thread 1 of this player's game is halted because player is dead
		Network_Main.thread[2].interrupt();//thread 2 of this player's game is halted because player is dead
		network.Network_Main.Main_Thread.game.setTitle("You Died");
	}
	
	/**
	 * Terminates connection with Server
	 */
	private void closeConnection(){
		System.out.println("\nConnection Terminated\n");
		try{
			output.close();//closes output stream
			input.close();//closes input stream
			clientSocket.close();//closes connection to socket
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	/**
	 * method to send message to client
	 * @param message
	 */
	private void sendData(int[] message){
		try{
			output.writeObject(message); //overwrites output stream with message
			output.flush();//sends output stream
			System.out.println("<G332-08> to <" + Server + port + "> " + Arrays.toString(message)); //shows on Server side what server sent to Client
		}catch(IOException ioException){//handles error if found
			System.out.println("\nError writing object");
		}
	}
	
}
	
	