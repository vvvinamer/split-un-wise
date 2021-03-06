package split.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import split.models.Cycle;
import split.models.Transaction;
import split.models.User;
import org.springframework.stereotype.Component;

@Data
@Component
public class BalancesManagementServiceImpl implements BalanceManagementService {

  @Autowired
  public Map<String, User> users;

  @Autowired
  public List<Cycle> cycles;

  @Override
  public Transaction processTransaction(Transaction transaction) {

    User paidByUser = users.computeIfAbsent(transaction.getPaidFrom(), User::new);
    for (String userName : transaction.getPaidTo()) {
      if (userName.equals(paidByUser.getName())) continue;

      User paidToUser = users.computeIfAbsent(userName, User::new);
      float amountPerHead = transaction.getAmountPerHead();
      //         record that paidFrom user lent money
      paidByUser.lendAmount(paidToUser, amountPerHead);
      //         record that paidTo user borrowed money
      paidToUser.borrowAmount(paidByUser, amountPerHead);
    }
    return transaction;
  }

  @Override
  public List<Cycle> simplifyBalances() {
    findCycles();
    if (cycles.isEmpty()) return null;
    users.forEach((name, user) -> user.setReducedAmountsLent(new HashMap<>(user.getAmountsLent())));
    cycles.forEach(Cycle::performReductionIfApplicable);
    users.forEach((name, user) -> user.remove0Balances());
    return cycles;
  }

  private void findCycles() {

    Set<User> visitedUsers = new HashSet<>();
    List<User> currPathUsers = new ArrayList<>();

    for (Map.Entry<String, User> user : users.entrySet()) {
      User currentUser = user.getValue();
      if (visitedUsers.contains(currentUser)) continue;
      currPathUsers.add(currentUser);
      dfs(currentUser, visitedUsers, currPathUsers);
      currPathUsers.remove(currPathUsers.size() - 1);
      visitedUsers.add(currentUser);
    }
  }

  private void dfs(User currentUser, Set<User> visitedUsers, List<User> currPathUsers) {

    int cycleStartIndex = currPathUsers.indexOf(currentUser);
    if (cycleStartIndex != -1 && cycleStartIndex != currPathUsers.size() - 1) {
      cycles.add(
          new Cycle(new ArrayList<>(currPathUsers.subList(cycleStartIndex, currPathUsers.size()))));
      return;
    }

    if (visitedUsers.contains(currentUser)) return;
    //      now repeat the following for related users
    for (Map.Entry<User, Float> lendingRecord : currentUser.getAmountsLent().entrySet()) {
      User paidToUser = lendingRecord.getKey();
      Float amountLent = lendingRecord.getValue();
      if (amountLent <= 0) continue;
      currPathUsers.add(paidToUser);
      dfs(paidToUser, visitedUsers, currPathUsers);
      currPathUsers.remove(currPathUsers.size() - 1);
    }
  }
}
