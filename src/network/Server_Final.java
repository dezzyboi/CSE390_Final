package network;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class Server_Final {
	private ServerSocket server;
	private int serverport;
	private Socket connection;
	private ObjectOutputStream output;
	private ObjectInputStream input;	
	
	/**
	 * Constructor
	 * @param port
	 */
	public Server_Final (int port) {
		serverport = port;
	}
	
	
	/**
	 * run method
	 */
	public void runServer_Final() {
		try{
			server = new ServerSocket (serverport); //initiates server socket
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
		System.out.println("Waiting for connection on " + serverport);
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
	 */
	private void processConnection() throws IOException{
		String message = "Connection successful";
		sendData (message);	//send message to Client
		do{
			try{
				message = (String) input.readObject(); //reads new message
				System.out.println("<CLIENT> " + message); //displays what client typed to user
				//Proto390 proto = new Proto390(serverport); //instance of Proto390 class
				//String outgoing = proto.processInput(message); //creates string to be processed by Proto390 class
				//sendData(outgoing);//sends string
			}catch(ClassNotFoundException classNotFoundException){
				System.out.println ("<SERVER> Unknown object type received"); //exception if object type received is unknown 
			}
		}while (!message.equals("close")||!message.equals("exit")||!message.equals("bye")); //does above while client message are not exit commands
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
		}catch(IOException ioException){//handles error if found
			System.out.println("\nError writing object");
		}
	}
}
