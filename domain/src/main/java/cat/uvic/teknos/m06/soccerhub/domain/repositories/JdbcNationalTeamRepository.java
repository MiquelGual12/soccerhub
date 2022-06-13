package cat.uvic.teknos.m06.soccerhub.domain.repositories;

import cat.uvic.teknos.m06.soccerhub.domain.exceptions.RepositoryException;
import cat.uvic.teknos.m06.soccerhub.domain.models.NationalTeam;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class JdbcNationalTeamRepository implements Repository<NationalTeam, Integer> {

    private static final String insert = "insert into nationalteam (nationalTeamId) values (values)";
    private static final String update = "update nationalteam set trophies = (nTrophies) where id = (nationalTeamId)";
    private static final String selectAll = "select * from nationalteam";
    private static final String Delete = "delete from nationalteam where id = (nationalTeamId)";

    private final Connection connection;

    public JdbcNationalTeamRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(NationalTeam team) {
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

    private void update(NationalTeam team) {
        try (var preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setInt(1, team.getId());
            preparedStatement.setInt(2, team.getTrophies());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + team, e);
        }

    }

    private void insert(NationalTeam team) {
        try (var prepared = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            prepared.setInt(1, team.getId());
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

    public void delete(NationalTeam team) {
        try (var preparedStatement = connection.prepareStatement(Delete)) {
            preparedStatement.setInt(1, team.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while trying to delete", e);
        }
    }


    public NationalTeam getById(Integer id) {
        NationalTeam team1 = null;
        try (var prepareStatement = connection.prepareStatement(selectAll + "where id = (nationalTeamId)")) {
            prepareStatement.setInt(1, id);
            var resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                team1 = new NationalTeam();
                team1.setId(resultSet.getInt("countryId"));
                team1.setTrophies(resultSet.getInt("trophies"));
            }
            return team1;
        } catch (SQLException ex) {
            throw new RepositoryException("Exception while excecuting get by id");
        }

    }

    @Override
    public List<NationalTeam> getAll() {
        var team = new ArrayList<NationalTeam>();
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(selectAll);
            while (resultSet.next()) {
                var team2 = new NationalTeam();
                team2.setId(resultSet.getInt("id"));
                team2.setTrophies(resultSet.getInt("trophies"));
                team.add(team2);
            }

            return team;
        } catch (SQLException e) {
            throw new RepositoryException("Exception while executing get all");
        }
    }

}
