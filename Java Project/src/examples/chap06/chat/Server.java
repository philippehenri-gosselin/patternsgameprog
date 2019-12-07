/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Server {
    
    private final ServerSocket serverSocket;
    
    private Socket socket;
    
    private PrintWriter outputWriter;
    
    private BufferedReader inputReader;
    
    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }
    
    public void run() throws Exception {
        System.out.println("Waiting for connection...");
        socket = serverSocket.accept();
        System.out.println("Connection established.");
        outputWriter = new PrintWriter(socket.getOutputStream());
        inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Thread listenThread = new ListenThread();
        Thread sendThread = new SendThread();
        listenThread.start();
        sendThread.start();
        listenThread.join();
        sendThread.join();
        System.out.println("End of server");
    }
    
    public class ListenThread extends Thread {

        public void run() {
            try {
                while(true) {
                    String message = inputReader.readLine();
                    if (message == null || message.equals("bye")) {
                        System.out.println("End of client connection.");
                        break;
                    }
                    System.out.println("Client>"+message);
                }
                socket.close();
                serverSocket.close();
            }
            catch(SocketException ex) {
            }
            catch (IOException ex) {
            }
        }
        
    }

    public class SendThread extends Thread {

        public void run() {
            try {
                Scanner userInput = new Scanner(System.in);
                while(true) {
                    String message = userInput.nextLine();
                    outputWriter.println(message);
                    outputWriter.flush();
                    if (message.equals("bye"))
                        break;
                }
                socket.close();
                serverSocket.close();
            }
            catch(SocketException ex) {
            }
            catch (IOException ex) {
            }
        }
        
    }
    
    public static void main(String[] args) {
        try {
            Server server = new Server(8000);
            server.run();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}
