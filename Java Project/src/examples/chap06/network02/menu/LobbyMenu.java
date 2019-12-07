/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.network02.menu;

import examples.chap06.network02.ServiceQueries;
import examples.chap06.network02.gui.Keyboard;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.json.Json;
import javax.json.JsonObject;

public class LobbyMenu extends MenuGameMode {

    private ServiceQueries serviceQueries;
    
    private String playerName;
    
    private int playerId;
    
    private ScheduledExecutorService updater;
    
    public LobbyMenu(String serverIp, int serverPort, String playerName, int playerId) {
        this.playerName = playerName;
        this.playerId = playerId;
        items.add("Pacman: ?");
        items.add("Blinky: ?");
        items.add("Pinky: ?");
        items.add("Inky: ?");
        items.add("Clyde: ?");
        serviceQueries = new ServiceQueries(serverIp,serverPort);
        updater = Executors.newSingleThreadScheduledExecutor();
        updater.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                updatePlayers();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
    
    public void updatePlayers() {
        JsonObject players = serviceQueries.get("/player");
        if (players != null) {
            updateMenuItem(0,"Pacman",players.getJsonObject("1"));
            updateMenuItem(1,"Blinky",players.getJsonObject("2"));
            updateMenuItem(2,"Pinky",players.getJsonObject("3"));
            updateMenuItem(3,"Inky",players.getJsonObject("4"));
            updateMenuItem(4,"Clyde",players.getJsonObject("5"));
        }
    }
    
    public void updateMenuItem(int index,String charName,JsonObject player) {
        if (player == null || player.getBoolean("ai")) {
            items.set(index, charName+": AI");
        }
        else {
            items.set(index, charName+": "+player.getString("name"));
        }
    }
    
    public void handleInputs() {
        Keyboard keyboard = gui.getKeyboard();
        switch(keyboard.getLastPressedKey()) {
            case KeyEvent.VK_ESCAPE:
                keyboard.consumeLastPressedKey();
                serviceQueries.delete("/player", playerId);
                updater.shutdownNow();
                setPreviousGameMode();
                return;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                keyboard.consumeLastPressedKey();
                if (selectedItem < 5) {
                    JsonObject player = Json.createObjectBuilder().add("name", playerName).build();
                    if (serviceQueries.post("/player", player, selectedItem+1)) {
                        playerId = selectedItem+1;
                        updatePlayers();
                    }
                }                
                return;
        }
        super.handleInputs();
    }

}
