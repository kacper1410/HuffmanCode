package pl.telekom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private Socket socket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Server is starting");

        while (true) {
            try {
                socket = serverSocket.accept();

                in = new ObjectInputStream(socket.getInputStream());
                System.out.println((Message)in.readObject());

                in.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
