package todo.ws.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/todo")
public class TodoService {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response list() {
		
		
		
	}
	
}
