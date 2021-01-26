package app.services;

import app.entities.Stats;
import app.entities.Deaths;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CoronaVirusDataService {


    // ********************** CONFIRMED CASES *********************** //

    private static String CONFIRMED_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<Stats> confirmedStats = new ArrayList<>();
    public List<Stats> getConfirmedStats() {
        return confirmedStats;
    }
    HttpClient client = HttpClient.newHttpClient();

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchConfirmedData() throws IOException, InterruptedException {
        List<Stats> confirmed = new ArrayList<>();

        HttpRequest requestConfirmed = HttpRequest.newBuilder()
                .uri(URI.create(CONFIRMED_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(requestConfirmed, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            Stats confirm = new Stats();
            confirm.setProvince(record.get("Province/State"));
            confirm.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            confirm.setLatestTotalCases(latestCases);
            confirm.setDiffFromPrevDay(latestCases - prevDayCases);
            confirmed.add(confirm);
        }
        this.confirmedStats = confirmed;
    }


    // ********************** DEATHS CASES *********************** //

    private static String DEATHS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";

    private List<Deaths> deathsStats = new ArrayList<>();
    public List<Deaths> getDeathsStats() {
        return deathsStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchDeathsData() throws IOException, InterruptedException {
        List<Deaths> deaths = new ArrayList<>();
        HttpRequest requestDeaths = HttpRequest.newBuilder()
                .uri(URI.create(DEATHS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(requestDeaths, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            Deaths death = new Deaths();
            death.setProvince(record.get("Province/State"));
            death.setCountry(record.get("Country/Region"));
            int latestDeaths = Integer.parseInt(record.get(record.size() - 1));
            int prevDeathsCases = Integer.parseInt(record.get(record.size() - 2));
            death.setLatestDeathsTotals(latestDeaths);
            death.setDeathsTotalsDiff(latestDeaths - prevDeathsCases);
            deaths.add(death);
        }
        this.deathsStats = deaths;
    }



}
