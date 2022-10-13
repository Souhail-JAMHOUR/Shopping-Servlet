import java.io.Serializable;

public class User implements Serializable {
  private static final long serialVersionUID = 100L;
  private String email;
  private String password;
  private String firstName;
  private String lastName;

  public User(String email, String password, String firstName, String lastName) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public boolean propertiesAreNotNull() {
    if (!(this.getEmail().isEmpty()
        && this.getLastName().isEmpty()
        && this.getPassword().isEmpty()
        && this.getFirstName().isEmpty())) {
      return true;
    }
    return false;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    User user = (User) obj;
    return user.getEmail().equals(this.getEmail());
  }

  @Override
  public int hashCode() {
    return this.email.hashCode();
  }
}
