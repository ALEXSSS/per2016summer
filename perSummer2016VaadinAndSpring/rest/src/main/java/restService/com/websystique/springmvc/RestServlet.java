package restService.com.websystique.springmvc;

import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.annotation.WebServlet;

/**
 * Created by Alexander.Luchko on 19.07.2016.
 */
@WebServlet(name = "PSC Rest Servlet", urlPatterns = "/rest/*", loadOnStartup = 1)
public class RestServlet extends DispatcherServlet {
    public RestServlet() {
        super(new GenericWebApplicationContext());
    }
}
