package app.controllers;

import app.entities.Stats;
import app.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ConfirmedController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/confirmed")
    public String home(Model model) {
        List<Stats> allStats = coronaVirusDataService.getConfirmedStats();
        int totalReportedCases = allStats.stream().mapToInt(stat ->  stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("Stats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);

        return "confirmed";
    }

}
