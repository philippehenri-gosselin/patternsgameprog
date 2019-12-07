/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import org.junit.Test;
import static org.junit.Assert.*;

public class ServerTest {
    
    public ServerTest() {
    }

    @Test
    public void testSomeMethod() throws IOException {
        HttpServer httpServer = null;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
            httpServer.createContext("/", new Server());        
            httpServer.start();
        
            ServiceQueries serviceQueries = new ServiceQueries("localhost",8080);

            JsonObject output;
            JsonObjectBuilder input;
            
            output = serviceQueries.get("/version");
            assertNotNull(output);
            assertEquals(1,output.getInt("major"));
            assertEquals(0,output.getInt("minor"));
            
            output = serviceQueries.get("/player");
            assertNotNull(output);
            assertEquals(0,output.size());
            
            input = Json.createObjectBuilder();
            input.add("name","Paul");
            input.add("ai",false);
            output = serviceQueries.put("/player",input.build());
            assertNotNull(output);
            int id1 = output.getInt("id");
            
            output = serviceQueries.get("/player",id1);
            assertNotNull(output);
            assertEquals("Paul",output.getString("name"));
            assertEquals(false,output.getBoolean("ai"));

            output = serviceQueries.get("/player");
            assertNotNull(output);
            assertEquals(1,output.size());
            output = output.getJsonObject(String.valueOf(id1));
            assertEquals("Paul",output.getString("name"));
            assertEquals(false,output.getBoolean("ai"));
            
            input = Json.createObjectBuilder();
            input.add("name","Sophie");
            input.add("ai",true);
            assertTrue( serviceQueries.post("/player",input.build(),id1) );
            
            output = serviceQueries.get("/player",id1);
            assertNotNull(output);
            assertEquals("Sophie",output.getString("name"));
            assertEquals(true,output.getBoolean("ai"));
            
            assertTrue( serviceQueries.delete("/player",id1) );
            assertNull( serviceQueries.get("/player",id1) );
            
            output = serviceQueries.get("/player");
            assertNotNull(output);
            assertEquals(0,output.size());
            
        }
        finally {
            if (httpServer != null) {
                httpServer.stop(0);
            }
        }
    }
    
}
