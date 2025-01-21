/*
package forum_hub.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@WebFilter(urlPatterns = {"/api/*", "/api/**"})
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Request received at: " + LocalDateTime.now());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
*/
