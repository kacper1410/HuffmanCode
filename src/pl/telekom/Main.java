package pl.telekom;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        String host = InetAddress.getLocalHost().getHostName();
        Thread thread;
        String path = "";
        int port;

        Scanner in = new Scanner(System.in);
        System.out.println("1. Server, 2. Client");

        switch (in.nextInt()) {
            case 1:
                System.out.println("Server: " + host);

                System.out.println("Choose file name to send: ");
                path = in.next();

                System.out.println("Choose port: ");
                Server server = new Server(in.nextInt(), path);
                thread = new Thread(server);
                thread.start();
                break;
            case 2:
                System.out.println("Choose file name to receive: ");
                path = in.next();

                System.out.println("Choose server: ");
                host = in.next();

                System.out.println("Choose port: ");
                port = in.nextInt();
                Client client = new Client(port, host, "compressed");
                thread = new Thread(client);
                thread.start();

                Thread.sleep(1500);

                Client clientD = new Client(port, host, "dictionary");
                thread = new Thread(clientD);
                thread.start();

                System.out.println("Click d to decompress");
                in.next();
                HuffmanCode huffmanCode = new HuffmanCode();
                huffmanCode.decode(path, "compressed", "dictionary");
                break;
        }

        in.close();
    }
}
