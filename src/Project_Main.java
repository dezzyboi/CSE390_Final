import network.Network_Main;
import gui.GUI_Main;


public class Project_Main {
	public static String IP1;
	public static String IP2;
	public static int Port1;
	public static int Port2;
	
	public static void main(String[] args) throws InterruptedException {
		Network_Main start = new Network_Main(IP1, IP2, Port1, Port2);
		start.main(args);
	}
}
