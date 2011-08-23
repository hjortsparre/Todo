package todo.ws.service;

import java.util.List;

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
import todo.ws.dto.ResponseDTO;

import com.google.gson.Gson;

@Path("/todo")
public class TodoService {

	private Gson gson = new Gson();
	private TodoLogicFacade todoLogicFacade = new TodoLogicFacade();

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response list() {

		ResponseDTO responseDTO = new ResponseDTO();

		try {
			List<Todo> todos = todoLogicFacade.list();

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

			String jsonPayload = gson.toJson(todos);
			responseDTO.setPayload(jsonPayload);

		} catch (TodoDataException e) {
			responseDTO.setCode(e.getType().getCode());
			responseDTO.setMessage(e.getType().getMessage());
		} catch (TodoLogicException e) {
			responseDTO.setCode(e.getType().getCode());
			responseDTO.setMessage(e.getType().getMessage());
		}

		String json = gson.toJson(responseDTO);
		return Response.status(Status.OK).entity(json).build();

	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("create")
	public Response create(@QueryParam("email") String email,
			@QueryParam("password") String password,
			@QueryParam("name") String name) {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			todoLogicFacade.create(email, password, name);

		} catch (TodoDataException e) {
			responseDTO.setCode(e.getType().getCode());
			responseDTO.setMessage(e.getType().getMessage());
		} catch (TodoLogicException e) {
			responseDTO.setCode(e.getType().getCode());
			responseDTO.setMessage(e.getType().getMessage());
		}

		String json = gson.toJson(responseDTO);

		return Response.status(Status.OK).entity(json).build();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("check/{action}")
	public Response check(@PathParam("action") String action,
			@QueryParam("email") String email,
			@QueryParam("password") String password,
			@QueryParam("todoId") long todoId) {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			if (action.equals("out")) {
				todoLogicFacade.checkOut(email, password, todoId);
			} else if (action.equals("in")) {
				todoLogicFacade.checkIn(email, password, todoId);
			}

		} catch (TodoDataException e) {
			responseDTO.setCode(e.getType().getCode());
			responseDTO.setMessage(e.getType().getMessage());
		} catch (TodoLogicException e) {
			responseDTO.setCode(e.getType().getCode());
			responseDTO.setMessage(e.getType().getMessage());
		}

		String json = gson.toJson(responseDTO);

		return Response.status(Status.OK).entity(json).build();
	}

}
