package com.example.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ObjectServer {
    private static final int PORT = 12555;
    private static final int MAX_CLIENTS = 6;
    private static int activeClients = 4;

    private static final Map<String, List<Serializable>> objects = new HashMap<>();

    static {
        objects.put("Toyota", Arrays.asList(
                new Toyota("Yaris_1", "Corolla_1", "Supra_1", "Aygo_1"),
                new Toyota("Yaris_2", "Corolla_2", "Supra_2", "Aygo_2"),
                new Toyota("Yaris_3", "Corolla_3", "Supra_3", "Aygo_3"),
                new Toyota("Yaris_4", "Corolla_4", "Supra_4", "Aygo_4")
        ));
        objects.put("Volkswagen", Arrays.asList(
                new Volkswagen("Golf_1", "Polo_1", "Passat_1", "T-Cross_1"),
                new Volkswagen("Golf_2", "Polo_2", "Passat_2", "T-Cross_2"),
                new Volkswagen("Golf_3", "Polo_3", "Passat_3", "T-Cross_3"),
                new Volkswagen("Golf_4", "Polo_4", "Passat_4", "T-Cross_4")
        ));
        objects.put("Peugeot", Arrays.asList(
                new Peugeot("207_1", "508_1", "5008_1", "406_1"),
                new Peugeot("207_2", "508_2", "5008_2", "406_2"),
                new Peugeot("207_3", "508_3", "5008_3", "406_3"),
                new Peugeot("207_4", "508_4", "5008_4", "406_4")
        ));
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                synchronized (ObjectServer.class) {
                    if (activeClients < MAX_CLIENTS) {
                        activeClients++;
                        new Thread(new ClientHandler(clientSocket)).start();
                    } else {
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        out.println("REFUSED");
                        clientSocket.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 ObjectOutputStream objectOut = new ObjectOutputStream(clientSocket.getOutputStream())) {

                String clientId = in.readLine();
                System.out.println("Client connected: " + clientId);
                out.println("OK");

                for (int i = 0; i < 3; i++) {
                    String request = in.readLine();
                    String className = request.split("_")[1];

                    List<Serializable> objList = objects.get(className);
                    if (objList != null) {
                        objectOut.writeObject(objList);
                        System.out.println("Sent " + objList.size() + " " + className + " objects to client " + clientId);
                    } else {
                        objectOut.writeObject(new ArrayList<>());
                        System.out.println("No objects found for " + className + " to client " + clientId);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                synchronized (ObjectServer.class) {
                    activeClients--;
                }
            }
        }
    }
}
