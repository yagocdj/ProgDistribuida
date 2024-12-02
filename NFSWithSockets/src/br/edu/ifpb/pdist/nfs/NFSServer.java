package br.edu.ifpb.pdist.nfs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NFSServer {

	public static void main(String[] args) throws IOException {

		System.out.println("===== NFS Server =====");

		try (ServerSocket serverSocket = new ServerSocket(6013)) {
			
			while (true) {
				// listen to incoming clients
				Socket socket = serverSocket.accept();

				// start a client handler for each new client
				Thread thread = new Thread(new ClientHandler(socket));
				thread.start();
			}
		}
	}

}
