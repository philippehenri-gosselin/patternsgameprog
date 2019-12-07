/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.stellaris;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import static org.junit.Assert.*;
import org.junit.Test;

public class ServerTest {

    @Test
    public void test() throws IOException {
        HttpServer httpServer = null;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
            httpServer.createContext("/", new Server());        
            httpServer.start();
        
            ServiceQueries serviceQueries = new ServiceQueries("localhost",8080);

            JsonObject output;
            JsonObjectBuilder input;
            JsonArray array;
            
            // PUT galaxy
            
            input = Json.createObjectBuilder();
            input.add("name", "Voie Lactée");
            output = serviceQueries.put("/galaxy", input.build());
            assertNotNull(output);
            int galaxy1 = output.getInt("id");
            String galaxy1path = output.getString("path");
            
            input = Json.createObjectBuilder();
            input.add("name", "Leo");
            output = serviceQueries.put("/galaxy", input.build());
            assertNotNull(output);
            int galaxy2 = output.getInt("id");
            String galaxy2path = output.getString("path");

            input = Json.createObjectBuilder();
            input.add("name", "Andromeda");
            output = serviceQueries.put("/galaxy", input.build());
            assertNotNull(output);
            int galaxy3 = output.getInt("id");
            String galaxy3path = output.getString("path");
         
            // GET galaxy
            
            output = serviceQueries.get("/galaxy");
            assertEquals(3,output.size());
            assertEquals("Voie Lactée",output.get(String.valueOf(galaxy1)).asJsonObject().getString("name"));
            assertEquals("Leo",output.get(String.valueOf(galaxy2)).asJsonObject().getString("name"));
            assertEquals("Andromeda",output.get(String.valueOf(galaxy3)).asJsonObject().getString("name"));
            
            output = serviceQueries.get(galaxy1path);
            assertEquals("Voie Lactée",output.getString("name"));
            output = serviceQueries.get(galaxy2path);
            assertEquals("Leo",output.getString("name"));
            output = serviceQueries.get(galaxy3path);
            assertEquals("Andromeda",output.getString("name"));
            
            // POST Galaxy
            
            input = Json.createObjectBuilder();
            input.add("name", "Andromède");
            assertTrue(serviceQueries.post(galaxy3path, input.build()));

            output = serviceQueries.get(galaxy3path);
            assertEquals("Andromède",output.getString("name"));
            
            // DELETE Galaxy
            assertTrue(serviceQueries.delete(galaxy2path));

            assertNotNull(serviceQueries.get(galaxy1path));
            assertNull(serviceQueries.get(galaxy2path));
            assertNotNull(serviceQueries.get(galaxy3path));
            
            assertEquals(2,serviceQueries.get("/galaxy").size());
            
            // PUT System
            input = Json.createObjectBuilder();
            input.add("name", "Solaire");
            input.add("x",10);
            input.add("y",50);
            output = serviceQueries.put(galaxy1path+"/system", input.build());
            assertNotNull(output);
            int system1id = output.getInt("id");
            String system1path = output.getString("path");
            
            input = Json.createObjectBuilder();
            input.add("name", "Alpha Centauri");
            input.add("x",100);
            input.add("y",-40);
            output = serviceQueries.put(galaxy1path+"/system", input.build());
            assertNotNull(output);
            int system2id = output.getInt("id");
            String system2path = output.getString("path");
            
            input = Json.createObjectBuilder();
            input.add("name", "Alpha Centauri B");
            input.add("x",200);
            input.add("y",30);
            output = serviceQueries.put(galaxy1path+"/system", input.build());
            assertNotNull(output);
            int system3id = output.getInt("id");
            String system3path = output.getString("path");
            
            // Get System
            output = serviceQueries.get(galaxy1path+"/system");
            assertEquals(3,output.size());
            assertEquals("Solaire",output.get(String.valueOf(system1id)).asJsonObject().getString("name"));
            assertEquals("Alpha Centauri",output.get(String.valueOf(system2id)).asJsonObject().getString("name"));
            
            output = serviceQueries.get(system1path);
            assertEquals("Solaire",output.getString("name"));
            assertEquals(10,output.getInt("x"));
            assertEquals(50,output.getInt("y"));
            
            output = serviceQueries.get(system2path);
            assertEquals("Alpha Centauri",output.getString("name"));
            assertEquals(100,output.getInt("x"));
            assertEquals(-40,output.getInt("y"));
            
            // POST System
            input = Json.createObjectBuilder();
            input.add("name", "Alpha Centauri C");
            input.add("x",110);
            input.add("y",-30);
            assertTrue(serviceQueries.post(system2path, input.build()));
            
            output = serviceQueries.get(system2path);
            assertEquals("Alpha Centauri C",output.getString("name"));
            assertEquals(110,output.getInt("x"));
            assertEquals(-30,output.getInt("y"));
            
            // DELETE System
            assertTrue(serviceQueries.delete(system2path));

            assertNotNull(serviceQueries.get(system1path));
            assertNull(serviceQueries.get(system2path));
            assertNotNull(serviceQueries.get(system3path));

            assertEquals(2,serviceQueries.get(galaxy1path+"/system").size());
            
            
/*            Map<String,Boolean> options = new HashMap();
            options.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(options);
            writerFactory.createWriter(System.out).write(serviceQueries.get("/galaxy/1/system/1"));*/
            
        }
        finally {
            if (httpServer != null) {
                httpServer.stop(0);
            }
        }        
    }
    
}
