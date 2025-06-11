package TCP.SameConnection;

import TCP.MathUtils;
import TCP.MultipleConnections.UDPMessage;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 1234;
    private static final int NUM_REQUESTS = 10;

    public void runClient() {
        try (
                Socket socket = new Socket(HOSTNAME, PORT);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream())
        ) {
            String clientId = IdGenerator.generateClientId();
            for (int i = 0; i < NUM_REQUESTS; i++) {
                TCPMessage msg = new TCPMessage(clientId);
                out.writeObject(msg);
                out.flush();

                boolean response = in.readBoolean();

                System.out.printf("Mensagem enviada: %d, %d%n", msg.getFirstNumber(), msg.getSecondNumber());
                System.out.println("São coprimos? " + response + "\n");
                Thread.sleep(500);
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Erro na comunicação com o servidor: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        Client client = new Client();
        client.runClient();
    }
}
