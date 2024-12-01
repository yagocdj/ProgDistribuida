package br.edu.ifpb.pdist.nfs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class NFSClient {
	public static void main(String[] args) throws Exception {
		System.out.println("===== NFS Client =====");

		Socket socket = new Socket("localhost", 6013);
		
		System.out.print("$ ");
		Scanner input = new Scanner(System.in);

		// command example: readdir /tmp/someDir
		String command = input.nextLine();
		
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		// send command
		dos.writeUTF(command);
		
		// answer
		System.out.println(dis.readUTF());
		
		input.close();
		socket.close();
			
	}
}
