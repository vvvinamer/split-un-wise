package split.service;

import split.models.Transaction;
import split.models.User;

import java.util.Map;

public interface StatsService {

  User netGetAmountMax();

  Transaction maxSpentAtOneTime();

  Map<String, Map<User, Float>> getBalances();

  Map<String, Map<User, Float>> getReducedBalances();

  void printNetBalances();

  void printNetReducedBalances();

  void printCycles();
}
