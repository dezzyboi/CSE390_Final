package start;
import java.util.Arrays;
import java.util.Random;

public class gameClass {
	//2D array to generate virtual board
	private static char[][] board = new char[12][9];

	//Array to store x and y coordinates of a shot
	static int shot[] = new int[2];


	
	static void setBoard() {
		//Set all the values of the board to -1
		//Use -2, -1, 0 and 1 to set four conditions: water, ship (undetected), missed and hit respectively
		for (int i = 0; i < 12; i++) {
			for (int k = 0; k < 9; k++) {
				board[i][k] = '~';
			}
		}
	}
	
	static void placeShips(int[] ships) {

		
		Random random = new Random();
		for (int i = 0; i < ships.length; i++ ) {
			char[][] verify = new char[12][9];
			for (int j = 0; j < 12; j++ ) {
				for (int k = 0; k < 9; k++) {
					verify[j][k] = 'N'; 
				}
			}
			boolean placed = false;
			while (!placed) {
				int r = random.nextInt(12);
				int c = random.nextInt(9);
				if(verify[r][c] == 'N') {
					verify[r][c] = 'Y';
					if (board[r][c] == '~') {
						int orientation = random.nextInt(8);
						//0 = N, 1 = NE, 2 = E, 3 = SE, 4 = S, 5 = SW, 6 = W, 7 = NW
						if(testPlace(ships[i], r, c, orientation)) {
							place(ships[i], r, c, orientation);
							placed = true;
						}
					}
				}
			}
		}
	
}
	private static void place(int size, int r, int c, int orientation) {
		int i = r;
		int k = c;
		if(orientation == 0) {
			for (int j = r; j >= r - (size - 1); j--){
				board[j][c] = 'S';
			}
		}
		if(orientation == 1) {
			while (i >= r - (size - 1) && k <= c + (size - 1)) {
				board[i][k] = 'S';
				i--;
				k++;
			}
//			i = r;
//			k = c;
//			break;
		}
		if(orientation == 2) {
			for (int j = c; j <= c + (size -1); j++) {
				board[r][j] = 'S';
			}
		//	break;
		}
		if(orientation == 3) {
			while (i <= c + (size - 1) && k <= r + (size - 1)) {
				board[i][k] = 'S';
				i++;
				k++;
			}
//			i = r;
//			k = c;
//			break;
		}
		if(orientation == 4) {
			for (int j = r; j <= r + (size -1); j++) {
				board[j][c] = 'S';
			}
			//break;
		}
		if(orientation == 5) {
			while (k >= c - (size - 1) && i <= r + (size - 1)) {
				board[i][k] = 'S';
				i++;
				k--;
			}
//			i = r;
//			k = c;
//			break;
		}
		if(orientation == 6) {
			for (int j = c; j >= c + (size -1); j--) {
				board[r][j] = 'S';
			}
			//break;
		}
		else {
			while (k >= c - (size - 1) && i >= r - (size - 1)) {
				board[i][k] = 'S';
				i--;
				k--;
			}
			i = r;
			k = c;
			}
			
	}
		

	private static boolean testPlace(int size, int r, int c, int orientation) {
		boolean fits = true;

		if(orientation == 0) {
			if (r - (size - 1) < 0) {
				fits = false;
			}else {
				for (int i = r; i >= r - (size - 1) && fits; i-- ) {
					fits = fits & (board[i][c] == '~');
				}
			}
		}
		if(orientation == 1) {
			if (r - (size - 1) < 0 || c + (size - 1) >= 9) {
				fits = false;
			}else {
				int i = r;
				int k = c;
				while (i >= r - (size - 1) && k <= c + (size - 1) && fits) {
					fits = fits & (board[i][k] == '~');
					i--;
					k++;
				}
			}
		}
		if(orientation == 2) {
			if (c + (size - 1) >= 9) {
				fits =  false;
			}else {
				for (int i = c; i <= c + (size -1) && fits; i++) {
					fits = fits & (board[r][i] == '~');
				}
			}
		}
		if(orientation == 3) {
			if (r + (size - 1) >= 12 || c + (size - 1) >= 9){
				fits = false;
			}else {
				int i = c;
				int k = r;
				while (i <= c + (size - 1) && k <= r + (size - 1) && fits) {
					fits = fits & (board[k][i] == '~');
					i++;
					k++;
				}
			}
		}
		if(orientation == 4) {
			if (r + (size - 1) >= 12) {
				fits = false;
			}else {
				for (int i = r; i <= r + (size -1) && fits; i++) {
					fits = fits & (board[i][c] == '~');
				}
			}
		}
		if(orientation == 5) {
			if (r + (size - 1) >= 12 || c - (size - 1) < 0){
				fits = false;
			}else {
				int i = c;
				int k = r;
				while (i >= c - (size - 1) && k <= r + (size - 1) && fits) {
					fits = fits & (board[k][i] == '~');
					i--;
					k++;
				}
			}
		}
		if(orientation == 6) {
			if (c - (size - 1) < 0) {
				fits = false;
			}else {
				for (int i = c; i >= c + (size -1) && fits; i--) {
					fits = fits & (board[r][i] == '~');
				}
			}
		}
		else {
			if (c - (size - 1) < 0 || r - (size - 1) < 0) {
				fits = false;
			}else {
				int i = c;
				int k = r;
				while (i >= c - (size - 1) && k >= r - (size - 1) && fits) {
					fits = fits & (board[k][i] == '~');
					i--;
					k--;
				}	
			}
		}
		return fits;
		
	}
	
	public void printBoard() {
		for (int i = 0; i < 12; i++)
			System.out.println(Arrays.toString(board[i]));
	}

}

