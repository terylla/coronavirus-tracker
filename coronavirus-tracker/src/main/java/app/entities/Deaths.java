package app.entities;

public class Deaths {

    private int deaths;
    private int latestDeathsTotals;
    private int deathsTotalsDiff;
    private String country;
    private String province;

    public int getDeathsTotalsDiff() {
        return deathsTotalsDiff;
    }

    public void setDeathsTotalsDiff(int deathsTotalsDiff) {
        this.deathsTotalsDiff = deathsTotalsDiff;
    }



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

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }


    public int getLatestDeathsTotals() {
        return latestDeathsTotals;
    }

    public void setLatestDeathsTotals(int latestDeathsTotals) {
        this.latestDeathsTotals = latestDeathsTotals;
    }




}
