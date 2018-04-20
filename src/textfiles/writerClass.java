package textfiles;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
public class writerClass {
	private String path;
	private boolean append = false;
	public writerClass(String pathCible) {
		path = pathCible;
	}
	public writerClass(String pathCible, boolean appendReq) {
		path = pathCible;
		append = appendReq;
	}
	
	public void writeToFile(String text) throws IOException{
		FileWriter write = new FileWriter(path, append);
		PrintWriter print = new PrintWriter(write);
		print.printf("%s" + "%n", text);
		print.close();
	}
	
	public void delete() throws IOException {
		PrintWriter print = new PrintWriter(path);
		print.close();
	}
}
