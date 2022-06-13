package cat.uvic.teknos.m06.soccerhub.domain.repositories;

import cat.uvic.teknos.m06.soccerhub.domain.exceptions.RepositoryException;
import cat.uvic.teknos.m06.soccerhub.domain.models.Country;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class JdbcCountryRepository implements Repository<Country, Integer> {

    private static final String insert = "insert into country (countryName) values (values)";
    private static final String update = "update country set name = (countryName) where id = (countryId)";
    private static final String selectAll = "select * from country";
    private static final String Delete = "delete from country where id = (countryId)";

    private final Connection connection;
    public JdbcCountryRepository(Connection connection){
        this.connection = connection;
    }

    @Override
    public void save(Country country) {
        if (country == null) {
            throw new RepositoryException("The products is null!");
        }
        if (country.getId() <= 0) {
            insert(country);
        } else {
            update(country);
        }
    }

    @Override
    public void delete(Integer model) {
        
    }

    private void update(Country country) {
        try (var preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setString(1, country.getName());
            preparedStatement.setInt(2, country.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + country, e);
        }

    }

    private void insert(Country country){
        try(var prepared = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)){
            prepared.setString(1, country.getName());
            prepared.executeUpdate();
            var generatedKeysResultSet = prepared.getGeneratedKeys();
            if (!generatedKeysResultSet.next()) {
                throw new RepositoryException("Exception while inserting: id not generated" + country);
            }
            country.setId(generatedKeysResultSet.getInt(1));
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + country, e);
        }
    }

    public void delete(Country country) {
        try (var preparedStatement = connection.prepareStatement(Delete)) {
            preparedStatement.setInt(1, country.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while trying to delete", e);
        }
    }

    @Override
    public Country getById(Integer id) {
        Country country = null;
        try (var prepareStatement = connection.prepareStatement(selectAll + "where id = (countryId)")) {
            prepareStatement.setInt(1, id);
            var resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                country = new Country();
                country.setId(resultSet.getInt("countryId"));
                country.setName(resultSet.getString("name"));
            }

            return country;
        } catch (SQLException ex) {
            throw new RepositoryException("Exception while excecuting get by id");
        }

    }

    @Override
    public List<Country> getAll() {
        var country = new ArrayList<Country>();
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(selectAll);
            while (resultSet.next()) {
                var country2 = new Country();
                country2.setId(resultSet.getInt("id"));
                country2.setName(resultSet.getString("name"));
                country.add(country2);
            }

            return country;
        } catch (SQLException e) {
            throw new RepositoryException("Exception while executing get all");
        }
    }
}
