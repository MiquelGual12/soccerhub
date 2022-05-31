package cat.uvic.teknos.m06.soccerhub.domain.repositories;

import cat.uvic.teknos.m06.soccerhub.domain.models.League;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

public class JpaLeagueRepositoryTest {
    public static final int MODEL_TO_DELETE = 2;
    private static EntityManagerFactory entityManagerFactory;
    private static Repository<League, Integer> repository;

    @BeforeAll
    static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("soccerhub_bd");
        repository = new JpaLeagueRepository(entityManagerFactory);
    }

    @Test
    void saveInsert() {
        var league = new League();
        league.setName("MSL");
        league.setId(1);


        assertDoesNotThrow(() -> {
            repository.save(league);
        });

        assertTrue(league.getId() > 0);
    }

    @Test
    void saveUpdate() {
        var league = new League();
        league.setId(1);
        league.setName("MLS");

        assertDoesNotThrow(() -> {
            repository.save(league);
        });

        var entityManager = entityManagerFactory.createEntityManager();
        var modifiedLeague = entityManager.find(League.class,1 );

        assertEquals("", modifiedLeague.getName());
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
        var league = repository.getById(2);

        assertNotNull(league);
    }

    @Test
    void getAll() {
        var leagues = repository.getAll();

        assertNotNull(leagues);
        assertTrue(leagues.size() > 0);
    }

}
