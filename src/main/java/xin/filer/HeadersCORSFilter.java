package xin.filer;

import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HeadersCORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "content-type,x-requested-with,Authorization");
        response.setHeader("Access-Control-Allow-Credentials","true");


        String url = request.getHeader("Origin");
        if (!StringUtils.isEmpty(url)) {
            response.setHeader("Access-Control-Allow-Origin", url);
            response.setHeader("Access-Control-Allow-Credentials","true");

        }

        chain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}