package JavaLaba7;

import JavaLaba7.DataSets.UserProfileDataSet;
import JavaLaba7.Service.DBService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/"})
public class SessionsServlet extends HttpServlet {
    public void doGet(HttpServletRequest httpServletRequest,
                      HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("log.jsp").forward(httpServletRequest, httpServletResponse);
    }

    //Вход в систему
    public void doPost(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) throws IOException {
        String login = httpServletRequest.getParameter("login");
        String pass = httpServletRequest.getParameter("pass");

        if (login.isEmpty() || pass.isEmpty()) {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().println("Отсутсвует логин или пароль");
            return;
        }

        UserProfileDataSet profile = DBService.getUserByLogin(login);
        if (profile == null || !profile.getPass().equals(pass)) {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().println("Неправильный логин или пароль");
            return;
        }

        httpServletRequest.getSession().setAttribute("login",login);
        httpServletRequest.getSession().setAttribute("pass",pass);

        String currentURL = httpServletRequest.getRequestURL().toString();
        httpServletResponse.sendRedirect(ServletUtilities.makeNewUrl(currentURL, "/manager"));
    }
}
