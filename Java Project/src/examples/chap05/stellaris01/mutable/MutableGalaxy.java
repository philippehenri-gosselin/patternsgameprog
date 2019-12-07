/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris01.mutable;

import examples.chap05.stellaris01.Connection;
import examples.chap05.stellaris01.Galaxy;
import examples.chap05.stellaris01.System;
import java.util.ArrayList;

public class MutableGalaxy implements Galaxy {

    private ArrayList<MutableSystem> systems;

    private ArrayList<MutableConnection> connections;

    public MutableGalaxy() {
        systems = new ArrayList();
        connections = new ArrayList();
    }

    public MutableSystem createSystem(String name, int x, int y) {
        if (findSystem(name) >= 0) {
            throw new IllegalArgumentException("Le système " + name + " existe déjà");
        }
        MutableSystem system = new MutableSystem(name, x, y);
        systems.add(system);
        return system;
    }

    public MutableConnection connectSystems(String name1, String name2, int parsecs) {
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
        MutableConnection connection = new MutableConnection(parsecs, index1, index2);
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
            MutableConnection connection = connections.get(i);
            if (connection.getSystem1() == index1 && connection.getSystem2() == index2) {
                return i;
            }
        }
        return -1;
    }

    public int getConnectionCount() {
        return connections.size();
    }
    
    public MutableConnection getConnection(int index) {
        if (index < 0 || index >= connections.size()) {
            throw new IllegalArgumentException("Il n'y a pas de connection à l'indice " + index);
        }
        return connections.get(index);
    }
}
