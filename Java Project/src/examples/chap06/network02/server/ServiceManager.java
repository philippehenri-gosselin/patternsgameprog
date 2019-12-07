/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network02.server;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;

public class ServiceManager {

    private Map<String, Service> services = new HashMap();

    private JsonBuilderFactory jsonBuilderFactory;

    public ServiceManager(JsonBuilderFactory jsonBuilderFactory) {
        this.jsonBuilderFactory = jsonBuilderFactory;
    }

    public void registerService(String name,Service service) {
        services.put(name, service);
    }

    public void unregisterService(String name) {
        services.remove(name);
    }
    
    final Pattern servicePattern = Pattern.compile("(/[^/]+)/(\\d+)");
    public Status queryService(ServiceQuery query) throws ServiceException {
        String serviceName = query.getPath();
        int serviceId = 0;

        Matcher matcher = servicePattern.matcher(serviceName);
        if (matcher.matches()) {
            serviceName = matcher.group(1);
            serviceId = Integer.parseInt(matcher.group(2));
        }
        else {
            if (serviceName.endsWith("/")) {
                serviceName = serviceName.substring(0,serviceName.length()-1);
            }
        }        
        if (!services.containsKey(serviceName)) {
            throw new ServiceException(Status.BAD_REQUEST, "No service "+serviceName);
        }

        System.out.println(query.getMethod()+" "+serviceName+"/"+serviceId);

        Service service = services.get(serviceName);
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
