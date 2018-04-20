
public class boardMain {
	public static void main() {
		//Array of linked lists to cycle through coordinates of every ship
		int[] ships = new int[5];
		//Ship sizes
		int BB = 5;
		int CV = 4;
		int CA = 3;
		int CL = 3;
		int DD = 2;
		ships[4] = DD;
		ships[3] = CL;
		ships[2] = CA;
		ships[1] = CV;
		ships[0] = BB;
		
		gameClass battlegame = new gameClass();
		battlegame.setBoard();
		battlegame.placeShips(ships);
		battlegame.printBoard();
		
		
	}
}
