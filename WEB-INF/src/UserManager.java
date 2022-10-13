import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
  private Map<String, User> users;

  public Map<String, User> getUsers() {
    return users;
  }

  public void setUsers(Map<String, User> users) {
    this.users = users;
  }

  public UserManager(Map<String, User> users) {
    this.users = users;
  }

  public UserManager() {
    this.users = new HashMap<>();
  }

  public void registerUser(User userToRegister) {
    if (!(userToRegister.propertiesAreNotNull())) {
      throw new InvalidParameterException();
    } else if (this.users.containsKey(userToRegister.getEmail())) {
      throw new IllegalStateException();
    } else {
      this.users.put(userToRegister.getEmail(), userToRegister);
    }
  }

  public User loginUser(User userToLogin) {
    if (userToLogin.getEmail() == null
        || userToLogin.getEmail().isEmpty()
        || userToLogin.getPassword() == null
        || userToLogin.getPassword().isEmpty()) {
      throw new InvalidParameterException();
    } else if (!(this.users.containsKey(userToLogin.getEmail()))) {
      throw new IllegalArgumentException();
    }
    User retrieved = this.users.get(userToLogin.getEmail());
    if (!(retrieved.getEmail().equals(userToLogin.getEmail())
        && retrieved.getPassword().equals(userToLogin.getPassword()))) {
      throw new IllegalStateException();
    }
    return retrieved;
  }

  public static void main(String[] args) {
    User souhail = new User("ss@jm", "123", "souhail", "jamhour");
    User sara = new User("ss@cs", "123", "sara", "jamhour");
    UserManager usermanager = new UserManager();
    usermanager.registerUser(souhail);
    usermanager.registerUser(sara);
    for (Map.Entry<String, User> pair : usermanager.getUsers().entrySet()) {
      System.out.println("User " + pair.getValue().getFirstName());
    }
  }
}
