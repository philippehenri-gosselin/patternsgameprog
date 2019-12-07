/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package pacman;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

public class ServiceQueries {
    
    private String host;
    
    private int port;
    
    private final Charset utf8 = Charset.forName("UTF8");
    
    private boolean simulateDelay = false;
    
    public ServiceQueries(String host,int port) {
        this.host = host;
        this.port = port;
    }
    
    public void setSimulateDelay(boolean b) {
        simulateDelay = b;
    }
    
    public JsonObject get(String serviceName) {
        return get(serviceName,0);
    }
    
    public JsonObject get(String serviceName, int serviceId) {
        if (simulateDelay) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
            }
        }
        JsonReader jsonReader = null;
        try {
            URL url = new URL("http", host, port, serviceName+"/"+serviceId);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);

            int httpStatusCode = connection.getResponseCode();
            if (httpStatusCode == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, utf8));
                jsonReader = Json.createReader(rd);
                return jsonReader.readObject();
            }
        } catch (Exception ex) {
        } finally {
            if (jsonReader != null) {
                jsonReader.close();
            }
        }
        return null;
    }

    public boolean post(String serviceName,JsonObject input,int serviceId) {
        if (simulateDelay) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
            }
        }
        JsonWriter jsonWriter = null;
        try {
            URL url = new URL("http", host, port, serviceName+"/"+serviceId);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            BufferedOutputStream bw = new BufferedOutputStream(connection.getOutputStream());
            jsonWriter = Json.createWriter(bw);
            jsonWriter.write(input);
            jsonWriter.close();
            jsonWriter = null;
            
            int httpStatusCode = connection.getResponseCode();
            if (httpStatusCode == HttpURLConnection.HTTP_NO_CONTENT) {
                return true;
            }
        } catch (Exception ex) {
        } finally {
            if (jsonWriter != null) {
                jsonWriter.close();
            }
        }
        return false;
    }
    
    public JsonObject put(String serviceName,JsonObject input) {
        if (simulateDelay) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
            }
        }
        JsonReader jsonReader = null;
        JsonWriter jsonWriter = null;
        try {
            URL url = new URL("http", host, port, serviceName);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            BufferedOutputStream bw = new BufferedOutputStream(connection.getOutputStream());
            jsonWriter = Json.createWriter(bw);
            jsonWriter.write(input);
            jsonWriter.close();
            jsonWriter = null;

            int httpStatusCode = connection.getResponseCode();
            if (httpStatusCode == HttpURLConnection.HTTP_CREATED) {
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, utf8));
                jsonReader = Json.createReader(rd);
                return jsonReader.readObject();
            }
        } catch (Exception ex) {
        } finally {
            if (jsonWriter != null) {
                jsonWriter.close();
            }
            if (jsonReader != null) {
                jsonReader.close();
            }
        }
        return null;
    }

    public boolean delete(String serviceName,int serviceId) {
        if (simulateDelay) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
            }
        }
        try {
            URL url = new URL("http", host, port, serviceName+"/"+serviceId);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);

            int httpStatusCode = connection.getResponseCode();
            if (httpStatusCode == HttpURLConnection.HTTP_NO_CONTENT) {
                return true;
            }
        } catch (Exception ex) {
        }
        return false;
    }
    
}
