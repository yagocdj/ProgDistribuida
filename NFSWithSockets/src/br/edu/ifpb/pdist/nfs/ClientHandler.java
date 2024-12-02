package br.edu.ifpb.pdist.nfs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
	
	private Socket socket;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private boolean isToReadClientMsg = true;
	
	public ClientHandler(Socket socket) throws IOException {
		this.socket = socket;
		
		// input (client to server)
		this.inputStream = new DataInputStream(socket.getInputStream());
		// output (server to client)
		this.outputStream = new DataOutputStream(socket.getOutputStream());
			
	}

	@Override
	public void run() {
		while (isToReadClientMsg) {
			try {
				// a client arrived
				String clientMessage = inputStream.readUTF().toLowerCase();
				
				// split command into ["command", parameters...]
				String[] splitClientMessage = clientMessage.split(" ");
				
				System.out.println(socket.getInetAddress() + ":" + socket.getPort() + 
						" sent: " + clientMessage);
				
				// create an NFS service (an implementation of common file commands)
				NFSServiceImp nfs = new NFSServiceImp();
				
				switch (FileCommand.valueOfLabel(splitClientMessage[0])) {
				case FileCommand.READDIR:
					outputStream.writeUTF("Path '" + splitClientMessage[1] + "'\n" + nfs.readdir(splitClientMessage[1]));
					break;
				case FileCommand.RENAME:
					outputStream.writeUTF("Current file/dir name: " + splitClientMessage[1] + "\n" +
							"New file/dir name:" + splitClientMessage[2]);
					nfs.rename(splitClientMessage[1], splitClientMessage[2]);
					break;
				case FileCommand.CREATE:
					outputStream.writeUTF("File to be created: " + splitClientMessage[1]);
					nfs.create(splitClientMessage[1]);
					break;
				case FileCommand.REMOVE:
					outputStream.writeUTF("File to be removed: " + splitClientMessage[1]);
					nfs.remove(splitClientMessage[1]);
					break;
				case FileCommand.STOP_CLIENT:
					outputStream.writeUTF(FileCommand.STOP_CLIENT.label);
					isToReadClientMsg = false;
					break;
				// unknown command
				default:
					throw new IllegalArgumentException("Unexpected value: " + splitClientMessage[0]);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
