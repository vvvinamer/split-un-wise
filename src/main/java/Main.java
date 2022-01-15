import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import models.Transaction;
import service.BalanceManagementService;
import service.BalancesManagementServiceImpl;

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
    for (Transaction transaction : transactions) {
      balanceManagementService.processTransaction(transaction);
    }
    balanceManagementService.printBalances();

    balanceManagementService.reduceTransactions();
    balanceManagementService.printReducedBalances();
    balanceManagementService.printNetBalances();
  }
}
