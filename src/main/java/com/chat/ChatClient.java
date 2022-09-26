package com.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Scanner;


public class ChatClient {
    private final Scanner scanner;
    private SocketChannel clientChannel;

    public ChatClient() throws IOException {
        clientChannel = SocketChannel.open();
        clientChannel.connect(new InetSocketAddress(ChatServer.HOSTNAME, ChatServer.PORT));
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        String msg;
        try(BufferedReader in = new BufferedReader(new InputStreamReader(clientChannel.socket().getInputStream()));
        PrintWriter out = new PrintWriter(clientChannel.socket().getOutputStream(), true)) {
            do {
                System.out.print("Digite uma mensagem (ou sair para finalizar): ");
                msg = scanner.nextLine();
                out.println(msg);
            } while (!"sair".equalsIgnoreCase(msg));
        }finally{
            clientChannel.close();
        }
    }

    public static void main(String[] args) {
        try {
            ChatClient client = new ChatClient();
            client.start();
        } catch (IOException e) {
            System.err.println("Erro ao inicializar cliente: " + e.getMessage());
        }
    }
}
