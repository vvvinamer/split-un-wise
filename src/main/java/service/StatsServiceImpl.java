package service;

import static service.BalancesManagementServiceImpl.cycles;
import static service.BalancesManagementServiceImpl.users;

import java.util.Map;
import models.Cycle;
import models.Transaction;
import models.User;

public class StatsServiceImpl implements StatsService {

  @Override
  public User netGetAmountMax() {
    return null;
  }

  @Override
  public Transaction maxSpentAtOneTime() {
    return null;
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
  }

  @Override
  public void printNetReducedBalances() {
    for (Map.Entry<String, User> entry : users.entrySet()) {
      float netAmount =
          entry.getValue().getReducedAmountsLent().values().stream().reduce(Float::sum).get();
      System.out.println(entry.getKey() + " : " + netAmount);
    }

    System.out.println("---------------------------------------------");
  }

  @Override
  public void printCycles() {
    for (Cycle cycle : cycles) {
      System.out.println(cycle);
    }
    System.out.println("---------------------------------------------");
  }
}
