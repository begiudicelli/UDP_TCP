package TCP.SameConnection;

import TCP.MathUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 1234;
    private final ServerSocket serverSocket;

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciado na porta " + PORT);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao abrir o socket na porta: " + PORT, e);
        }
    }

    public void runServer() {
        System.out.println("Aguardando conexões...\n");
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort() + "\n");
                new Thread(() -> handleClient(clientSocket)).start();

            } catch (IOException e) {
                System.err.println("Erro ao aceitar conexão: " + e.getMessage());
            }
        }
    }

    private void handleClient(Socket socket) {
        try (
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream())
        ) {
            while (true) {
                TCPMessage msg = (TCPMessage) in.readObject();
                String id = msg.getId();
                int a = msg.getFirstNumber();
                int b = msg.getSecondNumber();

                System.out.printf("Recebido de %s: %d, %d%n", id, a, b);

                boolean result = MathUtils.areCoprime(a, b);
                out.writeBoolean(result);
                out.flush();

                System.out.printf("Resposta enviada: %b%n\n", result);
            }
        } catch (EOFException e) {
            System.out.println("Cliente desconectado: " + socket.getInetAddress().getHostAddress() + "\n");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro com o cliente: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        Server server = new Server();
        server.runServer();
    }
}
