/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.restserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class E01Server {

    static class Handler implements HttpHandler {
    
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Successful execution";
            byte[] responseData = response.getBytes();
            exchange.sendResponseHeaders(200, responseData.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseData);
            os.close();
        }
    }

    public static void main(String args[]) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        httpServer.createContext("/", new Handler());
        httpServer.start();
    }    
}
