import java.util.Scanner;
import java.io.*;
public class OpenFile {
	
	public OpenFile() {

	}

	public static Scanner openToRead(String fileName) {
		Scanner scan = new Scanner(System.in);
		try {
			scan = new Scanner(new File(fileName));
			return scan;
		} catch(Exception e) {
			System.exit(1);
		}
		return scan;
	}

}