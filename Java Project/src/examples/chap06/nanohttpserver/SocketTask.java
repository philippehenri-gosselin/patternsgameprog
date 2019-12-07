/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.nanohttpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import pacman.mt.Task;
import pacman.mt.TaskManager;

public class SocketTask extends Task {

    private String state;
    
    private final Socket socket;
    
    private final int connectionCount;
    
    private BufferedReader inputReader;
    
    private final HttpRequest httpRequest = new HttpRequest();
   
    public SocketTask(Socket socket, int connectionCount) {
        this.socket = socket;
        this.connectionCount = connectionCount;
    }    
    
    public void run() {
        try {
            if (state == null) {
                handleHeader();
            }
            else if (state.equals("GET")) {
                handleGet();
            }
            else if (state.equals("POST")) {
                handlePost();
            }
            else {
                throw new HttpException(HttpStatusCode.INTERNAL_SERVER_ERROR, state);
            }
            return;
        } catch (HttpException ex) {
            try {
                writeResponse(ex.getStatus(),ex.getMessage());
            } catch (IOException ex2) {
            }
        } catch (SocketException ex) {
            System.out.println("Connection lost with client "+connectionCount);
        } catch (IOException ex) {
            System.out.println("Error when communicating with client "+connectionCount);
        } catch (Exception ex) {
            System.out.println("Error with client "+connectionCount);
        }
        try {
            writeResponse(HttpStatusCode.INTERNAL_SERVER_ERROR);
        } catch (IOException ex) {
        }
    }
        
    private void handleHeader() throws IOException, InterruptedException, HttpException {
        readRequestHeader();

        TaskManager taskManager = TaskManager.getInstance();
        String method = httpRequest.getMethod();
        if (method.equals("GET")) {
            state = "GET";
            taskManager.addConsumerTask(this);
        }
        else if (method.equals("POST")) {
            state = "POST";
            taskManager.addConsumerTask(this);
        }
        else {
            throw new HttpException(HttpStatusCode.BAD_METHOD, "Invalid method "+method);
        }
    }
    
    private void handleGet() throws IOException {
        String message;
        if (httpRequest.getPath().equals("/testget")) {
            message = "Test GET passed";
        }
        else {
            message = "Successful execution";
        }
        writeResponse(HttpStatusCode.OK, message);
        System.out.println("End of connection with client "+connectionCount);
    }
    
    private void handlePost() throws IOException, HttpException {
        String content = readRequestContent();
        if (content == null) {
            throw new HttpException(HttpStatusCode.BAD_REQUEST, "No content");
        }
        if (httpRequest.getPath().equals("/testpost")) {
            if (!content.equals("x=1,y=2")) {
                throw new HttpException(HttpStatusCode.BAD_REQUEST, "Invalid content");
            }
        }
        else {
            System.out.println(content);
        }
        writeResponse(HttpStatusCode.NO_CONTENT);
        System.out.println("End of connection with client " + connectionCount);
    }
    
    private void readRequestHeader() throws IOException, HttpException 
    {
        inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),NanoHttpServer.contentCharset));
        String line = inputReader.readLine();
        if (line == null || line.isEmpty()) {
            throw new HttpException(HttpStatusCode.BAD_REQUEST);
        }
        String[] parts = line.split(" ");
        if (parts.length != 3) {
            throw new HttpException(HttpStatusCode.BAD_REQUEST);
        }
        httpRequest.setMethod(parts[0]);
        httpRequest.setPath(parts[1]);
        httpRequest.setVersion(parts[2]);

        while (true) {
            line = inputReader.readLine();
            if (line == null || line.isEmpty())
                break;
            int index = line.indexOf(':');
            if (index < 0) {
                throw new IOException();
            }
            String name = line.substring(0, index).trim();
            String value = line.substring(index+1).trim();
            httpRequest.setHeader(name,value);
        }
    }
    
    private String readRequestContent() throws HttpException {
        String contentLength = httpRequest.getHeader("Content-Length");
        if (contentLength == null) {
            return null;
        }
        int contentSize = Integer.parseInt(contentLength);
        if (contentSize >= NanoHttpServer.MAX_CONTENT_LENGTH) {
            throw new HttpException(HttpStatusCode.BAD_REQUEST, "Content too large");
        }
        try {
            char[] buffer = new char[contentSize];
            inputReader.read(buffer);
            return new String(buffer);
        } catch (IOException ex) {
            return null;
        }
    }

    public void writeResponse(HttpStatusCode status) throws IOException {
        writeResponse(status,null);
    }
    
    public void writeResponse(HttpStatusCode status,String content) throws IOException {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append("HTTP/1.1 ");
        headerBuilder.append(String.valueOf(status.getCode()));
        headerBuilder.append(" ");
        headerBuilder.append(status.toString());
        headerBuilder.append("\n");
        headerBuilder.append("Content-Type: text/plain; charset=utf-8\n");
        byte[] contentBytes = null;
        if (content != null && !content.isEmpty()) {
            contentBytes = content.getBytes(NanoHttpServer.contentCharset);
            headerBuilder.append("Content-Length: ");
            headerBuilder.append(String.valueOf(contentBytes.length));
            headerBuilder.append("\n");
            headerBuilder.append("\n");
        }
        String header = headerBuilder.toString();
        byte[] headerBytes = header.getBytes(NanoHttpServer.contentCharset);
        socket.getOutputStream().write(headerBytes);
        if (contentBytes != null) {
            socket.getOutputStream().write(contentBytes);
        }
        socket.close();
    }

}
