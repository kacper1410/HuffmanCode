package pl.telekom;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {
    private int port;
    private Socket socket;
    private String host;
    private String xd;

    private InputStream in;

    public Client(int port, String host, String xd) {
        this.port = port;
        this.host = host;
        this.xd = xd;
    }

    @Override
    public void run() {
        try {
            int bytesRead;
            int current;

            socket = new Socket(host, port);

            byte[] byteArray  = new byte [999999];

            FileOutputStream fos = new FileOutputStream(xd);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            in = socket.getInputStream();

            bytesRead = in.read(byteArray,0, byteArray.length);
            current = bytesRead;

            do {
                bytesRead = in.read(byteArray, current, (byteArray.length - current));
                if (bytesRead >= 0) {
                    current += bytesRead;
                }
            } while (bytesRead > -1);

            bos.write(byteArray, 0 , current);
            bos.flush();

            fos.close();
            bos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
