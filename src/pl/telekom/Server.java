package pl.telekom;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private Socket socket;

    private OutputStream out;

    private String xd = "dictionary";

    public Server(int port, String path) {
        try {
            HuffmanCode huffmanCode = new HuffmanCode();
            huffmanCode.code(path, "compressed", "dictionary");
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
                File file;

                socket = serverSocket.accept();

                if ("dictionary".equals(xd)) {
                    xd = "compressed";
                } else {
                    xd = "dictionary";
                }

                file = new File (xd);
                byte[] byteArray  = new byte [(int)file.length()];

                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);

                bis.read(byteArray,0, byteArray.length);

                out = socket.getOutputStream();

                System.out.println("Sending file...");
                out.write(byteArray,0, byteArray.length);
                out.flush();

                fis.close();
                bis.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
