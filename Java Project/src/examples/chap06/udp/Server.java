/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.udp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {

    private DatagramSocket socket;
    
    private static class Player {
        public boolean present = false;
        public InetAddress address;
        public int port;
        public int[] coords = new int[2];
    }

    private Player[] players = new Player[4];

    public Server() {
        for (int i=0;i<players.length;i++)
            players[i] = new Player(); 
        players[0].coords[0] = 100;
        players[0].coords[1] = 100;
        players[1].coords[0] = 700;
        players[1].coords[1] = 100;
        players[2].coords[0] = 100;
        players[2].coords[1] = 500;
        players[3].coords[0] = 700;
        players[3].coords[1] = 500;
    }
    
    public int findPlayer(InetAddress address,int port) {
        for (int i=0;i<players.length;i++) {
            if (players[i].present
             && players[i].address.equals(address)
             && players[i].port == port) {
                return i;
            }
        }
        return -1;
    }

    public int findFreePlayer() {
        for (int i=0;i<players.length;i++) {
            if (!players[i].present) {
                return i;
            }
        }
        return -1;
    }
    
    private class ListenThread extends Thread {
        
        public void run() {
            while(true) {
                try {
                    byte[] inputData = new byte[1024];
                    DatagramPacket inputPacket = new DatagramPacket(inputData,inputData.length);
                    socket.receive(inputPacket);
                    InetAddress playerAddress = inputPacket.getAddress();
                    int playerPort = inputPacket.getPort();
                    int playerIndex = findPlayer(playerAddress, playerPort);

                    ByteArrayInputStream bis = new ByteArrayInputStream(inputData);
                    DataInputStream dis = new DataInputStream(bis);               
                    byte messageCode = dis.readByte();
                    if (messageCode == MessageCode.NEW_PLAYER.getCode()) {
                        if (playerIndex < 0) {
                            playerIndex = findFreePlayer();
                        }
                        byte[] outputData = new byte[1];
                        if (playerIndex < 0) {
                            outputData[0] = (byte)255;
                        }
                        else {
                            players[playerIndex].present = true;
                            players[playerIndex].address = playerAddress;
                            players[playerIndex].port = playerPort;
                            outputData[0] = (byte)playerIndex;
                        }
                        DatagramPacket outputPacket = new DatagramPacket(outputData,outputData.length,playerAddress,playerPort);
                        socket.send(outputPacket);
                    }
                    else if (messageCode == MessageCode.PLAYER_COORD.getCode()) {
                        synchronized(this) {
                            players[playerIndex].coords[0] = dis.readInt();
                            players[playerIndex].coords[1] = dis.readInt();
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public class SendThread extends Thread 
    {        
        public void run() {
            int fps = 60;
            long nanoPerFrame = (long) (1000000000.0 / fps);
            long lastTime = 0;
            while(true) {
                long nowTime = System.nanoTime();
                if ((nowTime - lastTime) < nanoPerFrame) {
                    continue;
                }
                lastTime = nowTime;
                try {
                    byte[] outputData;
                    synchronized(this) {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        DataOutputStream dos = new DataOutputStream(bos);
                        dos.writeByte(MessageCode.PLAYER_COORDS.getCode());
                        for (int i=0;i<players.length;i++) {
                            dos.writeInt(players[i].coords[0]);
                            dos.writeInt(players[i].coords[1]);
                        }
                        outputData = bos.toByteArray();
                    }
                    
                    for (int i=0;i<players.length;i++) {
                        if (!players[i].present)
                            continue;
                        InetAddress playerAddress = players[i].address;
                        int playerPort = players[i].port;
                        DatagramPacket outputPacket = new DatagramPacket(outputData,outputData.length,playerAddress,playerPort);
                        socket.send(outputPacket);
                    }
                    
                    long elapsed = System.nanoTime() - lastTime;
                    long milliSleep = (nanoPerFrame - elapsed) / 1000000;
                    if (milliSleep > 0) {
                        try {
                            Thread.sleep(milliSleep);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public void run() throws InterruptedException {
        try {
            socket = new DatagramSocket(8000);
        } catch (SocketException ex) {
            System.out.println("Error when opening socket. The port is free ?");
            return;
        }


        ListenThread listenThread = new ListenThread();
        SendThread sendThread = new SendThread();
        listenThread.start();
        sendThread.start();
        listenThread.join();
    }
    
    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();        
        server.run();
    }    
}
