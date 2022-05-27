package cat.uvic.teknos.m06.soccerhub.domain.models;

public class Player {
    private int id;
    private String name;
    private String surname;
    private int age;
    private Country CountryId;
    private NationalTeam NationalTeamId;
    private Team TeamId;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Country getCountryId() {
        return CountryId;
    }

    public void setCountryId(Country countryId) {
        CountryId = countryId;
    }

    public NationalTeam getNationalTeamId() {
        return NationalTeamId;
    }

    public void setNationalTeamId(NationalTeam nationalTeamId) {
        NationalTeamId = nationalTeamId;
    }

    public Team getTeamId() {
        return TeamId;
    }

    public void setTeamId(Team teamId) {
        TeamId = teamId;
    }

}
