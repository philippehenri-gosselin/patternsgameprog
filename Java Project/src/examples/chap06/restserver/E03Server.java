/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.restserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

public class E03Server {

    static class Handler implements HttpHandler {
    
        Charset utf8 = Charset.forName("UTF8");
        
        JsonBuilderFactory jsonBuilderFactory = Json.createBuilderFactory(null);
        
        JsonWriterFactory jsonWriterFactory;

        public Handler() {
            Map<String,Boolean> options = new HashMap();
            options.put(JsonGenerator.PRETTY_PRINTING, true);
            jsonWriterFactory = Json.createWriterFactory(options);
        }
        
        public void handle(HttpExchange exchange) throws IOException 
        {
            Headers headers = exchange.getResponseHeaders();
            headers.set("Content-Type","application/json; charset=utf-8");

            JsonObjectBuilder responseObjectBuilder = jsonBuilderFactory.createObjectBuilder();
            responseObjectBuilder.add("message", "Successful execution");
            
            StringWriter responseWriter = new StringWriter();
            JsonWriter jsonWriter = jsonWriterFactory.createWriter(responseWriter);
            jsonWriter.write(responseObjectBuilder.build());
            byte[] responseData = responseWriter.toString().getBytes(utf8);
            
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
