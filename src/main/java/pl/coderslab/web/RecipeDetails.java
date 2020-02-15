package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/recipe/details")
public class RecipeDetails extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe = recipeDao.read(id);

        String ingredients = recipe.getIngredients();
        String[] ingrTab = ingredients.split(",");

        request.setAttribute("recipe", recipe);
        request.setAttribute("ingrTab", ingrTab);

        getServletContext().getRequestDispatcher("/recipe-details.jsp").forward(request, response);

    }
}
