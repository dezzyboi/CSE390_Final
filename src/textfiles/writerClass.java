package textfiles;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
/**
 * Class to construct a text file and to write in it
 * @author jonathangilbert
 */
public class writerClass {
	
	//Path to desired file (will create the file if it doesn't exist)
	private String path;
	
	//Whether to delete all 
	private boolean append = false;
	
	/**
	 * Method to change whether to overwrite or not and set the path
	 * 
	 */
	public writerClass(String pathCible, boolean appendReq) {
		
		//Set the path
		path = pathCible;
		
		//Set whehter to overwrite
		append = appendReq;
	}
	/**
	 * Method to write desired text to file
	 * 
	 */
	public void writeToFile(String text) throws IOException{
		
		//Construct a file writer
		FileWriter write = new FileWriter(path, append);
		
		//Construct a printer to insert text
		PrintWriter print = new PrintWriter(write);
		print.printf("%s" + "%n", text);
		print.close();
	}
	/**
	 * Method to delete the contents of a text file
	 *
	 */
	public void delete() throws IOException {
		
		//Construct a printer and print nothing
		PrintWriter print = new PrintWriter(path);
		print.close();
	}
}
