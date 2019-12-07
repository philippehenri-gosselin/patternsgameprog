/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.stellaris.router;

import examples.chap06.stellaris.ServiceQuery;
import examples.chap06.stellaris.service.GalaxyService;
import examples.chap06.stellaris.service.Service;
import examples.chap06.stellaris.state.Universe;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.json.JsonBuilderFactory;

public class GalaxyRouter extends ServiceRouter {
    
    final Universe universe;
    
    public GalaxyRouter(JsonBuilderFactory jsonBuilderFactory,Universe universe) {
        super(jsonBuilderFactory);
        this.universe = universe;
    }

    final Pattern servicePattern = Pattern.compile("/galaxy/(\\d+)");
    
    public Service matchService(ServiceQuery query) {
        String method = query.getMethod();
        String path = query.getPath();
        if (path.equals("/galaxy")) {
            if (method.equals("PUT") || method.equals("GET")) {
                return new GalaxyService(jsonBuilderFactory, universe);
            }
        }
        else {
            Matcher matcher = servicePattern.matcher(path);
            if (matcher.matches()) {
                return new GalaxyService(jsonBuilderFactory, universe);
            }
        }
        return null;
    }
}
