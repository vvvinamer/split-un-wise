package service;

import models.Transaction;
import models.User;

public interface StatsService {

  User netGetAmountMax();

  Transaction maxSpentAtOneTime();
}