package pl.coderslab.web;


import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        String email = (request.getParameter("email"));
        String password = request.getParameter("password");
        User user = userDao.selectByEmail(email);
        if (user == null) {
            request.setAttribute("message ", "Hasło lub login jest niepoprawne");
            doGet(request, response);
        } else {
            if (user.getEmail().equals(email) && BCrypt.checkpw(password, user.getPassword())) {
                if (user.getEnable() == 1) {
                    HttpSession session = request.getSession();
                    session.setAttribute("email", email);
                    session.setAttribute("user", user);
                    response.sendRedirect(request.getContextPath() + "/app/dashboard");
                } else {
                    request.setAttribute("message", "Konto zostało zablokowane!");
                    doGet(request, response);
                }


            } else {
                request.setAttribute("message", "Hasło lub login jest niepoprawne");
                doGet(request, response);

            }
        }

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDao userDao = new UserDao();
        request.setAttribute("users", userDao.findAll());
        getServletContext().getRequestDispatcher("/login.jsp")
                .forward(request, response);
    }
}
