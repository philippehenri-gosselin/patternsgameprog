/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network02.server;

import java.util.ArrayList;
import java.util.List;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class PlayerService extends Service {

    private List<Player> players = new ArrayList();
   
    public PlayerService(JsonBuilderFactory jsonBuilderFactory) {
        super(jsonBuilderFactory);
        for (int i=0;i<5;i++) {
            players.add(null);
        }    
    }

    public Status get(JsonObjectBuilder output, int id) throws ServiceException {
        if (id > 0) {
            id --;
            if (id >= players.size() || players.get(id) == null) {
                throw new ServiceException(Status.NOT_FOUND, "Pas de joueur "+id);
            }
            Player player = players.get(id);
            player.toJson(output);
        }
        else {
            for (int i=0;i<players.size();i++) {
                Player player = players.get(i);
                if  (player != null) {
                    JsonObjectBuilder builder = jsonBuilderFactory.createObjectBuilder();
                    player.toJson(builder);
                    output.add(String.valueOf(i+1), builder.build());
                }
            }
        }
        return Status.OK;
    }

    public Status post(JsonObject input, int id) throws ServiceException {
        id --;
        if (id < 0 || id >= players.size()) {
            throw new ServiceException(Status.NOT_FOUND, "Pas de joueur "+id);
        }
        int playerIndex = findPlayer(input.getString("name"));
        Player player = players.get(id);
        if (player != null) {
            if (playerIndex >= 0) {
                Player otherPlayer = players.get(playerIndex);
                players.set(playerIndex, player);
                players.set(id, otherPlayer);
                player = otherPlayer;
            }
            player.setValues(input);
        }
        else {
            player = new Player();
            player.setValues(input);
            players.set(id, player);
            if (playerIndex >= 0) {
                players.set(playerIndex, null);
            }
        }
        return Status.NO_CONTENT;
    }

    public Status put(JsonObjectBuilder output, JsonObject input) throws ServiceException {
        int found = -1;
        for (int i=0;i<players.size();i++) {
            if (players.get(i) == null) {
                found = i;
                break;
            }
        }
        if (found < 0) {
            throw new ServiceException(Status.FORBIDDEN, "Plus de place");
        }
        if (findPlayer(input.getString("name")) >= 0) {
            throw new ServiceException(Status.FORBIDDEN, "Le nom est déjà utilisé");
        }
        Player player = new Player();
        player.setValues(input);
        players.set(found, player);
        output.add("id",found+1);
        return Status.CREATED;
    }

    public Status delete(int id) throws ServiceException {
        id --;
        if (id < 0 || id >= players.size()) {
            throw new ServiceException(Status.NOT_FOUND, "Pas de joueur "+id);
        }
        players.set(id, null);
        return Status.NO_CONTENT;
    }


    private int findPlayer(String playerName) {
        for (int i=0;i<players.size();i++) {
            Player player = players.get(i);
            if (player != null && player.getName().equals(playerName)) {
                return i;
            }
        }
        return -1;
    }
    
}
