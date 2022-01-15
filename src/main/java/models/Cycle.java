package models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cycle {

  private List<User> users;

  public void performReductionIfApplicable() {

    User paidByUser;
    User paidToUser;
    float minAmount = Float.MAX_VALUE;
    for (int i = 0; i < users.size() - 1; i++) {
      paidByUser = users.get(i);
      paidToUser = users.get(i + 1);
      minAmount = Float.min(minAmount, paidByUser.getReducedAmountsLent().get(paidToUser));
    }

    if (minAmount == 0) return;

    for (int i = 0; i < users.size() - 1; i++) {
      //            recording reverse dummy transaction
      paidToUser = users.get(i);
      paidByUser = users.get(i + 1);

      paidByUser.lendDummyAmount(paidToUser, minAmount);
      paidToUser.borrowDummyAmount(paidByUser, minAmount);
    }
  }

  @Override
  public String toString() {
    return users.toString();
  }
}
