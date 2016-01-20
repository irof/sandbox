package work1.fragment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author irof
 */
public class FragmentFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(FragmentFilter.class);

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
