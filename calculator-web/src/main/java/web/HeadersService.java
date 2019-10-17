package web;

import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.io.IOException;

@Path("/headers")
@Produces(MediaType.APPLICATION_JSON)
public class HeadersService {

    @GET
        public String getHeaders(@Context HttpHeaders httpHeaders) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
       return objectMapper.writeValueAsString(httpHeaders.getRequestHeaders());

    }

}
