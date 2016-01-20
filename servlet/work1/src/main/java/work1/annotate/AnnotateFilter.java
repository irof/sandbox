package work1.annotate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author irof
 */
@WebFilter(urlPatterns = "*.annotate")
public class AnnotateFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AnnotateFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("init: {}", filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("doFilter");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("destroy");
    }
}
