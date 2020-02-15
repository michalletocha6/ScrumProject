package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.Plan;
import pl.coderslab.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@WebServlet("/app/plan/add")
public class AppPlanAdd extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        PlanDao planDao = new PlanDao();
        int id = user.getId();
        Date date = new Date(System.currentTimeMillis());

        String planName = request.getParameter("planName");
        String planDescription = request.getParameter("planDescription");
        
        Plan plan = new Plan(planName, planDescription, date, id);
        planDao.create(plan);
        response.sendRedirect(request.getContextPath() + "/app/plan/list");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/app-add-schedules.jsp").forward(request, response);
    }
}
