package network;
/**
 * 
 * @author desmondwong
 *
 */
public class SocketThreads implements Runnable{
	private int serversocketport;
	/**
	 * 
	 * @param d
	 */
	public SocketThreads (int d) {
		serversocketport = d;
	}
	
	public void run() {
		Server_Final application = new Server_Final(serversocketport);
		application.runServer_Final();
	}
}
