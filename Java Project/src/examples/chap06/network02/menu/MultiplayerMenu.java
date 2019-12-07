/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.network02.menu;

import examples.chap06.network02.ServiceQueries;
import examples.chap06.network02.gui.Keyboard;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.json.Json;
import javax.json.JsonObject;
import javax.swing.JOptionPane;

public class MultiplayerMenu extends MenuGameMode {

    private String playerName;
    
    private String serverIp = "localhost";
    
    private int serverPort = 8080;
    
    public MultiplayerMenu() {
        playerName = "Player"+new Random().nextInt(100);
        items.add("Name: "+playerName);
        items.add("Server IP: "+serverIp);
        items.add("Server port: "+serverPort);
        items.add("Connect");
    }
    
    public void handleInputs() {
        Keyboard keyboard = gui.getKeyboard();
        switch(keyboard.getLastPressedKey()) {
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                keyboard.consumeLastPressedKey();
                switch(selectedItem) {
                    case 0: 
                        setGameMode(new InputGameMode(
                            "Enter your name:" , playerName, 
                            (value) -> { 
                                playerName = value;
                                items.set(0, "Name: "+playerName);
                            })
                        );
                        break;
                    case 1:
                        setGameMode(new InputGameMode(
                            "Server address:", serverIp,
                            (value) -> { 
                                serverIp = value; 
                                items.set(1, "Server IP: "+serverIp);
                            })
                        );
                        break;
                    case 2:
                        setGameMode(new InputGameMode(
                            "Server port:", String.valueOf(serverPort),
                            (value) -> { 
                                try {
                                    serverPort = Integer.valueOf(value);
                                }
                                catch(NumberFormatException ex) {
                                }
                                items.set(2, "Server port: "+serverPort);
                            })
                        );
                        break;
                    case 3:
                        JsonObject player = Json.createObjectBuilder().add("name", playerName).build();
                        ServiceQueries serviceQueries = new ServiceQueries(serverIp,serverPort);
                        JsonObject id = serviceQueries.put("/player", player);
                        if (id != null) {
                            int playerId = id.getInt("id");
                            setGameMode(new LobbyMenu(serverIp,serverPort,playerName,playerId));
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "The server is full or does not respond", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                }
                return;
        }
        super.handleInputs();
    }
    
}
