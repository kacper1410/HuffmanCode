package pl.wtorkowy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private int port;
    private ServerSocket server;
    private Socket socket;
    private String host;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Server(int port, String host) throws IOException {
        this.port = port;
        this.host = host;

        server = new ServerSocket(port);
    }

    @Override
    public void run() {
        System.out.println("Server is starting");

        while (true) {
            try {
                socket = server.accept();

                out = new ObjectOutputStream(socket.getOutputStream());
                out.flush();
                in = new ObjectInputStream(socket.getInputStream());

                System.out.println((Message)in.readObject());

                out.close();
                in.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
