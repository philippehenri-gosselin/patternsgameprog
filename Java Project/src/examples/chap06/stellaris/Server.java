/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.stellaris;

import examples.chap06.stellaris.service.GalaxyService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import examples.chap06.stellaris.router.GalaxyRouter;
import examples.chap06.stellaris.router.SystemRouter;
import examples.chap06.stellaris.state.Universe;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

public class Server implements HttpHandler {

    private ServiceManager serviceManager;
    
    private final Charset utf8 = Charset.forName("UTF8");
    
    private final JsonBuilderFactory jsonBuilderFactory = Json.createBuilderFactory(null);
    
    private final JsonWriterFactory jsonWriterFactory;
    
    private final Universe universe = new Universe();
    
    public Server() {
        Map<String,Boolean> options = new HashMap();
        options.put(JsonGenerator.PRETTY_PRINTING, true);
        jsonWriterFactory = Json.createWriterFactory(options);

        serviceManager = new ServiceManager(jsonBuilderFactory);
        serviceManager.addServiceRouter(new SystemRouter(jsonBuilderFactory,universe));
        serviceManager.addServiceRouter(new GalaxyRouter(jsonBuilderFactory,universe));
    }
    
    @Override
    public void handle(HttpExchange exchange) {
        Headers headers = exchange.getResponseHeaders();
        headers.set("Content-Type","application/json; charset=utf-8");
        int httpStatusCode;
        URI uri = exchange.getRequestURI();
        String method = exchange.getRequestMethod();
        ServiceQuery query = new ServiceQuery(method,uri.getPath());
//        System.out.println(query.getMethod()+" "+query.getPath());
        try {
            if (method.equals("POST") || method.equals("PUT")) {
                JsonReader jsonReader = null;
                JsonObject input = null;
                try {
                    InputStream is = exchange.getRequestBody();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is,utf8));
                    jsonReader = Json.createReader(rd);
                    input = jsonReader.readObject();
                }
                finally {
                    if (jsonReader != null)
                        jsonReader.close();
                }
                query.setInput(input);
            }
            httpStatusCode = serviceManager.queryService(query).getCode();            
        }
        catch(ServiceException ex) {
            httpStatusCode = ex.getStatus().getCode();
            JsonObjectBuilder builder = jsonBuilderFactory.createObjectBuilder();
            builder.add("error",ex.getMessage());
            query.setOutput(builder.build());
        }
        catch(Exception ex) {
            httpStatusCode = HttpURLConnection.HTTP_BAD_REQUEST;
            JsonObjectBuilder builder = jsonBuilderFactory.createObjectBuilder();
            if (ex.getMessage() != null) {
                builder.add("error",ex.getMessage());
            }
            else {
                builder.add("error",ex.toString());
            }
            query.setOutput(builder.build());
        }

        JsonObject output = query.getOutput();
        if (output != null) {
            StringWriter responseWriter = new StringWriter();
            JsonWriter jsonWriter = jsonWriterFactory.createWriter(responseWriter);
            jsonWriter.write(output);
            byte[] responseData = responseWriter.toString().getBytes(utf8);
            OutputStream os = null;
            try {
                exchange.sendResponseHeaders(httpStatusCode, responseData.length);
                os = exchange.getResponseBody();
                os.write(responseData);
            }
            catch (IOException ex) {
                System.err.println("Error when sending the response");
            }            
            finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException ex) {
                    System.err.println("Error when sending the response");
                }
            }
        }
        else {
            try {
                exchange.sendResponseHeaders(httpStatusCode, -1);
            } catch (IOException ex) {
                System.err.println("Error when sending the response");
            }
        }
    }
    
    public static void main(String args[]) throws IOException {
        System.setProperty("sun.net.httpserver.nodelay","true");
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        httpServer.createContext("/", new Server());        
        httpServer.start();
    }

}
