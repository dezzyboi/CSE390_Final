import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ShootingClient_Final implements Runnable{
	private static ObjectOutputStream output; //Data to be sent by Client
	private static ObjectInputStream input; //Data received by Client
	private String message; //global variable for string data
	private static String Server = "Lab5213-14.ece.rmc.ca"; // server 1 name
	private static Socket clientSocket; //client socket
	private int portnum; //port number
	
	/**
	 * Constructor that takes the port number of user input
	 * @param port
	 * @param host
	 */
	public ShootingClient_Final(int port){
		portnum = port;
		if (portnum == -1){ //checks if valued entered is an exit command
			closeConnection(); //terminates connection if true
		}

	}
	
	/**
	 * starts Client application
	 */
	public void runShootingClient_Final() {
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
		System.out.println(("Attempting to connect to: " + Server + " at port " + portnum));
		clientSocket = new Socket (InetAddress.getByName(Server), portnum);//new socket created based on server name and port number
		System.out.println("Connected to: " + clientSocket.getInetAddress().getHostName());
	}
	
	/**
	 * Gets streams to send and receive data
	 * @throws IOException
	 */
	private void getStreams() throws IOException{
		output = new ObjectOutputStream(clientSocket.getOutputStream());//overwrites output stream with new Data
		output.flush();//sends output stream
		input = new ObjectInputStream(clientSocket.getInputStream());//overwrites input stream with new Data
		System.out.println("\nGot I/O streams\n");
	}
	
	/**
	 * Processes connection with server
	 * @throws IOException
	 */
	private void processConnection() throws IOException{
		Scanner scan = new Scanner (System.in);//Scanner to take in user input
		do{
			try{
				message = (String) input.readObject();//the last string received by server
				System.out.println("<SERVER><P" + portnum + "> " + message);
				if (portnum == 4){
					System.out.println("\n<SERVER><P" + portnum + "> This port will echo user input. Please type something or an exit command to quit.");
				}else if (portnum == 6){
					System.out.println("\n<SERVER><P" + portnum + "> This port will read and check files. Please type a command followed by its path or an exit command to quit.");
				}else{
					System.out.println("\n<SERVER><P" + portnum + "> This port will do math. Please type a command followed by the number or an exit command to quit.");
				}
				String outgoing = scan.nextLine();//scans user input and puts it into outgoing string variable
				sendData(outgoing);//sends outgoing string variable to Server

			}catch(ClassNotFoundException classNotFoundException){
				System.out.println("\nUnknown object type received\n"); //exception if object type received is unknown 
			}
		}while (!message.equals("close")||!message.equals("exit")||!message.equals("bye"));//does above while client message are not exit commands
		scan.close();
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
	 * Sends data to server
	 * @param message
	 */
	private void sendData(String message){
		try{
			output.writeObject(message);//overwrites output stream with message
			output.flush();//sends output stream
			System.out.println("\n<CLIENT> " + message); //shows on Client side what server sent to Server
		}catch(IOException ioException){//handles error if found
			System.out.println("\nError writing object\n");
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
	
	