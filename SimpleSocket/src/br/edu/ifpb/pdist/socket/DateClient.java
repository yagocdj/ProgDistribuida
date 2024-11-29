package br.edu.ifpb.pdist.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class DateClient {
	
	public static void main(String[] args) {
		try {
			/* estabelece conexão com o socket do servidor */
			Socket sock = new Socket("127.0.0.1", 6013);
			
			InputStream inputStream = sock.getInputStream();
			DataInputStream dataInStream = new DataInputStream(inputStream);
			
			/* lê a data no socket */
			String line;
			while ((line = dataInStream.readUTF()) != null)
				System.out.println(line);
			
			/* fecha a conexão com o socket */
			sock.close();
			
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
}
