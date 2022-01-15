package service;

import models.Transaction;

public interface BalanceManagementService {

  void processTransaction(Transaction transaction);

  void reduceTransactions();

  void printBalances();

  void printReducedBalances();

  void printNetBalances();
}
