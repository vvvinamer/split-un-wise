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

  public void lendAmount(User paidToUser, float amount) {
    float amountLent = this.getAmountsLent().computeIfAbsent(paidToUser, key -> 0f);
    amountLent += amount;
    this.getAmountsLent().put(paidToUser, amountLent);
  }

  public void borrowAmount(User paidByUser, float amount) {
    this.lendAmount(paidByUser, amount * -1);
  }

  public void lendDummyAmount(User paidToUser, float amount) {
    float amountLent = this.getReducedAmountsLent().computeIfAbsent(paidToUser, key -> 0f);
    amountLent += amount;
    this.getReducedAmountsLent().put(paidToUser, amountLent);
  }

  public void borrowDummyAmount(User paidByUser, float amount) {
    this.lendDummyAmount(paidByUser, amount * -1);
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public int hashCode() {
    return this.name.hashCode();
  }
}
