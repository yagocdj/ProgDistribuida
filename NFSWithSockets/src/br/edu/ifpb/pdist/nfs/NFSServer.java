package br.edu.ifpb.pdist.nfs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
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

				Thread thread = new Thread(() -> {
					try {
						// a client arrived
						
						// output (server to client)
						DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
						// input (client to server)
						DataInputStream dis = new DataInputStream(socket.getInputStream());
						
						String clientMessage = dis.readUTF();

						// split command into ["command", parameters...]
						String[] splitClientMessage = clientMessage.split(" ");

						System.out.println(socket.getInetAddress() + " sent: " + clientMessage);

						// create an NFS service (an implementation of common file commands)
						NFSServiceImp nfs = new NFSServiceImp();

						switch (FileCommand.valueOfLabel(splitClientMessage[0])) {
						case FileCommand.READDIR:
							dos.writeUTF("Path '" + splitClientMessage[1] + "'\n" + nfs.readdir(splitClientMessage[1]));
							break;
						case FileCommand.RENAME:
							dos.writeUTF("Current file/dir name: " + splitClientMessage[1] + "\n" +
									"New file/dir name:" + splitClientMessage[2]);
							nfs.rename(splitClientMessage[1], splitClientMessage[2]);
							break;
						case FileCommand.CREATE:
							dos.writeUTF("File to be created: " + splitClientMessage[1]);
							nfs.create(splitClientMessage[1]);
							break;
						case FileCommand.REMOVE:
							dos.writeUTF("File to be removed: " + splitClientMessage[1]);
							nfs.remove(splitClientMessage[1]);
							break;
						// unknown command
						default:
							throw new IllegalArgumentException("Unexpected value: " + splitClientMessage[0]);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				});

				thread.start();
			}
		}
	}

}
