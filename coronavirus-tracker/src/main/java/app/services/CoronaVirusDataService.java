package app.services;

import app.entities.Stats;
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
    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<Stats> allStats = new ArrayList<>();

    public List<Stats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<Stats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            Stats locationStat = new Stats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStat.setLatestTotalCases(latestCases);
            locationStat.setDiffFromPrevDay(latestCases - prevDayCases);
            newStats.add(locationStat);
        }
        this.allStats = newStats;
    }

//    private static String COVID_DATA_URL = "https://github.com/CSSEGISandData/COVID-19/tree/master/csse_covid_19_data/csse_covid_19_daily_reports";
//    private static String date = new String("01-22-2020.csv");

//    private static String getCurrentDate(){
//        Date date = new Date();
//        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        String day = Integer.toString(localDate.getDayOfMonth());
//        String month = Integer.toString(localDate.getMonthValue());
//        String year = Integer.toString(localDate.getYear());
//        if (day.length() == 1)
//            day = "0" + day;
//        if (month.length() == 1)
//            month = "0" + month;
//        return month + "-" + day + "-" + year + ".csv";
//
//    }
//
//
//    private List<Stats> confirmedStats = new ArrayList<>();
//
//    public List<Stats> getAllStats() {
//        return confirmedStats;
//    }
//
//    @PostConstruct
//    @Scheduled(cron = "* * 1 * * *", zone = "EST")
//    public void fetchCovidData() throws IOException, InterruptedException {
//
//      //  date = getCurrentDate();
//        List<Stats> newStats = new ArrayList<>();
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(COVID_DATA_URL))
//                .build();
//        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
//        StringReader csvBodyReader = new StringReader(httpResponse.body());
//        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
//        for (CSVRecord record : records) {
//            Stats covidStat = new Stats();
//            covidStat.setState(record.get(2));
//            covidStat.setCountry(record.get(3));
//            covidStat.setConfirmed(Integer.parseInt(record.get(8)));
//            covidStat.setDeaths(Integer.parseInt(record.get(9)));
//            covidStat.setRecovered(Integer.parseInt(record.get(10)));
//            int latestCases = Integer.parseInt(record.get(record.size() - 1));
//            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
//            covidStat.setLatestTotalCases(latestCases);
//            covidStat.setDiffFromPrevDay(latestCases - prevDayCases);
//            newStats.add(covidStat);
//        }
//        this.confirmedStats = newStats;
//    }

}
