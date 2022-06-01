package cat.uvic.teknos.m06.soccerhub.domain.repositories;

import cat.uvic.teknos.m06.soccerhub.domain.models.League;
import cat.uvic.teknos.m06.soccerhub.domain.models.NationalTeam;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

public class JpaNationalTeamRepositoryTest {
    public static final int MODEL_TO_DELETE = 2;
    private static EntityManagerFactory entityManagerFactory;
    private static Repository<NationalTeam, Integer> repository;

    @BeforeAll
    static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("soccerhub_bd");
        repository = new JpaNationalTeamRepository(entityManagerFactory);
    }

    @Test
    void saveInsert() {
        var nationalTeam = new NationalTeam();
        nationalTeam.setId(1);
        nationalTeam.setTrophies(15);


        assertDoesNotThrow(() -> {
            repository.save(nationalTeam);
        });

        assertTrue(nationalTeam.getId() > 0);
    }

    @Test
    void saveUpdate() {
        var nationalTeam = new NationalTeam();
        nationalTeam.setId(1);
        nationalTeam.setTrophies(16);

        assertDoesNotThrow(() -> {
            repository.save(nationalTeam);
        });

        var entityManager = entityManagerFactory.createEntityManager();
        var modifiedNationalTeam = entityManager.find(NationalTeam.class,1 );

        assertEquals("", modifiedNationalTeam.getId());
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
        var nationalTeam = repository.getById(2);

        assertNotNull(nationalTeam);
    }

    @Test
    void getAll() {
        var nationalTeams = repository.getAll();

        assertNotNull(nationalTeams);
        assertTrue(nationalTeams.size() > 0);
    }

}
