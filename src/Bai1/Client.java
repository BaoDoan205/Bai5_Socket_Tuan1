package Bai1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 12345;
        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Connected to the server");

            BufferedReader inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outputToServer = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader inputFromConsole = new BufferedReader(new InputStreamReader(System.in));
            String serverMessage, clientMessage;

            while (true) {
                // Send message to server
                System.out.print("Client: ");
                clientMessage = inputFromConsole.readLine();
                outputToServer.println(clientMessage);

                // Read message from server
                if ((serverMessage = inputFromServer.readLine()) != null) {
                    System.out.println("Server: " + serverMessage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
