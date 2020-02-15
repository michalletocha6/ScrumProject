package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;
import pl.coderslab.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/edit/password")
public class EditPassword extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String newPassword = request.getParameter("newPassword");
        String rePassword = request.getParameter("rePassword");
        UserDao userDao = new UserDao();
        User user = (User) session.getAttribute("user");
        int id = user.getId();
        if (!newPassword.equals(rePassword)) {
            request.setAttribute("messages1", "Pola róznią się od siebie.");
            doGet(request, response);

        } else if(newPassword.isEmpty()) {
            request.setAttribute("messages", "Pola nie mogą być puste.");
            doGet(request, response);
        }else{
            userDao.changePassword(User.hashPasw(newPassword), id);
            request.setAttribute("messages3", "Pomyślnie zmieniono hasło.");
            doGet(request, response);
        }

        getServletContext().getRequestDispatcher("/edit/password").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/app-edit-password.jsp").forward(request, response);
    }
}
