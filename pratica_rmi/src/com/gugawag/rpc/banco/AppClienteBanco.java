package com.gugawag.rpc.banco;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class AppClienteBanco {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        // procura o serviço no RMI Registry local. Perceba que o cliente não connhece a implementação do servidor,
        // apenas a interface
        Registry registry = LocateRegistry.getRegistry(args[0]);
        BancoServiceIF banco = (BancoServiceIF) registry.lookup("BancoService");

        menu();
        Scanner entrada = new Scanner(System.in);
        int opcao = entrada.nextInt();

        while(opcao != 9) {
            switch (opcao) {
                case 1: {
                    System.out.println("Digite o número da conta:");
                    String conta = entrada.next();
                    //chamada ao método remoto, como se fosse executar localmente
                    System.out.println(banco.saldo(conta));
                    break;
                }
                case 2: {
                    //chamada ao método remoto, como se fosse executar localmente
                    System.out.println(banco.quantidadeContas());
                    break;
                }
                case 3:{
                    System.out.println("Numero da Conta a ser Criada: ");
                    String numeroConta = entrada.next();
                    //chamada ao método remoto, como se fosse executar localmente
                    banco.adicionarConta(numeroConta);
                    System.out.println("Conta Criada Com Sucesso");
                    break;
                }
                case 4:{
                    System.out.println("Numero da Conta a ser Buscada: ");
                    String numeroConta = entrada.next();
                    //chamada ao método remoto, como se fosse executar localmente
                    System.out.println("conta encontrada: " + banco.pesquisarConta(numeroConta));
                    break;
                }
                case 5:{
                    System.out.println("Numero da Conta a ser Excluida: ");
                    int numeroConta = entrada.nextInt();
                    //chamada ao método remoto, como se fosse executar localmente
                    System.out.println("conta excluida: " + banco.removerConta(Integer.toString(numeroConta)));
                    break;
                }
            }
            menu();
            opcao = entrada.nextInt();
        }

        entrada.close();
    }

    public static void menu() {
        System.out.println("\n=== BANCO RMI (ou FMI?!) ===");
        System.out.println("=== Yago César do Nascimento Aguiar ===");
        System.out.println("1 - Saldo da conta");
        System.out.println("2 - Quantidade de contas");
        System.out.println("3 - Criar Conta");
        System.out.println("4 - Pesquisar Conta");
        System.out.println("5 - Excluir Conta");
        System.out.println("9 - Sair");
    }

}
