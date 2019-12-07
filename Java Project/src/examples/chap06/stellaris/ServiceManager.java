/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.stellaris;

import examples.chap06.stellaris.router.ServiceRouter;
import examples.chap06.stellaris.service.Service;
import java.util.ArrayList;
import java.util.List;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;

public class ServiceManager {

    private List<ServiceRouter> routers = new ArrayList();
    
    private JsonBuilderFactory jsonBuilderFactory;

    public ServiceManager(JsonBuilderFactory jsonBuilderFactory) {
        this.jsonBuilderFactory = jsonBuilderFactory;
    }

    public void addServiceRouter(ServiceRouter router) {
        routers.add(router);
    }
    
    public Status queryService(ServiceQuery query) throws ServiceException 
    {
        Service service = null;
        for (ServiceRouter router : routers) {
            service = router.matchService(query);
            if (service != null) {
                break;
            }
        }
        if (service == null) {
            throw new ServiceException(Status.BAD_REQUEST, "Invalid path");
        }
        
        String path = query.getPath();
        int serviceId = 0;
        
        int slashIndex = path.lastIndexOf('/');
        if (slashIndex > 0) {
            String ending = path.substring(slashIndex+1);
            try {
                serviceId = Integer.parseInt(ending);
                path = path.substring(0,slashIndex);
            }
            catch(NumberFormatException ex) {
            }
        }

        switch(query.getMethod()) {
            case "GET": { 
                JsonObjectBuilder output = jsonBuilderFactory.createObjectBuilder();
                Status status = service.get(output, serviceId);
                query.setOutput(output.build());
                return status;
            }
            case "POST":  
                return service.post(query.getInput(), serviceId);
            case "PUT": {
                JsonObjectBuilder output = jsonBuilderFactory.createObjectBuilder();
                Status status = service.put(output, query.getInput());
                query.setOutput(output.build());
                return status;
            }
            case "DELETE":
                return service.delete(serviceId);
        }
        throw new ServiceException(Status.BAD_METHOD, "Method "+query.getMethod()+" invalid or unsupported");
    }


}
