package app.entities;

public class Recovered {
    private String country;
    private String province;
    private int recovered;
    private int recoveredDiff;
    private int latestRecoveredTotals;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getRecoveredDiff() {
        return recoveredDiff;
    }

    public void setRecoveredDiff(int recoveredDiff) {
        this.recoveredDiff = recoveredDiff;
    }

    public int getLatestRecoveredTotals() {
        return latestRecoveredTotals;
    }

    public void setLatestRecoveredTotals(int latestRecoveredTotals) {
        this.latestRecoveredTotals = latestRecoveredTotals;
    }
}
