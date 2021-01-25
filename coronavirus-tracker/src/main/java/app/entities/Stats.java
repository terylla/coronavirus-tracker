package app.entities;

public class Stats {

//
//    private int confirmed;
//    private int recovered;
//    private int deaths;
    private String state;
    private String country;
    private int latestTotalCases;
    private int diffFromPrevDay;


    public Stats(){

    }

//    public int getConfirmed() {
//        return confirmed;
//    }
//
//    public void setConfirmed(int confirmed) {
//        this.confirmed = confirmed;
//    }
//
//    public int getRecovered() {
//        return recovered;
//    }
//
//    public void setRecovered(int recovered) {
//        this.recovered = recovered;
//    }
//
//    public int getDeaths() {
//        return deaths;
//    }
//
//    public void setDeaths(int deaths) {
//        this.deaths = deaths;
//    }


    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestTotalCases=" + latestTotalCases +
                '}';
    }
}
