package web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;

@Path("/headers")
public class HeadersService {

    @GET
    public MultivaluedMap<String, String> getHeaders(@Context HttpHeaders httpHeaders){
        return httpHeaders.getRequestHeaders();
    }

}
