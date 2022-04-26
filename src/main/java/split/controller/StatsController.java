package split.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import split.models.User;
import split.service.StatsService;

import java.util.Map;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;


    @GetMapping("/balances/{isReduced}")
    public Map<String, Map<User, Float>> getBalances(
            @PathVariable Boolean isReduced) {
        if(Boolean.FALSE.equals(isReduced))
            return statsService.getBalances();
        return statsService.getReducedBalances();
    }
}
