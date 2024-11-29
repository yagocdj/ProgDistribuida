package br.edu.ifpb.pdist.nfs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NFSAppServer {
	public static void main(String[] args) throws IOException {
		
		System.out.println("===== NFS Server =====");

        ServerSocket serverSocket = new ServerSocket(7000);
        Socket socket = serverSocket.accept();

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        while (true) {
            System.out.println("Client: " + socket.getInetAddress());

            String mensagem = dis.readUTF();
            System.out.println(mensagem);

            dos.writeUTF("I've read your message -> " + mensagem);
        }
        
        //serverSocket.close();
	}
}
