package cat.uvic.teknos.m06.soccerhub.domain.repositories;

import cat.uvic.teknos.m06.soccerhub.domain.exceptions.RepositoryException;
import cat.uvic.teknos.m06.soccerhub.domain.models.Country;
import cat.uvic.teknos.m06.soccerhub.domain.models.League;
import cat.uvic.teknos.m06.soccerhub.domain.models.NationalTeam;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcLeagueRepository implements Repository<League, Integer>{

    private static final String insert = "insert into league (leagueId) values (values)";
    private static final String update = "update league set name = (name) where id = (leagueId)";
    private static final String selectAll = "select * from league";
    private static final String Delete = "delete from league where id = (leagueId)";

    private final Connection connection;
    public JdbcLeagueRepository(Connection connection){
        this.connection = connection;
    }

    @Override
    public void save(League league) {
        if (league == null) {
            throw new RepositoryException("The products is null!");
        }
        if (league.getId() <= 0) {
            insert(league);
        } else {
            update(league);
        }
    }

    @Override
    public void delete(Integer model) {

    }

    private void update(League league) {
        try (var preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setString(1, league.getName());
            preparedStatement.setInt(2, league.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + league, e);
        }

    }

    private void insert(League league){
        try(var prepared = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)){
            prepared.setString(1, league.getName());
            prepared.executeUpdate();
            var generatedKeysResultSet = prepared.getGeneratedKeys();
            if (!generatedKeysResultSet.next()) {
                throw new RepositoryException("Exception while inserting: id not generated" + league);
            }
            league.setId(generatedKeysResultSet.getInt(1));
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + league, e);
        }
    }

    public void delete(League league) {
        try (var preparedStatement = connection.prepareStatement(Delete)) {
            preparedStatement.setInt(1, league.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while trying to delete", e);
        }
    }

    @Override
    public League getById(Integer id) {
        League league = null;
        try (var prepareStatement = connection.prepareStatement(selectAll + "where id = (leagueId)")) {
            prepareStatement.setInt(1, id);
            var resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                league = new League();
                league.setId(resultSet.getInt("countryId"));
                league.setName(resultSet.getString("name"));
            }

            return league;
        } catch (SQLException ex) {
            throw new RepositoryException("Exception while excecuting get by id");
        }

    }

    @Override
    public List<League> getAll() {
        var leagues = new ArrayList<League>();
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(selectAll);
            while (resultSet.next()) {
                var league = new League();
                league.setId(resultSet.getInt("id"));
                league.setName(resultSet.getString("name"));
                leagues.add(league);
            }

            return leagues;
        } catch (SQLException e) {
            throw new RepositoryException("Exception while executing get all");
        }
    }

}
