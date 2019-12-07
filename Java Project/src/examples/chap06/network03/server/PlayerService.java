/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network03.server;

import java.util.List;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class PlayerService extends Service {

    private Game game;
   
    public PlayerService(JsonBuilderFactory jsonBuilderFactory, Game game) {
        super(jsonBuilderFactory);
        this.game = game;
    }

    public Status get(JsonObjectBuilder output, int id) throws ServiceException {
        if (id > 0) {
            Player player = game.getPlayers().get(id-1);
            if (player == null) {
                throw new ServiceException(Status.NOT_FOUND, "Pas de joueur "+id);
            }
            player.toJson(output);
        }
        else {
            List<Player> players = game.getPlayers().getAll();
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
        Player player = new Player();
        player.setValues(input);
        if (!game.getPlayers().set(id-1, player)) {
            throw new ServiceException(Status.NOT_FOUND, "Pas de joueur "+id);
        }
        return Status.NO_CONTENT;
    }

    public Status put(JsonObjectBuilder output, JsonObject input) throws ServiceException {
        if (game.getPlayers().find(input.getString("name")) >= 0) {
            throw new ServiceException(Status.FORBIDDEN, "Le nom est déjà utilisé");
        }
        Player player = new Player();
        player.setValues(input);
        int index = game.getPlayers().add(player);
        if (index < 0) {
            throw new ServiceException(Status.FORBIDDEN, "Plus de place");
        }
        output.add("id",index+1);
        return Status.CREATED;
    }

    public Status delete(int id) throws ServiceException {
        if (!game.getPlayers().remove(id-1)) {
            throw new ServiceException(Status.NOT_FOUND, "Pas de joueur "+id);
        }
        return Status.NO_CONTENT;
    }
    
}
