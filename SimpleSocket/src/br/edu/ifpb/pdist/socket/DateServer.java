package br.edu.ifpb.pdist.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DateServer {
	
	public static void main(String[] args) {
		try {
			ServerSocket sock = new ServerSocket(6013);
			
			/* agora escuta conexões */
			while (true) {
				Socket client = sock.accept();  // socket TCP
				
				DataOutputStream dataOutStream = new DataOutputStream(client.getOutputStream());
				
				/* grava a data no socket */
				dataOutStream.writeUTF(new Date().toString());
				
				/* fecha o socket e volta a escutar conexões */
				client.close();
			}
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
}
