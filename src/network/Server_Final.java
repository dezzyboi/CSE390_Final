package network;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

import gui.gameClass;
import gui.plyrBoard;
import gui.takeShot;

import java.io.IOException;
/**
 * 
 * @author desmondwong
 *
 */
public class Server_Final {
	private ServerSocket server;
	private int serverport;
	private Socket connection;
	private ObjectOutputStream output;
	private ObjectInputStream input;	
	private int port;

	
	
	public static class ServerSocketThreads implements Runnable{
		private int serversocketport;
		public ServerSocketThreads(int portnum){
			serversocketport = portnum;
		}
	
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Server_Final server = new Server_Final(serversocketport);
			try {
				server.runServer();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Constructor
	 * @param port
	 */
	public Server_Final (int portnum) {
		port = portnum;
	}
	
	/**
	 * run method
	 * @throws InterruptedException 
	 */
	public void runServer() throws InterruptedException {
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
	}
	
	/**
	 * Gets streams to send and receive data
	 * @throws IOException
	 */
	private void getStreams() throws IOException{
		output = new ObjectOutputStream (connection.getOutputStream());//overwrites output stream with new Data
		output.flush();//sends output stream
		input = new ObjectInputStream (connection.getInputStream());//overwrites input stream with new Data
		System.out.println("\nGot I/O streams\n");
	}
	
	/**
	 * Communicates with Client by taking input, processing it through Proto390, then sending it back to Client
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	private void processConnection() throws IOException, InterruptedException{
		String  message = "Connection successful";
		String IP = InetAddress.getLocalHost().getHostName();
		sendData (message);	//send message to Client
		//sendData(IP);

		plyrBoard battlegame = new plyrBoard();
		battlegame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	battlegame.setSize(600, 425);
		battlegame.setVisible(true);
		int []recmessage = new int [4];
		do{
			try{

				System.out.println("<CLIENT> " + message); //displays what client typed to user
				battlegame.shotResults(recmessage);
				recmessage = takeShot.fire();
				sendData(recmessage);//sends string
				recmessage = (int []) input.readObject(); //reads new message
				battlegame.shotResults(recmessage);
				input.close();//closes input stream
			}catch(ClassNotFoundException classNotFoundException){
				System.out.println ("<SERVER> Unknown object type received"); //exception if object type received is unknown 
			}
		}while (recmessage[3] != 1); //does above while client message are not exit commands
	} 

	/**
	 * Closes connection with Client
	 */
	public void closeConnection(){
		System.out.println("\nServer terminated connection\n");
		try{
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
	private void sendData(String message){
		try{
			output.writeObject(message); //overwrites output stream with message
			output.flush();//sends output stream
			System.out.println("<SERVER><P" + serverport+ ">"  + message); //shows on Server side what server sent to Client
			output.close();//closes output stream
		}catch(IOException ioException){//handles error if found
			System.out.println("\nError writing object");
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
			System.out.println("<SERVER><P" + serverport+ ">"  + message); //shows on Server side what server sent to Client
		}catch(IOException ioException){//handles error if found
			System.out.println("\nError writing object");
		}
	}
	
}
