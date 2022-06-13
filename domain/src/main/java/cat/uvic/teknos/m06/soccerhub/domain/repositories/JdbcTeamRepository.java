package cat.uvic.teknos.m06.soccerhub.domain.repositories;

import cat.uvic.teknos.m06.soccerhub.domain.exceptions.RepositoryException;
import cat.uvic.teknos.m06.soccerhub.domain.models.Team;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcTeamRepository implements  Repository<Team, Integer>{

    private static final String insert = "insert into team (teamId) values (values)";
    private static final String update = "update team set name = (name) where id = (teamId)";
    private static final String selectAll = "select * from team";
    private static final String Delete = "delete from team where id = (teamId)";

    private final Connection connection;
    public JdbcTeamRepository(Connection connection){
        this.connection = connection;
    }

    @Override
    public void save(Team team) {
        if (team == null) {
            throw new RepositoryException("The products is null!");
        }
        if (team.getId() <= 0) {
            insert(team);
        } else {
            update(team);
        }
    }

    @Override
    public void delete(Integer model) {

    }

    private void update(Team team) {
        try (var preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setString(1, team.getName());
            preparedStatement.setInt(2, team.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + team, e);
        }

    }

    private void insert(Team team){
        try(var prepared = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)){
            prepared.setString(1, team.getName());
            prepared.executeUpdate();
            var generatedKeysResultSet = prepared.getGeneratedKeys();
            if (!generatedKeysResultSet.next()) {
                throw new RepositoryException("Exception while inserting: id not generated" + team);
            }
            team.setId(generatedKeysResultSet.getInt(1));
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + team, e);
        }
    }

    public void delete(Team team) {
        try (var preparedStatement = connection.prepareStatement(Delete)) {
            preparedStatement.setInt(1, team.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while trying to delete", e);
        }
    }

    @Override
    public Team getById(Integer id) {
        Team team = null;
        try (var prepareStatement = connection.prepareStatement(selectAll + "where id = (leagueId)")) {
            prepareStatement.setInt(1, id);
            var resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                team = new Team();
                team.setId(resultSet.getInt("countryId"));
                team.setName(resultSet.getString("name"));
            }

            return team;
        } catch (SQLException ex) {
            throw new RepositoryException("Exception while excecuting get by id");
        }

    }

    @Override
    public List<Team> getAll() {
        var teams = new ArrayList<Team>();
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(selectAll);
            while (resultSet.next()) {
                var team = new Team();
                team.setId(resultSet.getInt("id"));
                team.setName(resultSet.getString("name"));
                teams.add(team);
            }

            return teams;
        } catch (SQLException e) {
            throw new RepositoryException("Exception while executing get all");
        }
    }
}
