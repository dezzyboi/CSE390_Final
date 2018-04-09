package network;

public class SocketThreads implements Runnable{
	private int serversocketport;
	
	public SocketThreads (double d) {
		serversocketport = (int) d;
	}
	
	public void run() {
		Server_Final application = new Server_Final(serversocketport);
		application.runServer_Final();
	}
}
