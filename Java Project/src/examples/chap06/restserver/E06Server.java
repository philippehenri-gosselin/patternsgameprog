/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.restserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
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
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

public class E06Server {

    static class User {
        public String name;
        public int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
    }
    
    static class HttpException extends Exception {
        private final int httpStatusCode;
        public HttpException(int httpStatusCode,String message) {
            super(message);
            this.httpStatusCode = httpStatusCode;
        }
        public int getHttpStatusCode() {
            return httpStatusCode;
        }
    }
    
    static class Handler implements HttpHandler {
    
        Charset utf8 = Charset.forName("UTF8");
        
        JsonBuilderFactory jsonBuilderFactory = Json.createBuilderFactory(null);
        
        JsonWriterFactory jsonWriterFactory;
        
        TreeMap<Integer,User> users = new TreeMap();

        Pattern servicePattern = Pattern.compile("(/[^/]+)/(\\d+)");
        
        public Handler() {
            Map<String,Boolean> options = new HashMap();
            options.put(JsonGenerator.PRETTY_PRINTING, true);
            jsonWriterFactory = Json.createWriterFactory(options);
            users.put(1, new User("Stéphane", 25));
            users.put(2, new User("Sophie",24));
        }

        public int getUser(JsonObjectBuilder response,int serviceId) throws HttpException {
            if (serviceId > 0) {
                if (!users.containsKey(serviceId)) {
                    throw new HttpException(HttpURLConnection.HTTP_NOT_FOUND, "User "+serviceId+" not found");
                }
                User user = users.get(serviceId);
                response.add("name", user.name);
                response.add("age", user.age);
            }
            else {
                users.entrySet().forEach((user) -> {
                    JsonObjectBuilder jsonUser = jsonBuilderFactory.createObjectBuilder();
                    jsonUser.add("name", user.getValue().name);
                    jsonUser.add("age", user.getValue().age);
                    response.add(String.valueOf(user.getKey()),jsonUser.build());
                });
            }
            return HttpURLConnection.HTTP_OK;
        }
        
        public int postUser(JsonObject request,int serviceId) throws HttpException {
            if (!users.containsKey(serviceId)) {
                throw new HttpException(HttpURLConnection.HTTP_NOT_FOUND, "User "+serviceId+" not found");
            }
            User user = users.get(serviceId);
            if (request.containsKey("name")) {
                user.name = request.getString("name");
            }
            if (request.containsKey("age")) {
                user.age = request.getInt("age");
            }
            return HttpURLConnection.HTTP_NO_CONTENT;
        }
        
        public JsonObject getRequestObject(HttpExchange exchange) {
            JsonReader jsonReader = null;
            try {
                InputStream is = exchange.getRequestBody();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is,utf8));
                jsonReader = Json.createReader(rd);
                return jsonReader.readObject();
            }
            finally {
                if (jsonReader != null)
                    jsonReader.close();
            }
        }
        
        public void handle(HttpExchange exchange) throws IOException 
        {
            Headers headers = exchange.getResponseHeaders();
            headers.set("Content-Type","application/json; charset=utf-8");
            int httpStatusCode = HttpURLConnection.HTTP_OK;
            JsonObjectBuilder response = null;
            try {
                String method = exchange.getRequestMethod();
                URI uri = exchange.getRequestURI();
                String serviceName = uri.getPath();
                int serviceId = 0;
                
                Matcher matcher = servicePattern.matcher(serviceName);
                if (matcher.matches()) {
                    serviceName = matcher.group(1);
                    serviceId = Integer.parseInt(matcher.group(2));
                }
                else {
                    if (serviceName.endsWith("/")) {
                        serviceName = serviceName.substring(0, serviceName.length() - 1);
                    }
                }
                
                System.out.println("Requête "+method+" "+serviceName+", id:"+serviceId);
                
                if (serviceName.equals("/user")) {
                    switch(method) {
                        case "GET":
                            response = jsonBuilderFactory.createObjectBuilder();
                            httpStatusCode = getUser(response,serviceId);
                            break;
                        case "POST":
                            httpStatusCode = postUser(getRequestObject(exchange),serviceId);
                            break;
                        default:
                           throw new HttpException(HttpURLConnection.HTTP_BAD_METHOD,"Method "+method+" is invalid or unsupported");
                    }
                }
                else {
                    throw new HttpException(HttpURLConnection.HTTP_BAD_REQUEST,"Service "+serviceName+" is invalid or unsupported");
                }
            }
            catch(HttpException ex) {
                httpStatusCode = ex.getHttpStatusCode();
                response = jsonBuilderFactory.createObjectBuilder();
                response.add("error",ex.getMessage());
            }
            catch(Exception ex) {
                httpStatusCode = HttpURLConnection.HTTP_BAD_REQUEST;
                response = jsonBuilderFactory.createObjectBuilder();
                response.add("error",ex.getMessage());
            }

            if (response != null) {
                StringWriter responseWriter = new StringWriter();
                JsonWriter jsonWriter = jsonWriterFactory.createWriter(responseWriter);
                jsonWriter.write(response.build());
                byte[] responseData = responseWriter.toString().getBytes(utf8);
                exchange.sendResponseHeaders(httpStatusCode, responseData.length);
                OutputStream os = exchange.getResponseBody();
                os.write(responseData);
                os.close();
            }
            else {
                exchange.sendResponseHeaders(httpStatusCode, -1);
            }

        }
    }

    public static void main(String args[]) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        httpServer.createContext("/", new Handler());
        httpServer.start();
    }    
}
