package cat.uvic.teknos.m06.soccerhub.domain.repositories;

import cat.uvic.teknos.m06.soccerhub.domain.exceptions.RepositoryException;
import cat.uvic.teknos.m06.soccerhub.domain.models.League;
import cat.uvic.teknos.m06.soccerhub.domain.models.Player;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class JdbcPlayerRepository implements Repository<Player, Integer>{

    private static final String insert = "insert into player (playerId) values (values)";
    private static final String update = "update player set name = (name) where id = (playerId)";
    private static final String selectAll = "select * from player";
    private static final String Delete = "delete from player where id = (playerId)";

    private final Connection connection;
    public JdbcPlayerRepository(Connection connection){
        this.connection = connection;
    }

    @Override
    public void save(Player player) {
        if (player == null) {
            throw new RepositoryException("The products is null!");
        }
        if (player.getId() <= 0) {
            insert(player);
        } else {
            update(player);
        }
    }

    @Override
    public void delete(Integer model) {

    }

    private void update(Player player) {
        try (var preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setString(1, player.getName());
            preparedStatement.setInt(2, player.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + player, e);
        }

    }

    private void insert(Player player){
        try(var prepared = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)){
            prepared.setString(1, player.getName());
            prepared.executeUpdate();
            var generatedKeysResultSet = prepared.getGeneratedKeys();
            if (!generatedKeysResultSet.next()) {
                throw new RepositoryException("Exception while inserting: id not generated" + player);
            }
            player.setId(generatedKeysResultSet.getInt(1));
        } catch (SQLException e) {
            throw new RepositoryException("Exception while inserting: " + player, e);
        }
    }

    public void delete(Player player) {
        try (var preparedStatement = connection.prepareStatement(Delete)) {
            preparedStatement.setInt(1, player.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Exception while trying to delete", e);
        }
    }

    @Override
    public Player getById(Integer id) {
        Player player = null;
        try (var prepareStatement = connection.prepareStatement(selectAll + "where id = (playerId)")) {
            prepareStatement.setInt(1, id);
            var resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                player = new Player();
                player.setId(resultSet.getInt("playerId"));
                player.setName(resultSet.getString("name"));
            }

            return player;
        } catch (SQLException ex) {
            throw new RepositoryException("Exception while excecuting get by id");
        }

    }

    @Override
    public List<Player> getAll() {
        var players = new ArrayList<Player>();
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(selectAll);
            while (resultSet.next()) {
                var player = new Player();
                player.setId(resultSet.getInt("id"));
                player.setName(resultSet.getString("name"));
                players.add(player);
            }

            return players;
        } catch (SQLException e) {
            throw new RepositoryException("Exception while executing get all");
        }
    }

}
