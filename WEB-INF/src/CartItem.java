import java.io.Serializable;

public class CartItem implements Serializable {
  private static final long serialVersionUID = 1L;
  public String imgAddress;
  public String name;
  public int price;

  public CartItem(String imgAddress, String name, int price) {
    this.imgAddress = imgAddress;
    this.name = name;
    this.price = price;
  }

  public String getImgAddress() {
    return imgAddress;
  }

  public void setImgAddress(String imgAddress) {
    this.imgAddress = imgAddress;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    CartItem item = (CartItem) obj;
    return item.getName().equals(this.getName());
  }

  @Override
  public int hashCode() {
    return this.name.hashCode();
  }
}
