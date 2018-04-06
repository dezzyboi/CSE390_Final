import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

/**
 * 
 * @author desmondwong
 *
 */
public class Proto390 {
	private static int portnum;
	private static String input;

	//Constructor which receives a port number from the server
    public Proto390 (int port){
            portnum = port;
    }
    /**
     * 
     * @param message recieved from server from client
     * @param port is the port that the socket is following said protocol
     * @throws IOException 
     */
    public String processInput (String message) throws IOException{
            input = message;
            //Splits the input at ever space if there is more to excecute. "Batman Rocks" becomes Array[0] = Batman Array[1] = Rocks into the String array inputArray
            String[] inputArray = input.split(" ");
            //Check if the first input is close, exit or bye
            checkTerminate (inputArray);
            
            //All ports are set as methods rather than all put in the main
            if (portnum == 4) {
                    return Port4();
            }else if (portnum == 6) {
                    return Port6(inputArray);
            }else {
                    return Port8(inputArray);
            }
    }
    
    /**
     * isNum Checks to see if a string is a number or not
     * @param s string being verified
     * @return whether or not the string is a number and return if it is true or not
     */
    public static boolean isNum(String s){
    //try and convert into an integer and if so, return true
            try{
                    Integer.parseInt(s);
                    //if there is an error, catch, and return false
            }catch(NumberFormatException nfe){
                    return false;
            }
            return true;
    }
	
    /**
     * Port 4 takes any input and returns the same String
     * @param input
     * @returns same inout and mimics
     */
    public static String Port4(){
            return input;
    }
    
    /**
     * Port 6 receives the Array, inputArray, and reads the word before the space to see if it is Disp or Exists. Then the second which is the filepath name. 
     * @param Array
     * @return If file exists or not and the file contents if exists
     * @throws IOException
     */
	public static String Port6 (String [] Array) throws IOException{
		//If string equals disp returns the file contents
		if (Array[0].equals("disp") && Array.length > 1) {
			//Path is the location of the file
            //Create path object based on user input
                    Path path = Paths.get(Array[1]);
                    //Checks if the path exists and there is a file to retrieve
                    if(Files.exists(path)){
                    //Return Contents of the file using a scanner
                        Scanner sc = new Scanner(path);
                        while (sc.hasNextLine()){
                        	return(sc.nextLine());
                        }
                        sc.close();
                        return "End of File";
                    }else {
                            return path + " does not exist";
                    }
                    //Checks to see if path exists
		}else if (Array[0].equals("exists") && Array.length > 1) {
			//Create path object based on user input
            Path daway = Paths.get(Array[1]);
            if(Files.exists(daway)){
                    return daway + " exists";
            }else{
                    return daway + " does not exist";
            }
		}else{
			return "Request unrecognized";
		}
	}
	
	 /**
     * Port 8 runs the user input through calculations if right input is provided
     * @param Array which is the inputArray from split
     * @returns either cube or sqrt of user input 
     */
    public static String Port8 (String[] Array){
          //Check to see if second input is a number
		if ( Array.length <= 1||isNum(Array[1]) == false){
			return "Invalid value to calculate";
		}else {
			//if equals cube, grab second number in the array and cube it
            if (Array[0].equals("cube")){
            //changed a string into an integer
                    int triple = Integer.parseInt(Array[1]);
                    //cubes it
                    int ans = (int) Math.pow(triple, 3);
                    //Back into String from integer
                    String the = Integer.toString(ans);
                    return the;
                    //if sqrt is first in the array then return the square root of input
            }else if (Array[0].equals("sqrt")){
            //into integer from string
                    int square = Integer.parseInt(Array[1]);
                    //to make sure input is not a negative, cannot take sqrt of a negative number
                    if (square < 0){
                            return "Cannot calculate the square root of a negative number";
                    }else{
                    //square root function from math io
                    //Converts backk to srting
                            return Double.toString(Math.sqrt(square));
                    }
            }else{
            //if first in the array is not cube or sqrt return
                    return "Request Unrecognized";
			}
		}
	}
		
    /**
     * Checks if the first word of an Array for an exit command, otherwise returns
     * @param Array which is the inputArray from split
     * @throws IOException if an exit command is present
     */
	public static void checkTerminate (String [] Array) throws IOException{
		if (Array[0].equals("exit") || Array[0].equals("bye") || Array[0].equals("close")){
			throw new IOException();
		}else {
			return;
		}
	}
}

