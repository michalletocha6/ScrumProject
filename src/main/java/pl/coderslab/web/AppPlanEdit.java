package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/plan/edit")
public class AppPlanEdit extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("planId"));
        String planName = request.getParameter("planName");
        String planDescription = request.getParameter("planDescription");

        PlanDao planDao = new PlanDao();

        Plan planToEdit = planDao.read(id);
        planToEdit.setName(planName);
        planToEdit.setDescription(planDescription);

        planDao.updateNameDescription(planToEdit);

        response.sendRedirect(request.getContextPath() + "/app/plan/list");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        PlanDao planDao = new PlanDao();
        Plan planToEdit = planDao.read(id);

        request.setAttribute("plan", planToEdit);

        getServletContext().getRequestDispatcher("/app-edit-schedules.jsp").forward(request, response);
    }
}
