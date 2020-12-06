package interface_server;

import Main.Activite;
import Main.Main;
import Main.Session;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main_server {

    private int port = 2345;
    private String host = "127.0.0.1";
    private ServerSocket server = null;
    private boolean isRunning = true;
    private String type;
    private RobotClientProcessor clientRobot;
    private AppClientProcessor clientApp;
    private Activite act;
    public boolean robotConnected = false;

    public Main_server() {
        try {
            server = new ServerSocket(port, 10, InetAddress.getByName(host));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Main_server(String pHost, int pPort, String ptype) {
        host = pHost;
        port = pPort;
        type = ptype;
        try {
            server = new ServerSocket(port, 100, InetAddress.getByName(host));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void open() {

        Thread t = new Thread(new Runnable() {
            public void run() {
                while (isRunning == true) {

                    try {
                        Socket client = server.accept();
                        System.out.println("Connexion cliente reçue.");
                        Thread t;
                        switch (type) {
                        case "app":
                            t = new Thread(clientApp =
                                               new AppClientProcessor(client));
                            t.start();
                            break;
                        case "robot":
                            robotConnected = true;
                            t = new Thread(
                                clientRobot =
                                    new RobotClientProcessor(client, act));
                            t.start();
                            break;
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    server = null;
                }
            }
        });

        t.start();
    }

    public void close() { isRunning = false; }

    public AppClientProcessor getClientApp() { return this.clientApp; }

    public RobotClientProcessor getRobotApp() { return this.clientRobot; }

    public void setActivite(Activite act) { this.act = act; }

    public Activite getActivite() { return this.act; }
}