/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.restserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class E05Client 
{
    static Charset utf8 = Charset.forName("UTF8");

    public static JsonObject getUser(int id)
    {
        JsonReader jsonReader = null;
        JsonObject jsonObject = null;
        try {
            URL url = new URL("http","localhost",8080,"/user/"+id);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);

            int httpStatusCode = connection.getResponseCode();
            if (httpStatusCode == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is,utf8));
                jsonReader = Json.createReader(rd);
                jsonObject = jsonReader.readObject();
            }
        }
        catch(Exception ex) {
            
        }
        finally {
            if (jsonReader != null)
                jsonReader.close();
        }
        return jsonObject;
    }

    public static void main(String args[]) throws MalformedURLException, IOException 
    {
        System.out.println("Request user 1 data");
        JsonObject user1 = getUser(1);
        System.out.println(" * Name: "+user1.getString("name")+", age: "+user1.getInt("age"));
        
        System.out.println("Request all users data");
        JsonObject users = getUser(0);
        users.entrySet().forEach((pair) -> {
            JsonObject user = pair.getValue().asJsonObject();
            System.out.println(" * Name: "+user.getString("name")+", age: "+user.getInt("age"));
        });

        System.out.println("Request an invalid user");
        JsonObject nullUser = getUser(3);
        if (nullUser == null)
            System.out.println(" * No user returned");
    }
    
}
