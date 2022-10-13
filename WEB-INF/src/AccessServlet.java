import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class AccessServlet extends HttpServlet {
  private UserManager userManager;
  private DatabaseManager db;
  private static final long serialVersionUID = 1L;

  @Override
  public void init(ServletConfig config) throws ServletException {
    db = new DatabaseManager();
    userManager = new UserManager(db.getUsers());
  }

  @Override
  public void destroy() {
    db.writeUsers(userManager.getUsers());
  }

  private void loginAction(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String requestEmail = request.getParameter("email");
    String requestPasswor = request.getParameter("password");
    HttpSession session = request.getSession(false);
    if (session != null) {
      String email = session.getAttribute("email").toString();
      if (!(email.isEmpty())) {
        session.invalidate();
        response.sendRedirect("/catalog/login.html");
        return;
      }
      if (email.equals(request.getParameter("email"))) {
        response.sendRedirect("/catalog/catalog.html");
      } else {
        session.invalidate();
        response.sendRedirect("/catalog//login.html");
      }
    }
    try {
      User userToLogin = new User(requestEmail, requestPasswor);
      userManager.loginUser(userToLogin);
      HttpSession userSession = request.getSession(true);
      userSession.setAttribute("email", userToLogin.getEmail());
      response.sendRedirect("/catalog/catalog.html");
    } catch (Exception e) {
      String err =
          "Looks like there was an error with the user you tried to log in. Make sure that all the"
              + " fields in the form have some value and are not empty.";
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, err);
      return;
    }
  }

  private void registerAction(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String email = request.getParameter("email");
    String firstName = request.getParameter("firstName");
    String lastName = request.getParameter("lastName");
    String password = request.getParameter("password");
    HttpSession session = request.getSession(false);
    if (!(session == null)) {
      session.invalidate();
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Try Again Please");
      return;
    }
    User registeringUser =
        new User(
            request.getParameter("email"),
            request.getParameter("password"),
            request.getParameter("firstName"),
            request.getParameter("lastName"));

    try {
      userManager.registerUser(registeringUser);
      response.sendRedirect("/catalog/login.html");
    } catch (Exception e) {
      response.sendError(
          HttpServletResponse.SC_BAD_REQUEST,
          "Some erro occured while registering the user please make sure to provide all the"
              + " information needed");
    }
  }

  private void logoutAction(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    HttpSession session = request.getSession(false);
    if (session == null) {
      response.sendRedirect("/catalog/login.html");
    }
    session.invalidate();
    response.sendRedirect("/catalog/login.html");
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String action = request.getParameter("action");
    System.out.println(action);
    if (action == null) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No action");
    } else if (action.equals("login")) {
      loginAction(request, response);
    } else if (action.equals("register")) {
      registerAction(request, response);
    } else if (action.equals("logout")) {
      logoutAction(request, response);
    } else {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "rani hna ");
    }
  }
}
