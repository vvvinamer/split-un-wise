package split;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import split.models.Transaction;
import split.service.BalanceManagementService;
import split.service.BalancesManagementServiceImpl;
import split.service.StatsService;
import split.service.StatsServiceImpl;

public class Main {

  public static List<Transaction> transactions = new ArrayList<>();

  public static void main(String[] args) throws IOException {

    //    TODO: should transactions be recorded somewhere as well?
    ObjectMapper objectMapper = new ObjectMapper();
    StringBuilder jsonArrayString = new StringBuilder();
    Files.lines(Paths.get("transactions.json")).forEach(jsonArrayString::append);

    transactions.addAll(
        objectMapper.readValue(
            jsonArrayString.toString(), new TypeReference<List<Transaction>>() {}));

    BalanceManagementService balanceManagementService = new BalancesManagementServiceImpl();
    StatsService statsService = new StatsServiceImpl();

    for (Transaction transaction : transactions) {
      balanceManagementService.processTransaction(transaction);
    }
    statsService.getBalances();
    statsService.printNetBalances();

    balanceManagementService.simplifyBalances();
    statsService.printCycles();

    statsService.getReducedBalances();
    statsService.printNetReducedBalances();
  }
}
