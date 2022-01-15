package service;

import models.Transaction;
import models.User;

public interface StatsService {

  User netGetAmountMax();

  Transaction maxSpentAtOneTime();

  void printBalances();

  void printReducedBalances();

  void printNetBalances();

  void printNetReducedBalances();

  void printCycles();
}
