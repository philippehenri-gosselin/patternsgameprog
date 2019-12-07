/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.stellaris.router;

import examples.chap06.stellaris.ServiceException;
import examples.chap06.stellaris.ServiceQuery;
import examples.chap06.stellaris.Status;
import examples.chap06.stellaris.service.Service;
import examples.chap06.stellaris.service.SystemService;
import examples.chap06.stellaris.state.Galaxy;
import examples.chap06.stellaris.state.Universe;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.json.JsonBuilderFactory;

public class SystemRouter extends ServiceRouter {

    private final Universe universe;

    public SystemRouter(JsonBuilderFactory jsonBuilderFactory,Universe universe) {
        super(jsonBuilderFactory);
        this.universe = universe;
    }

    final Pattern servicePattern1 = Pattern.compile("/galaxy/(\\d+)/system");
    final Pattern servicePattern2 = Pattern.compile("/galaxy/(\\d+)/system/(\\d+)");
    public Service matchService(ServiceQuery query) throws ServiceException {
        String method = query.getMethod();
        String path = query.getPath();
        Matcher matcher1 = servicePattern1.matcher(path);
        if (matcher1.matches()) {
            if (method.equals("PUT") || method.equals("GET")) {
                int galaxyId = Integer.parseInt(matcher1.group(1));
                Galaxy galaxy = universe.getGalaxy(galaxyId-1);
                if (galaxy == null) {
                    throw new ServiceException(Status.BAD_REQUEST, "Galaxy "+galaxyId+" does not exist.");
                }
                return new SystemService(jsonBuilderFactory, galaxy);
            }
        }
        else {
            Matcher matcher2 = servicePattern2.matcher(path);
            if (matcher2.matches()) {
                int galaxyId = Integer.parseInt(matcher2.group(1));
                Galaxy galaxy = universe.getGalaxy(galaxyId-1);
                if (galaxy == null) {
                    throw new ServiceException(Status.BAD_REQUEST, "Galaxy "+galaxyId+" does not exist.");
                }
                return new SystemService(jsonBuilderFactory, galaxy);
            }
        }
        return null;
    }
}
