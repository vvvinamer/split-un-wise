package models;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class User {

  private static int totalUsers;

  public User(String name) {
    this.name = name;
    this.amountsLent = new HashMap<>();
  }

  //  functionality for getting cause as well.
  //  transactions could be stored and this could be stored in transactions

  private String name;

  private Map<User, Float> amountsLent;

  private Map<User, Float> reducedAmountsLent;

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public int hashCode() {
    return this.name.hashCode();
  }
}
