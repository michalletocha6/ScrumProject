package pl.coderslab.web;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/app/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // filtr sprawdza czy jest w sesji ustawiony parametr email, w servlecie Login trzeba dopisać ustawienie sesji
        boolean authorized = false;
        if (request != null) {
            HttpSession session = httpRequest.getSession(false);
            if (session != null) {
                String email = (String) session.getAttribute("email");
                if (email != null)
                    authorized = true;
            }
        }

        if (authorized) {
            chain.doFilter(request, response);
            return;
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }
}
