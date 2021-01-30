package app.controllers;

import app.entities.Deaths;
import app.entities.Recovered;
import app.entities.Confirmed;
import app.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ServiceController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/confirmed")
    public String confirmed(Model model) {
        List<Confirmed> stats = coronaVirusDataService.getConfirmedStats();
        int totalReportedCases = stats.stream().mapToInt(stat ->  stat.getLatestTotalCases()).sum();
        int totalNewCases = stats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("Stats", stats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);

        return "confirmed";
    }

    @GetMapping("/deaths")
    public String deaths(Model model) {
        List<Deaths> stats = coronaVirusDataService.getDeathsStats();
        int totalDeathsCases = stats.stream().mapToInt(stat ->  stat.getLatestDeathsTotals()).sum();
        int totalNewDeathsCases = stats.stream().mapToInt(stat -> stat.getDeathsTotalsDiff()).sum();
        model.addAttribute("Deaths", stats);
        model.addAttribute("totalDeathsCases", totalDeathsCases);
        model.addAttribute("totalNewDeathsCases", totalNewDeathsCases);

        return "deaths";
    }

    @GetMapping("/recovered")
    public String recovered(Model model) {
        List<Recovered> stats = coronaVirusDataService.getRecoveredStats();
        int totalRecovered = stats.stream().mapToInt(stat -> stat.getLatestRecoveredTotals()).sum();
        int totalNewRecovered = stats.stream().mapToInt(stat -> stat.getRecoveredDiff()).sum();
        model.addAttribute("Recovered", stats);
        model.addAttribute("totalRecovered", totalRecovered);
        model.addAttribute("totalNewRecovered", totalNewRecovered);

        return "recovered";
    }


}
