package app.controllers;

import app.entities.Deaths;
import app.entities.Stats;
import app.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DeathsController {
    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/deaths")
    public String home(Model model) {
        List<Deaths> allStats = coronaVirusDataService.getDeathsStats();
        int totalDeathsCases = allStats.stream().mapToInt(stat ->  stat.getLatestDeathsTotals()).sum();
        int totalNewDeathsCases = allStats.stream().mapToInt(stat -> stat.getDeathsTotalsDiff()).sum();
        model.addAttribute("Deaths", allStats);
        model.addAttribute("totalDeathsCases", totalDeathsCases);
        model.addAttribute("totalNewDeathsCases", totalNewDeathsCases);

        return "deaths";
    }
}
