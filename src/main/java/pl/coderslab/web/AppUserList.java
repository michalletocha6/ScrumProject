package pl.coderslab.web;

import pl.coderslab.dao.UserDao;
import pl.coderslab.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/app/users")
public class AppUserList extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = userDao.read(id);
            if (user.getEnable() == 1) {
                userDao.userStatus(0, id);
            } else {
                userDao.userStatus(1, id);
            }
        }


        List<User> users = userDao.findAll();

        request.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/super-admin-users.jsp")
                .forward(request, response);
    }
}
