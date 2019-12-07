/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.pacman;

import examples.chap04.features03.gui.GUIFacade;
import examples.chap04.features03.gui.awt.AWTGUIFacade;
import examples.chap04.features03.render.Renderer;
import examples.chap04.features03.rules.Rules;
import examples.chap04.features03.rules.commands.DirectionCommand;
import examples.chap04.features03.state.Characters;
import examples.chap04.features03.state.Direction;
import examples.chap04.features03.state.ElementFactory;
import examples.chap04.features03.state.Ghost;
import examples.chap04.features03.state.GhostStatus;
import examples.chap04.features03.state.Pacman;
import examples.chap04.features03.state.PacmanStatus;
import examples.chap04.features03.state.Space;
import examples.chap04.features03.state.SpaceTypeId;
import examples.chap04.features03.state.State;
import examples.chap04.features03.state.StaticElement;
import examples.chap04.features03.state.World;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;
import org.junit.Test;
import static org.junit.Assert.*;


public class RulesTest {

    private State state;
    
    private boolean enableDisplay = false;
    private GUIFacade gui;
    private Renderer renderer;
    
    private Rules rules;    
    
    static final int[][] level = new int[][] {
        { 15,11,11,11,11,11,11,11,16 },
        { 12,5,3,3,3,3,3,3,12 },
        { 12,3,15,11,11,11,16,3,12 },
        { 14,3,13,11,11,11,14,3,13 },
        { 21,3,3,3,25,24,22,23,3 },
        { 11,11,11,11,11,11,11,11,11 }
    };    

    public void createState() 
    {
        state = new State();
        state.setSuperDuration(20);
        
        World world = new World(9,6);
        world.setFactory(ElementFactory.getDefault());
        world.init(level);
        
        int gumCount = 0;
        for (int j=0;j<world.getHeight();j++) {
            for (int i=0;i<world.getWidth();i++) {
                StaticElement se = world.get(i, j, Direction.NONE);
                if (se instanceof Space) {
                    Space space = (Space)se;
                    if (space.getSpaceTypeId() == SpaceTypeId.GUM
                     || space.getSpaceTypeId() == SpaceTypeId.SUPERGUM) {
                        gumCount ++;
                    }
                }
            }
        }
        state.setGumCount(gumCount);
        
        Characters chars = new Characters();
        chars.init(level);
        
        state.setWorld(world);
        state.setChars(chars);    
    }
    
    public void generateCommands() {
        Characters chars = state.getChars();
        Pacman pacman = chars.getPacman();
        Ghost ghost1 = chars.getGhost(1);
        Ghost ghost2 = chars.getGhost(2);
        if (enableDisplay) {
            System.out.println(" *** Epoch "+state.getEpoch()+" ***");
            System.out.println("Gums: "+state.getGumCount());
            System.out.println("Pacman: ("+pacman.getX()+","+pacman.getY()+") pos:"+pacman.getPosition()
                              +" dir:"+pacman.getDirection()+" status:"+pacman.getStatus()+" time:"+pacman.getStatusTime());
            System.out.println("Ghost 1: ("+ghost1.getX()+","+ghost1.getY()+") pos:"+ghost1.getPosition()
                              +" dir:"+ghost1.getDirection()+" status:"+ghost1.getStatus()+" time:"+ghost1.getStatusTime());
            System.out.println("Ghost 2: ("+ghost2.getX()+","+ghost2.getY()+") pos:"+ghost2.getPosition()
                              +" dir:"+ghost2.getDirection()+" status:"+ghost2.getStatus()+" time:"+ghost2.getStatusTime());
        }
        
switch(state.getEpoch()) {
    case 0:
        assertEquals(15, state.getGumCount());

        assertEquals(PacmanStatus.NORMAL,pacman.getStatus());
        assertEquals(0,pacman.getStatusTime());
        assertEquals(Direction.NONE,pacman.getDirection());
        assertEquals(0,pacman.getPosition());
        assertEquals(0,pacman.getX());
        assertEquals(4,pacman.getY());
        rules.addCommand(0, new DirectionCommand(0, Direction.EAST));
        rules.addCommand(1, new DirectionCommand(1, Direction.WEST));
        rules.addCommand(2, new DirectionCommand(2, Direction.EAST));
        break;
            case 1:
                assertEquals(15, state.getGumCount());

                assertEquals(PacmanStatus.NORMAL,pacman.getStatus());
                assertEquals(0,pacman.getStatusTime());
                assertEquals(Direction.EAST,pacman.getDirection());
                assertEquals(1,pacman.getPosition());
                assertEquals(0,pacman.getX());
                assertEquals(4,pacman.getY());
                break;
            case 2:
                assertEquals(15, state.getGumCount());

                assertEquals(PacmanStatus.NORMAL,pacman.getStatus());
                assertEquals(0,pacman.getStatusTime());
                assertEquals(Direction.EAST,pacman.getDirection());
                assertEquals(-2,pacman.getPosition());
                assertEquals(1,pacman.getX());
                assertEquals(4,pacman.getY());
                break;
            case 3:
                assertEquals(15, state.getGumCount());

                assertEquals(PacmanStatus.NORMAL,pacman.getStatus());
                assertEquals(0,pacman.getStatusTime());
                assertEquals(Direction.EAST,pacman.getDirection());
                assertEquals(-1,pacman.getPosition());
                assertEquals(1,pacman.getX());
                assertEquals(4,pacman.getY());
                break;
            case 4:
                assertEquals(14, state.getGumCount());

                assertEquals(PacmanStatus.NORMAL,pacman.getStatus());
                assertEquals(0,pacman.getStatusTime());
                assertEquals(Direction.EAST,pacman.getDirection());
                assertEquals(0,pacman.getPosition());
                assertEquals(1,pacman.getX());
                assertEquals(4,pacman.getY());
                rules.addCommand(0, new DirectionCommand(0, Direction.NORTH));
                break;
            case 5:
                assertEquals(14, state.getGumCount());

                assertEquals(PacmanStatus.NORMAL,pacman.getStatus());
                assertEquals(0,pacman.getStatusTime());
                assertEquals(Direction.NORTH,pacman.getDirection());
                assertEquals(-1,pacman.getPosition());
                assertEquals(1,pacman.getX());
                assertEquals(4,pacman.getY());
                break;
                
            case 6:
                assertEquals(14, state.getGumCount());

                assertEquals(PacmanStatus.NORMAL,pacman.getStatus());
                assertEquals(0,pacman.getStatusTime());
                assertEquals(Direction.NORTH,pacman.getDirection());
                assertEquals(-2,pacman.getPosition());
                assertEquals(1,pacman.getX());
                assertEquals(4,pacman.getY());
                break;

            case 7:
                assertEquals(14, state.getGumCount());

                assertEquals(PacmanStatus.NORMAL,pacman.getStatus());
                assertEquals(0,pacman.getStatusTime());
                assertEquals(Direction.NORTH,pacman.getDirection());
                assertEquals(1,pacman.getPosition());
                assertEquals(1,pacman.getX());
                assertEquals(3,pacman.getY());
                break;
                
            case 8:
                assertEquals(13, state.getGumCount());

                assertEquals(PacmanStatus.NORMAL,pacman.getStatus());
                assertEquals(0,pacman.getStatusTime());
                assertEquals(Direction.NORTH,pacman.getDirection());
                assertEquals(0,pacman.getPosition());
                assertEquals(1,pacman.getX());
                assertEquals(3,pacman.getY());
                rules.addCommand(2, new DirectionCommand(2, Direction.NORTH));
                break;
                
            case 9:
                assertEquals(13, state.getGumCount());

                assertEquals(PacmanStatus.NORMAL,pacman.getStatus());
                assertEquals(0,pacman.getStatusTime());
                assertEquals(Direction.NORTH,pacman.getDirection());
                assertEquals(-1,pacman.getPosition());
                assertEquals(1,pacman.getX());
                assertEquals(3,pacman.getY());
                break;
                
            case 10:
                assertEquals(13, state.getGumCount());

                assertEquals(PacmanStatus.NORMAL,pacman.getStatus());
                assertEquals(0,pacman.getStatusTime());
                assertEquals(Direction.NORTH,pacman.getDirection());
                assertEquals(-2,pacman.getPosition());
                assertEquals(1,pacman.getX());
                assertEquals(3,pacman.getY());
                break;
                
            case 11:
                assertEquals(13, state.getGumCount());

                assertEquals(PacmanStatus.NORMAL,pacman.getStatus());
                assertEquals(0,pacman.getStatusTime());
                assertEquals(Direction.NORTH,pacman.getDirection());
                assertEquals(1,pacman.getPosition());
                assertEquals(1,pacman.getX());
                assertEquals(2,pacman.getY());
                break;
                
            case 12:
                assertEquals(12, state.getGumCount());

                assertEquals(PacmanStatus.NORMAL,pacman.getStatus());
                assertEquals(0,pacman.getStatusTime());
                assertEquals(Direction.NORTH,pacman.getDirection());
                assertEquals(0,pacman.getPosition());
                assertEquals(1,pacman.getX());
                assertEquals(2,pacman.getY());
                rules.addCommand(1, new DirectionCommand(1, Direction.NORTH));
                break;
                
            case 13:
                assertEquals(12, state.getGumCount());

                assertEquals(PacmanStatus.NORMAL,pacman.getStatus());
                assertEquals(0,pacman.getStatusTime());
                assertEquals(Direction.NORTH,pacman.getDirection());
                assertEquals(-1,pacman.getPosition());
                assertEquals(1,pacman.getX());
                assertEquals(2,pacman.getY());
                break;
                
            case 14:
                assertEquals(12, state.getGumCount());

                assertEquals(PacmanStatus.NORMAL,pacman.getStatus());
                assertEquals(0,pacman.getStatusTime());
                assertEquals(Direction.NORTH,pacman.getDirection());
                assertEquals(-2,pacman.getPosition());
                assertEquals(1,pacman.getX());
                assertEquals(2,pacman.getY());
                break;
                
            case 15:
                assertEquals(12, state.getGumCount());

                assertEquals(PacmanStatus.NORMAL,pacman.getStatus());
                assertEquals(0,pacman.getStatusTime());
                assertEquals(Direction.NORTH,pacman.getDirection());
                assertEquals(1,pacman.getPosition());
                assertEquals(1,pacman.getX());
                assertEquals(1,pacman.getY());
                break;
                
            case 16:
                assertEquals(11, state.getGumCount());

                assertEquals(GhostStatus.FLEE,ghost1.getStatus());
                assertEquals(19,ghost1.getStatusTime());
                assertEquals(GhostStatus.FLEE,ghost2.getStatus());
                assertEquals(19,ghost2.getStatusTime());

                assertEquals(PacmanStatus.SUPER,pacman.getStatus());
                assertEquals(19,pacman.getStatusTime());
                assertEquals(Direction.NORTH,pacman.getDirection());
                assertEquals(0,pacman.getPosition());
                assertEquals(1,pacman.getX());
                assertEquals(1,pacman.getY());
                rules.addCommand(0, new DirectionCommand(0, Direction.SOUTH));
                break;     
                
            case 17:
                assertEquals(11, state.getGumCount());

                assertEquals(GhostStatus.FLEE,ghost1.getStatus());
                assertEquals(18,ghost1.getStatusTime());
                assertEquals(GhostStatus.FLEE,ghost2.getStatus());
                assertEquals(18,ghost2.getStatusTime());
                
                assertEquals(PacmanStatus.SUPER,pacman.getStatus());
                assertEquals(18,pacman.getStatusTime());
                assertEquals(Direction.SOUTH,pacman.getDirection());
                assertEquals(1,pacman.getPosition());
                assertEquals(1,pacman.getX());
                assertEquals(1,pacman.getY());
                break;                

            case 18:
                assertEquals(PacmanStatus.SUPER,pacman.getStatus());
                assertEquals(GhostStatus.FLEE,ghost1.getStatus());
                assertEquals(GhostStatus.FLEE,ghost2.getStatus());
                break;

            case 19:                
                assertEquals(GhostStatus.EYES,ghost1.getStatus());
                assertEquals(0,ghost1.getStatusTime());
                assertEquals(GhostStatus.FLEE,ghost2.getStatus());
                assertEquals(16,ghost2.getStatusTime());
                assertEquals(PacmanStatus.SUPER,pacman.getStatus());
                assertEquals(16,pacman.getStatusTime());
                break;

            case 20:
                rules.addCommand(2, new DirectionCommand(2, Direction.WEST));
                break;

            case 24:
                rules.addCommand(0, new DirectionCommand(0, Direction.EAST));
                rules.addCommand(1, new DirectionCommand(1, Direction.EAST));
                break;
                
            case 34:
                assertEquals(GhostStatus.EYES,ghost1.getStatus());
                assertEquals(0,ghost1.getStatusTime());
                assertEquals(GhostStatus.FLEE,ghost2.getStatus());
                assertEquals(1,ghost2.getStatusTime());
                assertEquals(PacmanStatus.SUPER,pacman.getStatus());
                assertEquals(1,pacman.getStatusTime());
                break;

            case 35:
                assertEquals(GhostStatus.EYES,ghost1.getStatus());
                assertEquals(0,ghost1.getStatusTime());
                assertEquals(GhostStatus.TRACK,ghost2.getStatus());
                assertEquals(0,ghost2.getStatusTime());
                assertEquals(PacmanStatus.NORMAL,pacman.getStatus());
                assertEquals(0,pacman.getStatusTime());
                break;
                
            case 44:
                rules.addCommand(2, new DirectionCommand(2, Direction.SOUTH));
                break;

            case 48:
                rules.addCommand(1, new DirectionCommand(1, Direction.SOUTH));
                break;

            case 53:
                assertEquals(GhostStatus.EYES,ghost1.getStatus());
                assertEquals(0,ghost1.getStatusTime());
                assertEquals(GhostStatus.TRACK,ghost2.getStatus());
                assertEquals(0,ghost2.getStatusTime());
                assertEquals(PacmanStatus.NORMAL,pacman.getStatus());
                assertEquals(0,pacman.getStatusTime());
                break;
                
            case 54:
                assertEquals(GhostStatus.EYES,ghost1.getStatus());
                assertEquals(0,ghost1.getStatusTime());
                assertEquals(GhostStatus.TRACK,ghost2.getStatus());
                assertEquals(0,ghost2.getStatusTime());
                assertEquals(PacmanStatus.DEAD,pacman.getStatus());
                assertEquals(12,pacman.getStatusTime());
                break;
                
            case 59:
                assertEquals(GhostStatus.EYES,ghost1.getStatus());
                assertEquals(0,ghost1.getStatusTime());
                assertEquals(GhostStatus.TRACK,ghost2.getStatus());
                assertEquals(0,ghost2.getStatusTime());
                assertEquals(PacmanStatus.DEAD,pacman.getStatus());
                assertEquals(7,pacman.getStatusTime());
                break;
                
            case 60:
                assertEquals(GhostStatus.TRACK,ghost1.getStatus());
                assertEquals(0,ghost1.getStatusTime());
                assertEquals(GhostStatus.TRACK,ghost2.getStatus());
                assertEquals(0,ghost2.getStatusTime());
                assertEquals(PacmanStatus.DEAD,pacman.getStatus());
                assertEquals(6,pacman.getStatusTime());
                break;
                
            case 66:
                assertEquals(PacmanStatus.DEAD,pacman.getStatus());
                assertEquals(0,pacman.getStatusTime());
                rollback = true;
                break;
        }
        
    }
    
    private Queue<State> states = Collections.asLifoQueue(new ArrayDeque()); 
    private boolean rollback = false;
    private long lastUpdate;
    public void update() {
        long now = System.nanoTime();

        if ( (now - lastUpdate) >= state.getEpochDuration()) 
        {
            lastUpdate = now;

            if (rules.getActions().isEmpty()) {
                rollback = false;
            }
            
            if (rollback) {
                rules.rollback();
                assertEquals(state,states.poll());
            }
            else {
                states.add(state.clone());
                generateCommands();
                rules.addPassiveCommands();
                rules.update();
                
                state.save("save.ser");
                State savedState = State.load("save.ser");
                assertEquals(state,savedState);
            }
        }
    }    
    
    private int lastEpoch = -1;
    private long beginEpoch = 0;
    public void render(long time) {
        if (state.getEpoch() != lastEpoch) {
            lastEpoch = state.getEpoch();
            beginEpoch = System.nanoTime();
        }
        renderer.render(beginEpoch,time,beginEpoch+state.getEpochDuration(),state);
    }    
    
    public void run() 
    {
        int fps = enableDisplay?60:100000;
        long nanoPerFrame = (long) (1000000000.0 / fps);
        long lastTime = 0;
        do {
            long nowTime = System.nanoTime();
            if ((nowTime - lastTime) < nanoPerFrame) {
                continue;
            }
            lastTime = nowTime;

            if (enableDisplay) {
                render(nowTime);            
            }
            update();
            
            if (enableDisplay) {
                long elapsed = System.nanoTime() - lastTime;
                long milliSleep = (nanoPerFrame - elapsed) / 1000000;
                if (milliSleep > 0) {
                    try {
                        Thread.sleep(milliSleep);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        while (state.getEpoch() > 0);
        if (enableDisplay) {
            gui.dispose();
        }
    }    
    
    @Test
    public void test() {
        createState();
        
        if (enableDisplay) {
            gui = new AWTGUIFacade();
            renderer = new Renderer(gui);
            state.registerObserver(renderer);
            state.notityStateChanged();
        }
        else {
            state.setEpochRate(10000);
        }
        
        rules = new Rules(state);
    
        run();
    }
    
}
