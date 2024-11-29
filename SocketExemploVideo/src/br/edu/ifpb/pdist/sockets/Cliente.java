package br.edu.ifpb.pdist.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	public static void main(String[] args) throws Exception {
		System.out.println("=== Cliente ===");
		Socket socket = new Socket("localhost", 6000);
		
		DataOutputStream saidaSocket = new DataOutputStream(socket.getOutputStream());
		DataInputStream entradaSocket = new DataInputStream(socket.getInputStream());
		
		while (true) {
			Scanner teclado = new Scanner(System.in);
			String texto = teclado.nextLine();
			saidaSocket.writeUTF(texto);
			
			String mensagemRecebida = entradaSocket.readUTF();
			System.out.println("Recebi: " + mensagemRecebida);
		}
	}
}
