package UDP;

import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 1234;
    private static final int BUFFER_SIZE = 1024;
    private final DatagramSocket socket;

    public Server() {
        try {
            socket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            System.err.println();
            throw new RuntimeException("Error opening socket on port: " + PORT, e);
        }
    }

    public void startServer() {
        System.out.println("UDP.Server initializing...");

        byte[] buffer = new byte[BUFFER_SIZE];

        while (true) {
            try {
                // Cria um pacote para receber dados com o tamanho do buffer definido e envia
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivePacket);

                // Cria um novo array com o tamanho exato dos bytes recebidos para evitar dados "lixo"
                byte[] data = new byte[receivePacket.getLength()];
                System.arraycopy(receivePacket.getData(), 0, data, 0, receivePacket.getLength());

                // Desserializa os bytes recebidos para um objeto UDP.Message
                Message message = deserializeMessage(data);
                System.out.println("Received message: " + message.getFirstNumber() + " and " + message.getSecondNumber());

                // Verifica se sao coprimos
                boolean areCoprime = checkCoprime(message.getFirstNumber(), message.getSecondNumber());
                System.out.println("Are coprime? " + areCoprime);

                // Serializa o resultado booleano para envio ao cliente, cria o pacote e envia ao cliente
                byte[] response = serializeBoolean(areCoprime);
                DatagramPacket sendPacket = new DatagramPacket(response, response.length, receivePacket.getAddress(), receivePacket.getPort());
                socket.send(sendPacket);

            } catch (IOException e) {
                System.err.println("Communication error: " + e.getMessage());
            }
        }

    }

    private static Message deserializeMessage(byte[] buffer) throws IOException {
        try (ByteArrayInputStream in = new ByteArrayInputStream(buffer); ObjectInputStream din = new ObjectInputStream(in)) {
            return (Message) din.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Error deserializing UDP.Message object", e);
        }
    }

    private static byte[] serializeBoolean(Boolean result) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream(); ObjectOutputStream dout = new ObjectOutputStream(out)) {
            dout.writeObject(result);
            dout.flush();
            return out.toByteArray();
        }
    }

    private static boolean checkCoprime(int a, int b) {
        return gcd(a, b) == 1;
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }
}
