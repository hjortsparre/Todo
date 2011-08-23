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
import todo.ws.dto.ResponseDTO;

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

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			accountLogicFacade.register(email, password);

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
	@Path("login")
	public Response login(
			@QueryParam("email") String email,
			@QueryParam("password") String password) {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			accountLogicFacade.login(email, password);

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
