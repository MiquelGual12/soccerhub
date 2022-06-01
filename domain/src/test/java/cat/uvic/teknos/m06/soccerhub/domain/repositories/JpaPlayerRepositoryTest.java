package cat.uvic.teknos.m06.soccerhub.domain.repositories;

import cat.uvic.teknos.m06.soccerhub.domain.models.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

public class JpaPlayerRepositoryTest {

    public static final int MODEL_TO_DELETE = 2;
    private static EntityManagerFactory entityManagerFactory;
    private static Repository<Player, Integer> repository;

    @BeforeAll
    static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("soccerhub_bd");
        repository = new JpaPlayerRepository(entityManagerFactory);
    }

    @Test
    void saveInsert() {
        var player = new Player();
        player.setId(1);
        player.setName("Leo");
        player.setSurname("Messi");
        player.setAge(33);


        assertDoesNotThrow(() -> {
            repository.save(player);
        });

        assertTrue(player.getId() > 0);
    }

    @Test
    void saveUpdate() {
        var player = new Player();
        player.setId(1);
        player.setAge(34);

        assertDoesNotThrow(() -> {
            repository.save(player);
        });

        var entityManager = entityManagerFactory.createEntityManager();
        var modifiedPlayer = entityManager.find(Player.class,1 );

        assertEquals("", modifiedPlayer.getId());
        entityManager.close();
    }

    @Test
    void delete() {
        var pop = repository.getById(MODEL_TO_DELETE);

        assertNotNull(pop);

        assertDoesNotThrow(() -> {
            repository.delete(MODEL_TO_DELETE);
        });

        pop = repository.getById(MODEL_TO_DELETE);

        assertNull(pop);
    }

    @Test
    void getById() {
        var player = repository.getById(2);

        assertNotNull(player);
    }

    @Test
    void getAll() {
        var players = repository.getAll();

        assertNotNull(players);
        assertTrue(players.size() > 0);
    }
}
