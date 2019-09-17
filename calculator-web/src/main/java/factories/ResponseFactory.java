package factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import models.errors.ErrorCodeMessage;
import org.codehaus.jackson.map.ObjectMapper;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static models.errors.ExceptionMessages.GENERAL_EXCEPTION_MESSAGE;
import static models.errors.ExceptionMessages.ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE;

public class ResponseFactory {
    @Inject
    private ObjectMapper objectMapper;


    public Response getResponse(String message) throws IOException {

        if (GENERAL_EXCEPTION_MESSAGE.equals(message)) {
            return errorResponseAsJSON(message, SC_INTERNAL_SERVER_ERROR);
        }
        return errorResponseAsJSON(message, SC_BAD_REQUEST);
    }

    private Response errorResponseAsJSON(String exceptionMessage, int statusCode) throws IOException {
        ErrorCodeMessage errorCodeMessage = new ErrorCodeMessage(exceptionMessage, statusCode);
        String json = objectMapper.writeValueAsString(errorCodeMessage);
        return Response.status(statusCode).entity(json).build();
    }
}


