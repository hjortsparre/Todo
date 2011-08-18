package todo.webapp.filter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import todo.webapp.dto.ResponseDTO;
import todo.webapp.dto.TodoDTO;
import todo.webapp.util.HTTPHelper;
import todo.webapp.util.WebServiceRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@WebFilter(urlPatterns = "/list.jsp", dispatcherTypes = {
		DispatcherType.FORWARD, DispatcherType.REQUEST })
public class ListFilter implements Filter {

	private Gson gson = new Gson();
	private WebServiceRequest webServiceRequest = new WebServiceRequest();

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		try {

			ResponseDTO responseDTO = webServiceRequest.list();
			// If this deserialization fails, we probably received an error code
			Type collectionType = new TypeToken<Collection<TodoDTO>>() {
			}.getType();
			List<TodoDTO> todos = gson.fromJson(responseDTO.getPayload(),
					collectionType);

			request.setAttribute("todos", todos);

			chain.doFilter(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
