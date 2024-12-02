package br.edu.ifpb.pdist.nfs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class NFSClient {
	public static void main(String[] args) throws Exception {
		System.out.println("===== NFS Client =====");
		System.out.println("TYPE " + Option.CLOSE.key + " TO CLOSE .");

		Socket socket = new Socket("localhost", 6013);
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		
		System.out.print("$ ");
		Scanner input = new Scanner(System.in);

		// command example: readdir /tmp/someDir
		String command = input.nextLine().toLowerCase();
		
		String serverAnswer;
		
		while (true) {
			// send command
			dos.writeUTF(command);
			// answer
			serverAnswer = dis.readUTF();
			System.out.println(serverAnswer);
			
			if (serverAnswer.equals(Option.CLOSE.key)) break;
			
			// read next input
			System.out.print("$ ");
			command = input.nextLine();
		}
		
		System.out.println("END");
		
		input.close();
		socket.close();
	}

	public enum Option {
		CLOSE("x");
		
		public final String key;
		
		private Option(String key) {
			this.key = key;
		}
	}
	
}

