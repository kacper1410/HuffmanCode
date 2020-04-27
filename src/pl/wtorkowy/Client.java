package pl.wtorkowy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable {
    private int port;
    private Socket socket;
    private String host;
    private Message message;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Client(int port, String host, Message message) {
        this.port = port;
        this.host = host;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            System.out.println("Client is starting on host " + InetAddress.getLocalHost().getHostAddress());
            socket = new Socket(host, port);

            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();

            out.writeObject(message);

            socket.close();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
