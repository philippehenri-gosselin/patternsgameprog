/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package pacman.menu;

import pacman.RemoteGameMode;
import pacman.ServiceQueries;
import pacman.gui.Keyboard;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.json.Json;
import javax.json.JsonArray;
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
        items.add("Join the game");
        serviceQueries = new ServiceQueries(serverIp,serverPort);
        updater = Executors.newSingleThreadScheduledExecutor();
        updater.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                updatePlayers();
            }
        }, 0, 5, TimeUnit.SECONDS);
    }
    
    public void updatePlayers() {
        JsonObject players = serviceQueries.get("/player");
        if (players != null) {
            updateMenu(players);
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

    public void updateMenu(JsonObject players) {
        updateMenuItem(0,"Pacman",players.getJsonObject("1"));
        updateMenuItem(1,"Blinky",players.getJsonObject("2"));
        updateMenuItem(2,"Pinky",players.getJsonObject("3"));
        updateMenuItem(3,"Inky",players.getJsonObject("4"));
        updateMenuItem(4,"Clyde",players.getJsonObject("5"));
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
                else {
                    serviceQueries.put("/game", Json.createObjectBuilder().build());
                    if (serviceQueries.get("/game") != null) {
                        JsonObject commands = serviceQueries.get("/command");
                        if (commands != null) {
                            JsonArray scheduledCommands = commands.getJsonArray("commands");
                            if (scheduledCommands != null) {
                                updater.shutdownNow();
                                setGameMode(new RemoteGameMode(scheduledCommands, serviceQueries, playerId));
                            }
                        }
                    }
                }
                return;
        }
        super.handleInputs();
    }
    
}
