package split.service;

import split.models.Cycle;
import split.models.Transaction;

import java.util.List;

public interface BalanceManagementService {

  Transaction processTransaction(Transaction transaction);

  List<Cycle> simplifyBalances();
}
