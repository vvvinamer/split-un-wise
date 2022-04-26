package split.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import split.models.Cycle;
import split.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class Beans {

    @Bean
    public Map<String, User> users() {
        return new HashMap<>();
    }

    @Bean
    public List<Cycle> cycles() {
        return new ArrayList<>();
    }

}
