package pl.wtorkowy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws UnknownHostException {
        String host = InetAddress.getLocalHost().getHostName();
        int port = 80;
        Thread thread;

        Scanner in = new Scanner(System.in);
        System.out.println("1. Server, 2. Client");
        switch (in.nextInt()) {
            case 1:
                try {
                    Server server = new Server(port, host);
                    thread = new Thread(server);
                    thread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                System.out.println("Write message: ");
                Client client = new Client(port, host, new Message(in.next()));
                thread = new Thread(client);
                thread.start();
                break;
        }

    }
}
