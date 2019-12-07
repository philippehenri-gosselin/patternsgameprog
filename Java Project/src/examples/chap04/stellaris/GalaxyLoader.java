/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap04.stellaris;

import examples.chap02.stellaris.Galaxy;
import examples.chap02.stellaris.Gaseous;
import examples.chap02.stellaris.Habitable;
import examples.chap02.stellaris.Planet;
import examples.chap02.stellaris.System;

public class GalaxyLoader {

    private final String[] data;
    
    private int index;
    
    private PlanetFactory factory = new PlanetFactory();
    
    public GalaxyLoader(String[] data) {
        this.data = data;

        factory.registerCreator("Habitable", new PlanetCreator() {
            @Override
            public Planet create(String name) {
                return new Habitable(name);
            }
        });
        factory.registerCreator("Gaseous", new PlanetCreator() {
            @Override
            public Planet create(String name) {
                return new Gaseous(name);
            }
        });        
    }
    
    public Galaxy load() {
        Galaxy galaxy = new Galaxy();
        index = 0;
        while(index < data.length) {
            if (data[index].equals("End"))
                break;
            if (!data[index].equals("System"))
                throw new RuntimeException("Invalid format");
            index ++;
            if (index >= data.length)
                throw new RuntimeException("Invalid format");
            String systemName = data[index++];
            System system = galaxy.createSystem(systemName,0,0);
            loadSystem(system);
        }        
        return galaxy;
    }
    
    private void loadSystem(System system) {
        while(index < data.length) {
            if (data[index].equals("End"))
                break;
            String planetType = data[index++];
            if (index >= data.length)
                throw new RuntimeException("Invalid format");
            String planetName = data[index++];
            Planet planet = factory.create(planetType,planetName);
            system.addPlanet(planet);
        }
        index ++;
    }    
}
