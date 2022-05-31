package cat.uvic.teknos.m06.soccerhub.domain.models;

import javax.persistence.*;
import java.util.List;
@Entity
public class NationalTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int trophies;
    @Transient
    private Country CountryId;
    @Transient
    private List<Player> players;

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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
