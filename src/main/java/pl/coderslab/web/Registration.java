package pl.coderslab.web;

import pl.coderslab.dao.UserDao;
import pl.coderslab.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class Registration extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("firstName");
        String surname = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");

        UserDao userDao = new UserDao();

        User user = new User(name, surname, email, password);
        User emailCheck = userDao.selectByEmail(email);

        if (!user.isValide()) {
            request.setAttribute("message", "Pola nie mogą być puste.");
            doGet(request, response);
        } else if (!password.equals(repassword)) {
            request.setAttribute("message", "Pole \"podaj hasło\" różni się od \"powtórz hasło\" .");
            doGet(request, response);
        } else if (emailCheck != null) {
            request.setAttribute("message", "Istnieje już taki użytkownik.");
            doGet(request, response);
        } else {
            userDao.create(user);
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        getServletContext().getRequestDispatcher("/registration.jsp").forward(request, response);
    }
}
