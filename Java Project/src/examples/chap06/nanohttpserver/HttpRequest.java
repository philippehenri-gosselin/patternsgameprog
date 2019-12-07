/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.nanohttpserver;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private String method;
    
    private String path;
    
    private String version;
    
    private final HashMap<String,String> headers = new HashMap();

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getHeader(String name) {
        return headers.get(name.toLowerCase());
    }
    
    public void setHeader(String name,String value) {
        headers.put(name.toLowerCase(), value);
    }
 
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(method);
        builder.append(" ");
        builder.append(path);
        builder.append(" ");
        builder.append(version);
        builder.append("\n");
        for (Map.Entry<String, String> header : headers.entrySet()) {
            builder.append(header.getKey());
            builder.append(":");
            builder.append(header.getValue());
            builder.append("\n");
        }
        return builder.toString();
    }
    
}
