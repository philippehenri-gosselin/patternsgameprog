/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.stellaris.state;

import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class Galaxy {
    
    private final Universe universe;
    
    private final int id;
    
    private String name;

    private ArrayList<System> systems = new ArrayList();

    private ArrayList<Connection> connections = new ArrayList();

    public Galaxy(Universe universe,int id,String name) {
        this.universe = universe;
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int createSystem(String name, int x, int y) {
        if (findSystem(name) >= 0) {
            throw new IllegalArgumentException("Le système " + name + " existe déjà");
        }
        int found = -1;
        for (int i=0;i<systems.size();i++) {
            if (systems.get(i) == null) {
                found = i;
                break;
            }
        }
        if (found >= 0) {
            systems.set(found, new System(this,found,name,x,y));
            return found;
        }
        else {
            int id = systems.size();
            systems.add(new System(this,id,name,x,y));
            return id;
        }        
    }

    public Connection connectSystems(String name1, String name2, int parsecs) {
        int index1 = findSystem(name1);
        if (index1 < 0) {
            throw new IllegalArgumentException("Le système " + name1 + " n'existe pas");
        }
        int index2 = findSystem(name2);
        if (index2 < 0) {
            throw new IllegalArgumentException("Le système " + name2 + " n'existe pas");
        }
        if (index1 == index2) {
            throw new IllegalArgumentException("Vous ne pouvez pas connecter un système avec lui-même");
        }
        if (index1 > index2) {
            int tmp = index1;
            index1 = index2;
            index2 = tmp;
        }
        int index = findConnection(index1, index2);
        if (index >= 0) {
            throw new IllegalArgumentException("Il y a déjà une connection entre le système " + name1 + " et le système " + name2);
        }
        Connection connection = new Connection(parsecs, index1, index2);
        connections.add(connection);
        return connection;
    }

    public int findSystem(String name) {
        for (int i = 0; i < systems.size(); i++) {
            if (systems.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }
    
    public int getSystemCount() {
        return systems.size();
    }

    public System getSystem(int index) {
        if (index < 0 || index >= systems.size()) {
            throw new IllegalArgumentException("Il n'y a pas de système à l'indice " + index);
        }
        return systems.get(index);
    }
    
    public ArrayList<Connection> findLeftConnections(int index) {
        if (index < 0 || index >= systems.size()) {
            throw new IllegalArgumentException("Il n'y a pas de système à l'indice " + index);
        }
        ArrayList<Connection> list = new ArrayList();
        for (int i = 0; i < connections.size(); i++) {
            Connection connection = connections.get(i);
            if (connection.getSystem1() == index) {
                list.add(connection);
            }
        }
        return list;
    }

    public int findConnection(int index1, int index2) {
        if (index1 < 0 || index1 >= systems.size()) {
            throw new IllegalArgumentException("Il n'y a pas de système à l'indice " + index1);
        }
        if (index2 < 0 || index2 >= systems.size()) {
            throw new IllegalArgumentException("Il n'y a pas de système à l'indice " + index2);
        }
        if (index1 > index2) {
            int tmp = index1;
            index1 = index2;
            index2 = tmp;
        }
        for (int i = 0; i < connections.size(); i++) {
            Connection connection = connections.get(i);
            if (connection.getSystem1() == index1 && connection.getSystem2() == index2) {
                return i;
            }
        }
        return -1;
    }
    
    public int getConnectionCount() {
        return connections.size();
    }

    public Connection getConnection(int index) {
        if (index < 0 || index >= connections.size()) {
            throw new IllegalArgumentException("Il n'y a pas de connection à l'indice " + index);
        }
        return connections.get(index);
    }
    
    public String getPath() {
        return "/galaxy/"+(id+1);
    }    
    
    
    public void fromJson(JsonObject json) {
        this.name = json.getString("name");
    }    
    
    public boolean removeSystem(int index) {
        if (index < 0 || index >= systems.size())
            return false;
        System system = systems.get(index);
        if (system == null)
            return false;
        systems.set(index, null);
        return true;
    }    
    
    public void toJson(JsonObjectBuilder output) {
        output.add("name", name);
        
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        buildJsonSystems(objectBuilder);
        output.add("systems",objectBuilder);
    }      
    
    public void buildJsonSystems(JsonObjectBuilder objectBuilder) {
        for (int i=0;i<systems.size();i++) {
            System system = systems.get(i);
            if (system == null)
                continue;
            JsonObjectBuilder jsonSystem = Json.createObjectBuilder();
            jsonSystem.add("name", system.getName());
            jsonSystem.add("x", system.getX());
            jsonSystem.add("y", system.getY());
            jsonSystem.add("planets", system.getPlanetCount());
            jsonSystem.add("path",system.getPath());
            objectBuilder.add(String.valueOf(i+1),jsonSystem);
        }
    }

}
