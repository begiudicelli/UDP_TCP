package TCP.OneConnection;

import java.io.*;
import java.net.Socket;

public class Client {

    private static final String HOSTNAME = "localhost";
    private static final int PORT = 1234;
    private static final int NUM_REQUESTS = 10;

    public void sendMessages() {
        for (int i = 0; i < NUM_REQUESTS; i++) {
            try (Socket socket = createConnection()) {
                Message msg = generateMessage();
                sendMessage(socket, msg);
                boolean response = receiveResponse(socket);

                System.out.println("Message sent: " + msg.getFirstNumber() + ", " + msg.getSecondNumber());
                System.out.println("Message received: " + response + "\n");
                Thread.sleep(1000);
            } catch (IOException e) {
                throw new RuntimeException("Error sending message: " + e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Socket createConnection() throws IOException {
        return new Socket(HOSTNAME, PORT);
    }

    private Message generateMessage() {
        return new Message();
    }

    private void sendMessage(Socket socket, Message msg) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(msg);
        out.flush();
    }

    private boolean receiveResponse(Socket socket) throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        return in.readBoolean();
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.sendMessages();
    }

}
