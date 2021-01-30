package app.services;

import app.entities.Recovered;
import app.entities.Confirmed;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {


    // ********************** CONFIRMED CASES *********************** //

    private static String CONFIRMED_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<Confirmed> confirmedStats = new ArrayList<>();
    public List<Confirmed> getConfirmedStats() {
        return confirmedStats;
    }
    HttpClient client = HttpClient.newHttpClient();

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchConfirmedData() throws IOException, InterruptedException {
        List<Confirmed> confirmed = new ArrayList<>();

        HttpRequest requestConfirmed = HttpRequest.newBuilder()
                .uri(URI.create(CONFIRMED_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(requestConfirmed, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            Confirmed confirm = new Confirmed();
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

    // ********************** RECOVERED CASES *********************** //
    private static String RECOVERED_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";

    List<Recovered> recoveredStats = new ArrayList<>();
    public List<Recovered> getRecoveredStats() {
        return recoveredStats;
    }



    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchRecoveredData() throws IOException, InterruptedException {
        List<Recovered> recovered = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(RECOVERED_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records){
            Recovered recover = new Recovered();
            recover.setProvince(record.get("Province/State"));
            recover.setCountry(record.get("Country/Region"));
            int latestRecovered = Integer.parseInt(record.get(record.size() -1));
            int prevLatestRecovered = Integer.parseInt(record.get(record.size() -2));
            recover.setLatestRecoveredTotals(latestRecovered);
            recover.setRecoveredDiff(prevLatestRecovered);
            recovered.add(recover);
        }
        this.recoveredStats = recovered;
    }


}
