import java.util.HashMap;
import java.util.Map;

public class CartManager {
  private Map<String, Map<CartItem, Integer>> userCarts;

  public CartManager(Map<String, Map<CartItem, Integer>> userCarts) {
    this.userCarts = userCarts;
  }

  public CartManager() {
    this.userCarts = new HashMap<>();
  }

  public Map<String, Map<CartItem, Integer>> getUserCart() {
    return this.userCarts;
  }

  public void addToCart(String email, CartItem item) {
    Map<String, Map<CartItem, Integer>> retrieved = getUserCart();
    if (retrieved == null) {
      this.userCarts.put(email, new HashMap<CartItem, Integer>());
      return;
      // retrieved = getUserCart(email);
    }
    if (retrieved.containsKey(email)) {
      Map<CartItem, Integer> userCart = retrieved.get(email);
      if (userCart.containsKey(item)) {
        int new_quantity = userCart.get(item) + 1;
        userCart.put(item, new_quantity);
      } else {

        userCart.put(item, 1);
      }
    } else {
      Map<CartItem, Integer> cartToAdd = new HashMap<>();
      cartToAdd.put(item, 1);
      this.userCarts.put(email, cartToAdd);
    }
  }

  public static void main(String[] args) {
    CartManager manager = new CartManager();
    CartItem item = new CartItem("imageaddrs", "swihla", 50);
    CartItem item2 = new CartItem("imageaddrs", "banana", 76);
    manager.addToCart("ss@jm", item);
    manager.addToCart("ss@jm", item2);
    manager.addToCart("ss@jm", item2);
    Map<String, Map<CartItem, Integer>> usersCart = manager.getUserCart();
    // for (Map.Entry<String, Map<CartItem, Integer>> pair :
    // manager.getUserCart().entrySet()) {
    // System.out.println("item " + pair.getValue() + "User name" +
    // pair.getValue());
    // }
    for (String email : usersCart.keySet()) {
      Map<CartItem, Integer> cart = usersCart.get(email);
      for (CartItem itemInCart : cart.keySet()) {
        String ItemName = itemInCart.getName();
        Integer count = cart.get(itemInCart);
        System.out.println("email : " + email + "   item " + ItemName + "\t quantity " + count);
      }
    }
  }
}
