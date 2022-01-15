import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import models.Transaction;
import service.BalanceManagementService;
import service.BalancesManagementServiceImpl;
import service.StatsService;
import service.StatsServiceImpl;

public class Main {

  public static void main(String[] args) throws IOException {

    //    TODO: should transactions be recorded somewhere as well?
    ObjectMapper objectMapper = new ObjectMapper();
    StringBuilder jsonArrayString = new StringBuilder();
    Files.lines(Paths.get("transactions.json")).forEach(jsonArrayString::append);

    List<Transaction> transactions =
        objectMapper.readValue(
            jsonArrayString.toString(), new TypeReference<List<Transaction>>() {});

    BalanceManagementService balanceManagementService = new BalancesManagementServiceImpl();
    StatsService statsService = new StatsServiceImpl();

    for (Transaction transaction : transactions) {
      balanceManagementService.processTransaction(transaction);
    }
    statsService.printBalances();

    balanceManagementService.reduceTransactions();
    statsService.printReducedBalances();
    statsService.printNetBalances();
  }
}
