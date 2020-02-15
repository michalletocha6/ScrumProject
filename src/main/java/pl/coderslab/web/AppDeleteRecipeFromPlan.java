package pl.coderslab.web;

import pl.coderslab.dao.RecipePlanDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/app/recipe/plan/delete")
public class AppDeleteRecipeFromPlan extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        int planId = Integer.parseInt(request.getParameter("planId"));

        RecipePlanDao rpDao = new RecipePlanDao();

        if (itemId != 0) {
            rpDao.delete(itemId);
        }

        response.sendRedirect(request.getContextPath() + "/app/plan/details?id=" + planId);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int planId = Integer.parseInt(request.getParameter("planId"));
        int itemId = Integer.parseInt(request.getParameter("itemId"));

        request.setAttribute("recipeId", id);
        request.setAttribute("planId", planId);
        request.setAttribute("itemId", itemId);
        getServletContext().getRequestDispatcher("/app-recipe-plan-delete.jsp").forward(request, response);
    }
}
