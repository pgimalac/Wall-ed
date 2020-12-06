package Main;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.imageio.ImageIO;

public class test1 {

    public static void main(String[] args)
        throws UnknownHostException, IOException, InterruptedException {
        // TODO Auto-generated method stub
        ServerSocket server =
            new ServerSocket(2346, 10, InetAddress.getByName("127.0.0.1"));
        while (true) {
            try {
                byte[] b = new byte[20000];
                Socket sock = server.accept();
                System.out.println("connection received");
                PrintWriter writer = new PrintWriter(sock.getOutputStream());
                BufferedInputStream reader =
                    new BufferedInputStream(sock.getInputStream());
                System.out.println(reader.read(b, 0, b.length));
                writer.write("sendImage");
                writer.flush();
                FileOutputStream image = new FileOutputStream(
                    "/home/florian/Téléchargements/image.png");
                int n;
                while ((n = reader.read(b, 0, b.length)) == 20000) {
                    System.out.println(n);
                    image.write(b, 0, n);
                }
                image.write(b, 0, n);
                // reader.read(b, 0, b.length);
                // BufferedImage imageData = ImageIO.read(reader);
                // System.out.println(imageData.getHeight());
                System.out.println("image received, storing it");
                // image.write(b, 0, b.length);
                // ImageIO.write(imageData, "png", image);
                sock.close();
            } finally {
            }
        }
    }
}
