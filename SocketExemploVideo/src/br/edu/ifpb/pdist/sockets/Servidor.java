package br.edu.ifpb.pdist.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Servidor {
	public static void main(String[] args) throws Exception {
		System.out.println("=== Servidor ===");
		ServerSocket serverSocket = new ServerSocket(6000);
		
		Socket socket = serverSocket.accept();
		System.out.println("Cliente novo...");
		
		DataOutputStream saidaSocket = new DataOutputStream(socket.getOutputStream());
		DataInputStream entradaSocket = new DataInputStream(socket.getInputStream());
		
		while (true) {
			String mensagemRecebida = entradaSocket.readUTF();
			System.out.println("Recebi: " + mensagemRecebida);
			System.out.println("Cliente: " + socket.getInetAddress() + ":" +
					socket.getPort());
			
			String resposta = "";
			
			if (mensagemRecebida.startsWith("readdir")) {
				StringTokenizer strToken = new StringTokenizer(mensagemRecebida, " "); 
				strToken.nextToken();
				File pasta = new File(strToken.nextToken());
				File[] arquivos = pasta.listFiles();
				
				for (File arquivo : arquivos) {
					resposta += " " + arquivo.getName();
				}
			}

			Scanner teclado = new Scanner(System.in);
			String texto = teclado.nextLine();
			saidaSocket.writeUTF(texto + resposta);
		}
	}
}
