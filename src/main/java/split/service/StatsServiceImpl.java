package split.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import split.models.Cycle;
import split.models.Transaction;
import split.models.User;
import org.springframework.stereotype.Component;

@Component
public class StatsServiceImpl implements StatsService {

  @Autowired
  public Map<String, User> users;

  @Autowired
  public List<Cycle> cycles;

  @Override
  public User netGetAmountMax() {
    return null;
  }

  @Override
  public Transaction maxSpentAtOneTime() {
    return null;
  }

  @Override
  public Map<String, Map<User, Float>> getBalances() {

    Map<String, Map<User, Float>> balances = new HashMap<>();
    users.forEach((key, value) -> balances.put(key, value.getAmountsLent()));
    return balances;
  }

  @Override
  public Map<String, Map<User, Float>> getReducedBalances() {
    Map<String, Map<User, Float>> balances = new HashMap<>();
    users.forEach((key, value) -> balances.put(key, value.getReducedAmountsLent()));
    return balances;
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
