package cat.uvic.teknos.m06.soccerhub.domain.repositories;

import cat.uvic.teknos.m06.soccerhub.domain.models.NationalTeam;
import cat.uvic.teknos.m06.soccerhub.domain.models.Team;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;


public class JpaTeamRepositoryTest {
    public static final int MODEL_TO_DELETE = 2;
    private static EntityManagerFactory entityManagerFactory;
    private static Repository<Team, Integer> repository;

    @BeforeAll
    static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("soccerhub_bd");
        repository = new JpaTeamRepository(entityManagerFactory);
    }

    @Test
    void saveInsert() {
        var team = new Team();
        team.setId(1);
        team.setName("F.C.Barcelona");
        team.setCity("Barcelona");
        team.setTrophies(4);


        assertDoesNotThrow(() -> {
            repository.save(team);
        });

        assertTrue(team.getId() > 0);
    }

    @Test
    void saveUpdate() {
        var team = new Team();
        team.setId(1);
        team.setTrophies(5);

        assertDoesNotThrow(() -> {
            repository.save(team);
        });

        var entityManager = entityManagerFactory.createEntityManager();
        var modifiedTeam = entityManager.find(Team.class,1 );

        assertEquals("", modifiedTeam.getId());
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
        var team = repository.getById(2);

        assertNotNull(team);
    }

    @Test
    void getAll() {
        var teams = repository.getAll();

        assertNotNull(teams);
        assertTrue(teams.size() > 0);
    }

}
