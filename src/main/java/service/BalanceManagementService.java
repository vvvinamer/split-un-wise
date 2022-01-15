package service;

import models.Transaction;

public interface BalanceManagementService {

  void processTransaction(Transaction transaction);

  void reduceTransactions();
}
