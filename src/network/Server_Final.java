package network;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import javax.swing.JFrame;
import gui.plyrBoard;
import gui.takeShot;
import java.io.IOException;

/**
 * Server Class that runs the server
 * @author desmondwong
 *
 */
public class Server_Final implements Runnable{
	private ServerSocket server; //Server Socket
	private Socket connection;//Client socket
	private ObjectOutputStream output;//Data to be sent by Server
	private ObjectInputStream input;	//Data received by Server
	private int port;//port number
	private static int index;//index to boolean [] connected


	
	/**
	 * Constructor that stores the port number and the index to array that checks if it is connected
	 * @param port
	 */
	public Server_Final (int portnum, int rindex) {
		port = portnum;
		index = rindex;
	}
	
	/**
	 * starts the server
	 */
	public void run() {
		try{
			server = new ServerSocket (port); //initiates server socket
			while (true){//keeps sockets listening even after connection terminated
				try{
					waitForConnection(); //waits for connection
					getStreams(); //receives input & output streams
					processConnection(); //processes Data	
				}
				catch (IOException ioException){
					System.out.println("\nServer terminating connection");//tells user that connection will terminate
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					closeConnection(); //terminates connection
				}
			}
		}catch (IOException ioException){
			ioException.printStackTrace();//show errors
		}
	}
	
	/**
	 * Waits for connection, the shows connection info
	 * @throws IOException
	 */
	private void waitForConnection() throws IOException{ 
		System.out.println("Waiting for connection on port " + server.getLocalPort());
		connection = server.accept();//socket begins accepting connections
		System.out.println("Connection received from: " + connection.getInetAddress().getHostName());
		network.Network_Main.connected[index] = true;
	}
	
	/**
	 * Gets streams to send and receive data
	 * @throws IOException
	 */
	private void getStreams() throws IOException{
		output = new ObjectOutputStream (connection.getOutputStream());//overwrites output stream with new Data
		output.flush();//sends output stream
		input = new ObjectInputStream (connection.getInputStream());//overwrites input stream with new Data
		System.out.println("\nGot I/O streams from Client: "  + connection.getInetAddress().getHostName());
	}
	
	/**
	 * Communicates with Client by taking input, processing it through Proto390, then sending it back to Client
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	private void processConnection() throws IOException, InterruptedException{

		//Displays the enemy playerboard
		plyrBoard battlegame = new plyrBoard();
		battlegame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	battlegame.setSize(600, 425);
		battlegame.setVisible(true);
		battlegame.setTitle("Host: " + connection.getInetAddress().getHostName());
		
		int []recmessage = new int [4];
		//Virtual board to track whether or not shot has been fired at location
		char[][] verifyShot = new char[10][13];
		//Loop to initiate the verification array for shots
		for (int j = 1; j < 10; j++ ) {
			for (int k = 1; k < 13; k++) {
				verifyShot[j][k] = 'N'; 
			}
		}
		do{
			try{
				recmessage = takeShot.fire(verifyShot);
				System.out.println("You Took a shot: " + Arrays.toString(recmessage)); //displays what client typed to user
				sendData(recmessage);//sends string
				recmessage = (int []) input.readObject(); //reads new message
				battlegame.shotResults(recmessage);//updates enemyboard
				
			}catch(ClassNotFoundException classNotFoundException){
				System.out.println ("<SERVER> Unknown object type received"); //exception if object type received is unknown 
			}
		}while (recmessage[3] != 1); //does above while client message are not exit commands
		Network_Main.thread[1].interrupt();//thread 1 of this player's game is halted because player is dead
		Network_Main.thread[2].interrupt();//thread 2 of this player's game is halted because player is dead
	} 

	/**
	 * Closes connection with Client
	 */
	public void closeConnection(){
		System.out.println("\nServer terminated connection\n");
		try{
			output = new ObjectOutputStream(connection.getOutputStream());
			input = new ObjectInputStream(connection.getInputStream());
			output.close();//closes output stream
			input.close();//closes input stream
			connection.close();//closes connection to socket
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}


	/**
	 * Sends message to client
	 * @param message
	 */
	private void sendData(int[] message){
		try{
			output.writeObject(message); //overwrites output stream with message
			output.flush();//sends output stream
			System.out.println("<SERVER><P" + port+ "> to <" + connection.getInetAddress().getHostName() +">"  + Arrays.toString(message)); //shows on Server side what server sent to Client
		}catch(IOException ioException){//handles error if found
			System.out.println("\nError writing object");
		}
	}
	
}
