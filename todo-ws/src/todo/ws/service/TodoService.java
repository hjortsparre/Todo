package todo.ws.service;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import todo.data.entity.Todo;
import todo.data.util.TodoDataException;
import todo.logic.facade.TodoLogicFacade;
import todo.logic.util.TodoLogicException;

import com.google.gson.Gson;

@Path("/todo")
public class TodoService {

	private Gson gson = new Gson();
	private TodoLogicFacade todoLogicalFacade = new TodoLogicFacade();

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response list() {

		try {
			List<Todo> todos = todoLogicalFacade.list();

			// If there is an account in getCheckedOut, it
			// will create a circular reference when serializing to
			// json, so we set it to null. This won't change the database state!
			// Probably a good idea to set password to null as well
			for (Todo todo : todos) {
				if (todo.getCheckedOutBy() != null) {
					todo.getCheckedOutBy().setCheckedOut(null);
					todo.getCheckedOutBy().setPassword(null);
				}
			}

			String entityData = gson.toJson(todos);

			return Response.status(Status.OK).entity(entityData).build();

		} catch (TodoDataException e) {

			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(gson.toJson(e.getType())).build();

		} catch (TodoLogicException e) {

			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(gson.toJson(e.getType())).build();

		}

	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("create")
	public Response create(@QueryParam("email") String email,
			@QueryParam("password") String password,
			@QueryParam("name") String name) {

		try {

			todoLogicalFacade.create(email, password, name);

			return Response.status(Status.OK).build();

		} catch (TodoDataException e) {

			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(gson.toJson(e.getType())).build();

		} catch (TodoLogicException e) {

			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(gson.toJson(e.getType())).build();

		}
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("check/{action}")
	public Response check(@PathParam("action") String action,
			@QueryParam("email") String email,
			@QueryParam("password") String password,
			@QueryParam("todoId") long todoId) {

		try {

			if (action.equals("out")) {
				todoLogicalFacade.checkOut(email, password, todoId);
			} else if (action.equals("in")) {
				todoLogicalFacade.checkIn(email, password, todoId);
			}

			return Response.status(Status.OK).build();

		} catch (TodoDataException e) {

			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(gson.toJson(e.getType())).build();

		} catch (TodoLogicException e) {

			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(gson.toJson(e.getType())).build();

		}

	}

}
