/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.udp;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JFrame;

public class Client extends JFrame implements KeyListener {

    private class Player {
        public int[] coords = new int[2];
    }
    
    private Player[] players = new Player[4];
    
    private int playerIndex = -1;
    
    private int serverPort;
    
    private InetAddress serverAddress;
    
    private DatagramSocket socket;
    
    private int canvasWidth = 800;
    private int canvasHeight = 600;
    private Canvas canvas;
    
    public Client(InetAddress serverAddress,int serverPort) 
    {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        for (int i=0;i<players.length;i++)
            players[i] = new Player(); 
    }     
    
    public void run() throws UnknownHostException 
    {

        try {
            socket = new DatagramSocket();
        } catch (SocketException ex) {
            System.out.println("Aucun port de libre");
            throw new RuntimeException();
        }
        
        try {
            byte[] inputData = new byte[1];
            inputData[0] = MessageCode.NEW_PLAYER.getCode();
            DatagramPacket inputPacket = new DatagramPacket(inputData,inputData.length,serverAddress,serverPort);
            socket.send(inputPacket);

            byte[] outputData = new byte[1024];
            DatagramPacket outputPacket = new DatagramPacket(outputData,outputData.length);
            socket.receive(outputPacket);

            playerIndex = outputData[0];
            if (playerIndex < 0 || playerIndex >= 4) {
                System.out.println("Server is full");
                return;
            }

        } catch (IOException ex) {
            System.out.println("Erreur lors de la communication");
            throw new RuntimeException();
        }        
        
        new ListenThread().start();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Protocole UDP");
        
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(canvasWidth,canvasHeight));
        canvas.setMinimumSize(new Dimension(canvasWidth,canvasHeight));
        canvas.setMaximumSize(new Dimension(canvasWidth,canvasHeight));
        add(canvas);
        pack();
        
        addKeyListener(this);
        requestFocusInWindow();        
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private class ListenThread extends Thread
    {
        public void run() {
            while(true) {
                try {
                    byte[] inputData = new byte[1024];
                    DatagramPacket inputPacket = new DatagramPacket(inputData,inputData.length,serverAddress,serverPort);
                    socket.receive(inputPacket);
                    ByteArrayInputStream bis = new ByteArrayInputStream(inputData);
                    DataInputStream dis = new DataInputStream(bis);
                    byte messageCode = dis.readByte();
                    if (messageCode == MessageCode.PLAYER_COORDS.getCode()) {
                        for(int i=0;i<players.length;i++) {
                            players[i].coords[0] = dis.readInt();
                            players[i].coords[1] = dis.readInt();
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if(isVisible()) {
                    render();
                }
            }
        }
    }
    
    public void render() {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2);
            return;
        }
        Graphics g = null;
        try {
            g = bs.getDrawGraphics();

            g.setColor(Color.black);
            g.fillRect(0,0,canvasWidth,canvasHeight);        
            for (int i=0;i<4;i++) {
                switch(i) {
                    case 0: g.setColor(Color.red); break;
                    case 1: g.setColor(Color.blue); break;
                    case 2: g.setColor(Color.green); break;
                    default: g.setColor(Color.white); break;
                }
                g.fillOval(players[i].coords[0]-12, players[i].coords[1]-12, 24, 24);
            }
            
            bs.show();
        }
        finally {
            if (g != null) {
                g.dispose();
            }
        }
    }    
    
    public void sendCoords(int sx, int sy) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeByte(MessageCode.PLAYER_COORD.getCode());
            dos.writeInt(players[playerIndex].coords[0]+sx);
            dos.writeInt(players[playerIndex].coords[1]+sy);
            byte[] inputData = bos.toByteArray();
            DatagramPacket inputPacket = new DatagramPacket(inputData,inputData.length,serverAddress,serverPort);
            socket.send(inputPacket);
        } catch (IOException ex) {
            System.out.println("Erreur lors de la communication");
            return;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                sendCoords(5,0);
                break;
            case KeyEvent.VK_LEFT:
                sendCoords(-5,0);
                break;
            case KeyEvent.VK_DOWN:
                sendCoords(0,5);
                break;
            case KeyEvent.VK_UP:
                sendCoords(0,-5);
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
        
    public static void main(String[] args) throws UnknownHostException {
        Client client = new Client(InetAddress.getByName("localhost"),8000);
        client.run();
    }      
}
