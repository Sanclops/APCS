import java.util.Scanner;

public class OpenFile {
	
	public OpenFile() {

	}

	public static Scanner OpenToRead(String fileName) {
		Scanner scan = new Scanner(new File(fileName));
		return scan;
	}

}