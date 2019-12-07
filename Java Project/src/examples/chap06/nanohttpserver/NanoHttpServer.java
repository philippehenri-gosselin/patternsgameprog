/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.nanohttpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import pacman.mt.Task;
import pacman.mt.TaskManager;

public class NanoHttpServer extends Thread {

    public static final int MAX_CONTENT_LENGTH = 1000;
    
    public static final Charset contentCharset = Charset.forName("UTF8");
    
    private final int port;
    
    private ServerSocket serverSocket;
    
    public NanoHttpServer(int port) {
        this.port = port;
    }
    
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("Error when creating the socket. Does the port "+port+" is free ?");
            return;
        }
        int connectionCount = 0;
        while(!interrupted()) {
            Socket socket;
            try {
                socket = serverSocket.accept();
            } catch (SocketException ex) {
                break;
            } catch (IOException ex) {
                System.out.println("Error waiting for a new connection");
                break;
            }
            System.out.println("Open connection with client "+connectionCount);
            Task task = new SocketTask(socket,connectionCount++);
            try {            
                TaskManager.getInstance().addProducerTask(task);
            } catch (InterruptedException ex) {
                interrupt();
            }
        }
    }
     
    public void terminate() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
            }
        }
    }
    
    public static void main(String[] args) {
        TaskManager taskManager = TaskManager.getInstance();
        taskManager.launch();
        NanoHttpServer server = new NanoHttpServer(8080);
        try {
            server.start();
            server.join();
        } catch (InterruptedException ex) {
        }
        taskManager.terminate();
    }
    
}
