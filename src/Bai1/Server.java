package Bai1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 12345;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outputToClient = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader inputFromConsole = new BufferedReader(new InputStreamReader(System.in));
            String clientMessage, serverMessage;

            while (true) {
                // Read message from client
                if ((clientMessage = inputFromClient.readLine()) != null) {
                    System.out.println("Client: " + clientMessage);
                }

                // Send message to client
                System.out.print("Server: ");
                serverMessage = inputFromConsole.readLine();
                outputToClient.println(serverMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
