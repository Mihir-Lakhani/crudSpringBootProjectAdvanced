package in.mihirlakhani.crudSpringBootProject.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@Order(1)
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        long startTime = System.currentTimeMillis();

        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            String token = httpServletRequest.getHeader("token");
            if (!"12345".equals(token)) {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpServletResponse.setContentType("application/json");
                httpServletResponse.setCharacterEncoding("UTF-8");

                httpServletResponse.getWriter().write(
                        "{\"issue\":\"Could not authenticate token\"}"
                );

                return; // Stop: do not pass this request further
            }

            chain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            System.out.println("Request processing time: " + duration + " ms");
        }
    }


}
