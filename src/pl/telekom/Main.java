package pl.telekom;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws UnknownHostException {
        HuffmanCode h = new HuffmanCode("jakas wiadomosccc");
        h.code();
//        String host = InetAddress.getLocalHost().getHostName();
//        int port = 5000;
//        Thread thread;
//
//        Scanner in = new Scanner(System.in);
//        System.out.println("1. Server, 2. Client");
//        switch (in.nextInt()) {
//            case 1:
//                System.out.println("Server: " + host);
//                Server server = new Server(port);
//                thread = new Thread(server);
//                thread.start();
//                break;
//            case 2:
//                System.out.println("Write message: ");
//                Client client = new Client(port, host, new Message(in.next()));
//                thread = new Thread(client);
//                thread.start();
//                break;
//        }

    }
}
