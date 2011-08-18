package todo.ws.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import todo.data.util.TodoDataException;
import todo.logic.facade.AccountLogicFacade;
import todo.logic.util.TodoLogicException;

import com.google.gson.Gson;

@Path("/account")
public class AccountService {

	private Gson gson = new Gson();
	private AccountLogicFacade accountLogicFacade = new AccountLogicFacade();

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("register")
	public Response register(@QueryParam("email") String email,
			@QueryParam("password") String password) {

		try {
			accountLogicFacade.register(email, password);

			return Response.status(Status.OK).build();

		} catch (TodoDataException e) {
			return Response.status(Status.OK)
					.entity(gson.toJson(e.getType().getCode())).build();

		} catch (TodoLogicException e) {

			return Response.status(Status.OK)
					.entity(gson.toJson(e.getType().getCode())).build();

		}

	}

}
