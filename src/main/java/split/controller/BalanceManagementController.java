package split.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import split.models.Cycle;
import split.models.Transaction;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import split.service.BalanceManagementService;
import split.service.StatsService;

import java.util.List;

@RestController
@RequestMapping("/balance")
public class BalanceManagementController {

    @Autowired
    private BalanceManagementService balanceManagementService;

    @PostMapping("/transaction")
    public Transaction recordTransaction(@RequestBody Transaction transaction) {
        return balanceManagementService.processTransaction(transaction);
    }

    @PutMapping("balance/simplify")
    public List<Cycle> simplifyBalances() {
        return balanceManagementService.simplifyBalances();
    }
}
