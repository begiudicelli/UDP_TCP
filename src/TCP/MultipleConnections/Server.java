package TCP.MultipleConnections;

import TCP.MathUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 1234;
    private static ServerSocket serverSocket;

    public Server(){
        try{
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException("Error opening socket on port: " + PORT, e);
        }
    }

    private void runServer(){
        try{
            System.out.println("Starting server...");
            while(true){
                System.out.println("Server waiting for connection...\n");
                Socket request = serverSocket.accept();
                System.out.println("New client connected: " + request.getInetAddress().getHostAddress() + ": " + request.getPort());

                UDPMessage msg = receiveMessage(request);
                boolean result = processMessage(msg);
                sendResponse(request, result);

                System.out.println("Response send to client: " + result);

                request.close();
                System.out.println("Connection closed.\n");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private static UDPMessage receiveMessage(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        UDPMessage msg = (UDPMessage) in.readObject();
        System.out.println("Recebido: " + msg.getFirstNumber() + ", " + msg.getSecondNumber());
        return msg;
    }

    private static boolean processMessage(UDPMessage msg){
        int a = msg.getFirstNumber();
        int b = msg.getSecondNumber();
        return MathUtils.areCoprime(a, b);
    }

    private static void sendResponse(Socket socket, boolean result) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeBoolean(result);
    }


    public static void main(String[] args) {
        Server server = new Server();
        server.runServer();
    }
}
