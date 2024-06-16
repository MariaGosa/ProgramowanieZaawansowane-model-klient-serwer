package com.example.client;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ObjectClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 12555;
        int clientId = new java.util.Random().nextInt(1000);

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream())) {

            out.println(clientId);
            String response = in.readLine();
            if ("REFUSED".equals(response)) {
                System.out.println("Client " + clientId + ": Connection refused by server.");
                return;
            } else {
                System.out.println("Client " + clientId + ": Connected to server.");
            }

            String[] requests = {"get_Toyota", "get_Volkswagen", "get_Peugeot"};
            for (String request : requests) {
                out.println(request);

                try {
                    List<?> receivedObjects = (List<?>) objectIn.readObject();
                    System.out.println("Client " + clientId + ": Received objects " + receivedObjects);
                } catch (ClassCastException e) {
                    System.out.println("Client " + clientId + ": Error casting received objects.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
