package UDP;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Client {
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 1234;
    private static final int BUFFER_SIZE = 1024;
    private static final int NUM_REQUESTS = 10;

    public Client() {
    }

    public void sendMessages() {
        try {
            Random random = new Random();
            byte[] buffer;
            byte[] answer = new byte[BUFFER_SIZE];
            InetAddress address = InetAddress.getByName(HOSTNAME);
            DatagramSocket socket = new DatagramSocket();

            for (int i = 0; i < NUM_REQUESTS; i++) {
                // Cria mensagem com 2 numeros aleatórios
                Message msg = new Message();
                buffer = serializeMessage(msg);

                // Manda a mensagem para o servidor
                DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, address, PORT);
                socket.send(sendPacket);

                // Recebe a mensagem(um boolean)
                DatagramPacket receivePacket = new DatagramPacket(answer, answer.length);
                socket.receive(receivePacket);

                // copia somente os bytes recebidos para evitar lixo(pq eh um object)
                byte[] receivedData = new byte[receivePacket.getLength()];
                System.arraycopy(receivePacket.getData(), 0, receivedData, 0, receivePacket.getLength());

                Boolean result = deserializeBoolean(receivedData);

                // Mostra a mensagem e resultado
                System.out.println("UDP.Message sent: " + msg.getFirstNumber() + ", " + msg.getSecondNumber());
                printResult(result);

                Thread.sleep(1000);
            }

            socket.close();

        } catch (IOException e) {
            throw new RuntimeException("I/O error in client communication", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] serializeMessage(Message message) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutputStream dout = new ObjectOutputStream(out)) {
            dout.writeObject(message);
            dout.flush();
            return out.toByteArray();
        }
    }

    private static Boolean deserializeBoolean(byte[] buffer) throws IOException {
        try (ByteArrayInputStream in = new ByteArrayInputStream(buffer);
             ObjectInputStream din = new ObjectInputStream(in)) {
            return (Boolean) din.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Error deserializing Boolean", e);
        }
    }

    private static void printResult(boolean result) {
        if (result) {
            System.out.println("✅ The two numbers are coprime!\n");
        } else {
            System.out.println("❌ The numbers are NOT coprime.\n");
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.sendMessages();
    }
}
