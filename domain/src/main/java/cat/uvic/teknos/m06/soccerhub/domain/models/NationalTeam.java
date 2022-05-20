package cat.uvic.teknos.m06.soccerhub.domain.models;

public class NationalTeam {
    private int id;
    private int trophies;
    private Country CountryId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrophies() {
        return trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    public Country getCountryId() {
        return CountryId;
    }

    public void setCountryId(Country countryId) {
        CountryId = countryId;
    }
}
