/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.ai;

import pacman.ai.mapproviders.CharMapMaintainer;
import pacman.ai.mapproviders.DistanceMapProvider;
import pacman.ai.mapproviders.GhostsMapMaintainer;
import pacman.ai.mapproviders.GraveyardMapMaintainer;
import pacman.ai.mapproviders.GumsMapMaintainer;
import pacman.gui.GUIFacade;
import pacman.gui.Keyboard;
import pacman.gui.awt.AWTGUIFacade;
import pacman.render.Renderer;
import pacman.rules.Rules;
import pacman.rules.commands.Command;
import pacman.rules.commands.LoadLevelCommand;
import pacman.state.Characters;
import pacman.state.GhostStatus;
import pacman.state.Pacman;
import pacman.state.PacmanStatus;
import pacman.state.State;
import pacman.state.mutable.MutableState;
import pacman.mt.TaskManager;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JOptionPane;

public class AIEval {
    private State state;
    
    private String strategy = "Random";
//    private String strategy = "Simple";
//    private String strategy = "Behaviour";
//    private String strategy = "Minimax";
//    private String strategy = "ParallelMinimax";
    private int ghostSpeed = 2;
    private boolean enableDisplay = false;
    private int updatesPerFrame = 120;
           
    
    private GUIFacade gui;
    private Renderer renderer;
    private DistanceMapProvider dmProvider;
    private AI[] ais = new AI[5];    
    private Rules rules;  
    private int victoryCount,gameCount,maxGameCount=1000;
    private int victoryEpochCount,lostEpochCount;
    private int finalGumCount;
    private boolean rollback = false;

    public void update() {
        if (rules.getActions().isEmpty()) {
            rollback = false;
        }

        if (rollback) {
            Keyboard keyboard = gui.getKeyboard();
            int keyCode = keyboard.getLastPressedKey();
            if (keyCode == KeyEvent.VK_SPACE) {
                keyboard.consumeLastPressedKey();
                rules.rollback();                
            }
            else if (keyCode == KeyEvent.VK_BACK_SPACE) {
                keyboard.consumeLastPressedKey();
                rollback = false;
            }
        }
        else
        {
            for(int charIndex=0;charIndex<5;charIndex++) {
                Command command = ais[charIndex].createCommand();
                if (command != null) {
                    rules.addCommand(charIndex, command);
                }
            }
            rules.addPassiveCommands();
            rules.update();
            
            boolean gameEnd = false;
            if (state.getGumCount() == 0) {
                victoryCount ++;
                victoryEpochCount += state.getEpoch();
                gameEnd = true;
            }
            Characters chars = state.getChars();
            Pacman pacman = chars.getPacman();
            if (pacman != null && pacman.getStatus() == PacmanStatus.DEAD) {
                if (enableDisplay) {
                    updatesPerFrame = 1;
                    rollback = true;
                }
                else {
                    gameEnd = true;
                    lostEpochCount += state.getEpoch();
                }
            }
            if (gameEnd) {
                gameCount ++;
                finalGumCount += state.getGumCount();
                double percentVictory = 100*victoryCount;
                percentVictory /= gameCount;
                double averageVictoryEpochCount = victoryEpochCount;
                if (victoryCount > 0) averageVictoryEpochCount /= victoryCount;
                double averageLostEpochCount = lostEpochCount;
                double averageFinalGumCount = finalGumCount;
                if ((gameCount - victoryCount) > 0) {
                    averageLostEpochCount /= gameCount - victoryCount;
                    averageFinalGumCount /= gameCount - victoryCount;
                }
                System.out.println(victoryCount+"/"+gameCount+" ("+String.format("%.0f", percentVictory)+"%)"
                        +", temps moyen vitoire="+String.format("%.0f", averageVictoryEpochCount)
                        +", temps moyen défaite="+String.format("%.0f", averageLostEpochCount)
                        +", nombre moyen Gums="+String.format("%.0f", averageFinalGumCount)
                        );
                rules.clearActions();
                rules.addCommand(0, new LoadLevelCommand("level.tmx", 2, ghostSpeed));
                rules.update();
                state.setEpoch(0);
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
        renderer.render(beginEpoch,time,time);
    }    
    
    public void run() 
    {
        int fps = enableDisplay?60:100000;
        long nanoPerFrame = (long) (1000000000.0 / fps);
        long lastTime = 0;
        while(gameCount < maxGameCount) {
            long nowTime = System.nanoTime();
            if ((nowTime - lastTime) < nanoPerFrame) {
                continue;
            }
            lastTime = nowTime;

            if (enableDisplay) {
                render(nowTime);            
            }
            for (int repeat=0;repeat<updatesPerFrame;repeat++)
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
        if (enableDisplay) {
            gui.dispose();
        }
    }    
    
    public void eval() {
        state = new MutableState();
        
        if (enableDisplay) {
            gui = new AWTGUIFacade();
            renderer = new Renderer(gui);
            state.registerObserver(renderer);
        }
        
        Random random = new Random();
        CommandsLister commandsLister = new DefaultCommandsLister();
        if (strategy.equals("Random")) {
            for(int charIndex=0;charIndex<5;charIndex++) {
                ais[charIndex] = new RandomAI(state,charIndex,commandsLister,random);
            }        
        }
        else if (strategy.equals("Simple")) {
            ais[0] = new ExplorationAI(state,0,commandsLister,random);
            for(int charIndex=1;charIndex<5;charIndex++) {
                ais[charIndex] = new RandomAI(state,charIndex,commandsLister,random);
            }        
        }
        else if (strategy.equals("Behaviour")) {
            dmProvider = new DistanceMapProvider();
            dmProvider.registerMaintainer("Pacman", new CharMapMaintainer(0));
            dmProvider.registerMaintainer("TrackingGhosts", new GhostsMapMaintainer(GhostStatus.TRACK));
            dmProvider.registerMaintainer("FleeingGhosts", new GhostsMapMaintainer(GhostStatus.FLEE));
            dmProvider.registerMaintainer("Graveyard", new GraveyardMapMaintainer());
            dmProvider.registerMaintainer("Gums", new GumsMapMaintainer());
            state.registerObserver(dmProvider);

            ais[0] = new PacmanAI(state,commandsLister,random,dmProvider);
            for(int charIndex=1;charIndex<5;charIndex++) {
                ais[charIndex] = new GhostAI(state,charIndex,commandsLister,random,dmProvider);
            }            
        }
        else if (strategy.equals("Minimax")) {
            dmProvider = new DistanceMapProvider();
            dmProvider.registerMaintainer("Pacman", new CharMapMaintainer(0));
            dmProvider.registerMaintainer("TrackingGhosts", new GhostsMapMaintainer(GhostStatus.TRACK));
            dmProvider.registerMaintainer("FleeingGhosts", new GhostsMapMaintainer(GhostStatus.FLEE));
            dmProvider.registerMaintainer("Graveyard", new GraveyardMapMaintainer());
            dmProvider.registerMaintainer("Gums", new GumsMapMaintainer());
            state.registerObserver(dmProvider);

            ais[0] = new MinimaxAI(state,commandsLister,random,dmProvider);
            for(int charIndex=1;charIndex<5;charIndex++) {
                ais[charIndex] = new GhostAI(state,charIndex,commandsLister,random,dmProvider);
            }            
        }
        else if (strategy.equals("ParallelMinimax")) {
            dmProvider = new DistanceMapProvider();
            dmProvider.registerMaintainer("Pacman", new CharMapMaintainer(0));
            dmProvider.registerMaintainer("TrackingGhosts", new GhostsMapMaintainer(GhostStatus.TRACK));
            dmProvider.registerMaintainer("FleeingGhosts", new GhostsMapMaintainer(GhostStatus.FLEE));
            dmProvider.registerMaintainer("Graveyard", new GraveyardMapMaintainer());
            dmProvider.registerMaintainer("Gums", new GumsMapMaintainer());
            state.registerObserver(dmProvider);

            ais[0] = new ParallelMinimaxAI(state,commandsLister,random,dmProvider);
            for(int charIndex=1;charIndex<5;charIndex++) {
                ais[charIndex] = new GhostAI(state,charIndex,commandsLister,random,dmProvider);
            }            
        }
        else {
            JOptionPane.showMessageDialog(null, "Strategie invalide", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        rules = new Rules(state);
        if (!enableDisplay) {
            rules.setActionsRecording(false);
        }
        rules.addCommand(0, new LoadLevelCommand("level.tmx", 2, ghostSpeed));
        rules.update();
    
        run();
    }
    
    public static void main(String[] args) {
        TaskManager.getInstance().launch();
        new AIEval().eval();
        TaskManager.getInstance().terminate();
    }
        
}
