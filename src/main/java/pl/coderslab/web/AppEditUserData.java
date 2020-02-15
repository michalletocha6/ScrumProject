package pl.coderslab.web;

import pl.coderslab.dao.UserDao;
import pl.coderslab.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/app/edit/user/data")
public class AppEditUserData extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String name = request.getParameter("name");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String validEmail = null;
        User user = (User) session.getAttribute("useredit");
        UserDao userDao = new UserDao();
        List<User> users = userDao.findAll();
        if (name.isEmpty() || lastname.isEmpty() || email.isEmpty()) {
            request.setAttribute("isempty", "Pola nie mogą być puste.");
            doGet(request, response);
        } else {
            for (User user1 : users) {
                if (user1.getEmail().equals(email)) {
                    validEmail = "Podany email już istnieje";
                    request.setAttribute("validEmail", validEmail);
                    doGet(request, response);
                }
            }
        }
        if (!user.getFirstName().equals(name)) {
            user.setFirstName(name);
        }
        if (!user.getLastName().equals(lastname)) {
            user.setLastName(lastname);
        }
        if (validEmail == null) {
            user.setEmail(email);
        }

        userDao.update(user);
        response.sendRedirect(request.getContextPath() + "/app/dashboard");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        session.setAttribute("useredit", user);
        getServletContext().getRequestDispatcher("/app-edit-user-data.jsp").forward(request, response);
    }
}
