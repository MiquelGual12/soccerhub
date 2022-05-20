package cat.uvic.teknos.m06.soccerhub.domain.models;

import java.util.List;

public class Country {
    private int id;
    private String name;
    private int poblation;
    private List<League> leagues;
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

    public int getPoblation() {
        return poblation;
    }

    public void setPoblation(int poblation) {
        this.poblation = poblation;
    }

    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

}
