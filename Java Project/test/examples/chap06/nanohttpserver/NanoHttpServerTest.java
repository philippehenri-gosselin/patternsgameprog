/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.nanohttpserver;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import pacman.mt.TaskManager;

public class NanoHttpServerTest {
    
    static final Charset utf8 = Charset.forName("UTF8");
    
    @Test
    public void test() throws Exception {
        TaskManager taskManager = TaskManager.getInstance();
        taskManager.launch();
        NanoHttpServer server = new NanoHttpServer(8080);
        server.start();
        
        // GET
        URL url = new URL("http","localhost",8080,"/testget");
        HttpURLConnection connection = null;
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setUseCaches(false);
        
        assertEquals(HttpURLConnection.HTTP_OK,connection.getResponseCode());
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is,utf8));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            builder.append(line);
            builder.append("\n");
        }
        rd.close();        
        assertEquals("Test GET passed\n", builder.toString());

        // POST
        url = new URL("http","localhost",8080,"/testpost");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setUseCaches(false);
        connection.setDoOutput(true);

        PrintWriter pw = new PrintWriter(connection.getOutputStream());
        pw.print("x=1,y=2");
        pw.close();

        assertEquals(HttpURLConnection.HTTP_NO_CONTENT,connection.getResponseCode());

        // Shutdown
        server.terminate();
        taskManager.terminate();        
    }
    
}
