package cat.uvic.teknos.m06.soccerhub.domain.models;

import java.util.List;

public class League {
    private int id;
    private String name;
    private Country CountryId;
    private List<Team> teams;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountryId() {
        return CountryId;
    }

    public void setCountryId(Country countryId) {
        CountryId = countryId;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
