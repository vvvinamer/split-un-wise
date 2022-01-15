package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import models.Pair;
import models.Transaction;
import models.User;

@Data
public class BalancesManagementServiceImpl implements BalanceManagementService {

  private Map<String, User> users = new HashMap<>();

  @Override
  public void processTransaction(Transaction transaction) {

    User paidByUser = users.computeIfAbsent(transaction.getPaidFrom(), User::new);

    for (String userName : transaction.getPaidTo()) {
      User paidToUser = users.computeIfAbsent(userName, User::new);

      float amountPerHead = transaction.getAmountPerHead();

      //         record that paidFrom user lent money
      lendAmount(paidByUser, paidToUser, amountPerHead);

      //         record that paidTo user borrowed money
      borrowAmount(paidByUser, paidToUser, amountPerHead);
    }
  }

  @Override
  public void reduceTransactions() {

    Set<User> visitedUsers = new HashSet<>();
    List<Pair<User, Float>> currPathUsers = new ArrayList<>();

    //    this would re-computed from beginning
    users.values().forEach(user -> user.setReducedAmountsLent(new HashMap<>(user.getAmountsLent())));

    for (Map.Entry<String, User> user : users.entrySet()) {
      User currentUser = user.getValue();
      if (visitedUsers.contains(currentUser)) continue;
      currPathUsers.add(new Pair<>(currentUser, Float.MAX_VALUE));
      dfs(currentUser, visitedUsers, currPathUsers);
      currPathUsers.remove(currPathUsers.size() - 1);
      visitedUsers.add(currentUser);
    }
  }

  private void dfs(
      User currentUser, Set<User> visitedUsers, List<Pair<User, Float>> currPathUsers) {

    int cycleStartIndex =
        currPathUsers.stream()
            .map(entry -> entry.first)
            .collect(Collectors.toList())
            .indexOf(currentUser);

    if (cycleStartIndex != -1 && cycleStartIndex != currPathUsers.size() - 1) {
      applyReductionLogic(currPathUsers, cycleStartIndex);
      return;
    }

    if (visitedUsers.contains(currentUser)) return;
    //      now repeat the following for related users
    for (Map.Entry<User, Float> lendingRecord : currentUser.getAmountsLent().entrySet()) {
      User paidToUser = lendingRecord.getKey();
      Float amountLent = lendingRecord.getValue();
      if (paidToUser.equals(currentUser) || amountLent < 0) continue;
      currPathUsers.add(new Pair<>(paidToUser, amountLent));
      dfs(paidToUser, visitedUsers, currPathUsers);
      currPathUsers.remove(currPathUsers.size() - 1);
    }

    visitedUsers.add(currentUser);
  }

  private void applyReductionLogic(List<Pair<User, Float>> currPathUsers, int cycleStartIndex) {
    User paidByUser;
    User paidToUser;

    //          a cycle has been detected and reduction logic has to be applied
    float minAmount = currPathUsers.stream().map(entry -> entry.second).min(Float::compare).get();

    if (minAmount == 0f) return;

    for (int i = cycleStartIndex + 1; i < currPathUsers.size(); i++) {
      paidByUser = currPathUsers.get(i - 1).first;
      paidToUser = currPathUsers.get(i).first;

      currPathUsers.get(i).second -= minAmount;

      //        recording reverse amounts
      lendDummyAmount(paidToUser, paidByUser, minAmount);
      borrowDummyAmount(paidToUser, paidByUser, minAmount);
    }
  }

  private void lendAmount(User paidByUser, User paidToUser, float amount) {
    float amountLent = paidByUser.getAmountsLent().computeIfAbsent(paidToUser, key -> 0f);
    amountLent += amount;
    paidByUser.getAmountsLent().put(paidToUser, amountLent);
  }

  private void borrowAmount(User paidByUser, User paidToUser, float amount) {
    lendAmount(paidToUser, paidByUser, amount * -1);
  }

  private void lendDummyAmount(User paidByUser, User paidToUser, float amount) {
    float amountLent =
        paidByUser
            .getReducedAmountsLent()
            .computeIfAbsent(
                paidToUser,
                key -> paidByUser.getAmountsLent().get(key));
    amountLent += amount;
    paidByUser.getReducedAmountsLent().put(paidToUser, amountLent);
  }

  private void borrowDummyAmount(User paidByUser, User paidToUser, float amount) {
    lendDummyAmount(paidToUser, paidByUser, amount * -1);
  }

  @Override
  public void printBalances() {
    for (Map.Entry<String, User> entry : users.entrySet()) {
      System.out.println(entry.getKey() + "   Balances : " + entry.getValue().getAmountsLent());
    }
    System.out.println("---------------------------------------------");
  }

  @Override
  public void printReducedBalances() {
    for (Map.Entry<String, User> entry : users.entrySet()) {
      System.out.println(
          entry.getKey() + "   Balances : " + entry.getValue().getReducedAmountsLent());
    }
    System.out.println("---------------------------------------------");
  }

  @Override
  public void printNetBalances() {

    for (Map.Entry<String, User> entry : users.entrySet()) {
      float netAmount =
          entry.getValue().getAmountsLent().values().stream().reduce(Float::sum).get();
      System.out.println(entry.getKey() + " : " + netAmount);
    }

    System.out.println("---------------------------------------------");

    for (Map.Entry<String, User> entry : users.entrySet()) {
      float netAmount =
          entry.getValue().getReducedAmountsLent().values().stream().reduce(Float::sum).get();
      System.out.println(entry.getKey() + " : " + netAmount);
    }

    System.out.println("---------------------------------------------");
  }
}
