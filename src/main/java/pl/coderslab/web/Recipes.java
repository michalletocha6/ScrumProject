package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/recipes")
public class Recipes extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("recipeName");
        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipes = recipeDao.findAll();

        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(name)) {
                request.setAttribute("recipe", recipe);
                getServletContext().getRequestDispatcher("/recipe-details.jsp").forward(request, response);
            }
        }
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipesSorted = recipeDao.findAllSorted();
        request.setAttribute("recipesSorted", recipesSorted);

        getServletContext().getRequestDispatcher("/recipes.jsp").forward(request, response);
    }
}
