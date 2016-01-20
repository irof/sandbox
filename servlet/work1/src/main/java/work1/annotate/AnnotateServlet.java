package work1.annotate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author irof
 */
@WebServlet("*.annotate")
public class AnnotateServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(AnnotateServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("doGet");
        resp.getWriter().write("HELLO, " + this.getClass());
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("init: {}", config);
        super.init(config);
    }

    @Override
    public void destroy() {
        logger.info("destroy");
        super.destroy();
    }
}
