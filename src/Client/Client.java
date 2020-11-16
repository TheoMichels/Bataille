package Client;

//import java.nio.Buffer;
//import java.io.InputStream;
import java.io.BufferedReader;
import java.net.Socket;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;


public class Client {

	public static void main(String[] args) {
		
		try {
			Socket s = new Socket("127.0.0.1", 1500);
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			new ThreadListening(s).start();
			System.out.println("Connexion réussie!");
			Scanner sc = new Scanner(System.in);
			String message="";
			while(message!="quit") {
			message=sc.nextLine();
			out.println(message);
			}
		}
		
		catch(Exception e) {
			e.getMessage();
		}
	}
}