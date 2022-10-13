import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class CartServlet extends HttpServlet {
  private CartManager cartManager;
  private DatabaseManager db;
  private static final long serialVersionUID = 1L;

  @Override
  public void init() throws ServletException {
    this.db = new DatabaseManager();
    this.cartManager = new CartManager(db.getUserCarts());
  }

  @Override
  public void destroy() {
    db.writeUserCarts(cartManager.getUserCart());
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession(false);
    if (session == null) {
      resp.sendRedirect("/catalog/login.html");
      return;
    }
    String email = session.getAttribute("email").toString();
    if (email == null) {
      session.invalidate();
      resp.sendRedirect("/catalog/login.html");
    }
    // retrieve the cart
    // Map<String,Map<CartItem, Integer>> usersCarts = cartManager.getUserCart();
    Map<CartItem, Integer> userCart = cartManager.getUserCart().get(email);
    PrintWriter out = resp.getWriter();
    if (userCart == null) {
      out.println("<html>");
      out.println("<body>");
      out.println("<p>");
      out.println("Your cart is Empty press the link below to go shopping");
      out.println("</p>");
      out.println("<a href='/catalog/catalog.html'>Go Shopping </a>");
      out.println("</body>");
      out.println("</html>");
    } else {
      out.println(CartSummaryHtmlGenerator.getCartSummaryPage(userCart));
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession(false);
    if (session == null) {
      resp.sendRedirect("/catalog/login.html");
    }
    String email = session.getAttribute("email").toString();
    if (email == null) {
      session.invalidate();
      resp.sendRedirect("/catalog/login.html");
    }
    String imgAddress = req.getParameter("imdAddress");
    String itemName = req.getParameter("itemName");
    Integer itemPrice = Integer.valueOf(req.getParameter("itemPrice"));
    CartItem cartItem = new CartItem(imgAddress, itemName, itemPrice);
    cartManager.addToCart(email, cartItem);
    resp.sendRedirect("/catalog/catalog.html");
  }
}
