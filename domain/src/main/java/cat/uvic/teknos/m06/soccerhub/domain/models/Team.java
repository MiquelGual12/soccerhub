package cat.uvic.teknos.m06.soccerhub.domain.models;

import java.util.List;

public class Team {
    private int id;
    private String name;
    private String city;
    private int trophies;
    private League LeagueId;
    private List<Player> players;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTrophies() {
        return trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    public League getLeagueId() {
        return LeagueId;
    }

    public void setLeagueId(League leagueId) {
        LeagueId = leagueId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

}
