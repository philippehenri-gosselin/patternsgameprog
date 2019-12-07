/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.sync;

import pacman.rules.Rules;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RulesThread extends Thread {

    private final Rules rules;

    private volatile boolean running = true;

    private List<RulesThreadObserver> observers = new ArrayList();

    private volatile boolean rollback = false;
    
    private boolean fastMode = false;
    
    public RulesThread(Rules rules) {
        this.rules = rules;
    }
    
    public boolean getRollback() {
        return rollback;
    }
    
    public void setRollback(boolean rollback) {
        this.rollback = rollback;
    }
    
    public void setFastMode(boolean fastMode) {
        this.fastMode = fastMode;
    }
    
    public synchronized void processRules(Consumer<Rules> consumer) {
        consumer.accept(rules);
    }
    
    public void stopRunning() {
        running = false;
    }
    
    public void run() 
    {        
        while(running) {
            update();
        }
        synchronized(this) {
            notifyGameOver();
        }
    }
    
    private long lastUpdate = 0;
    public void update() 
    {
        pacman.state.State state = rules.getState();
        if (!fastMode) {
            long now = System.nanoTime();
            if ( (now - lastUpdate) < state.getEpochDuration())  {
                return;
            }                
            lastUpdate = now;
        }

        synchronized(this) {
            notifyStateUpdating();

            if (rules.getActions().isEmpty()) {
                rollback = false;
            }
            if (rollback) {
                rules.rollback();
            } else {
                rules.addPassiveCommands();
                rules.update();
            }

            notifyStateUpdated();
        }

        if (!fastMode) {
            long elapsed = System.nanoTime() - lastUpdate;
            long milliSleep = (state.getEpochDuration() - elapsed) / 1000000;
            if (milliSleep > 0) {
                try {
                    Thread.sleep(milliSleep);
                } catch (InterruptedException ex) {
                }
            }
        }
    }

    public synchronized void registerObserver(RulesThreadObserver o) {
        observers.add(o);
    }
    
    public synchronized void unregisterObserver(RulesThreadObserver o) {
        observers.remove(o);
    }
    
    private void notifyStateUpdating() {
        for (RulesThreadObserver o : observers) {
            o.stateUpdating(rules);
        }
    }
    
    private void notifyStateUpdated() {
        for (RulesThreadObserver o : observers) {
            o.stateUpdated(rules);
        }
    }
    
    private void notifyGameOver() {
        for (RulesThreadObserver o : observers) {
            o.gameOver(rules);
        }
    }

}
