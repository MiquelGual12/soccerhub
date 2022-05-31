package cat.uvic.teknos.m06.soccerhub.domain.repositories;

import cat.uvic.teknos.m06.soccerhub.domain.models.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

public class JpaCountryRepositoryTest {
    public static final int MODEL_TO_DELETE = 2;
    private static EntityManagerFactory entityManagerFactory;
    private static Repository<Country, Integer> repository;

    @BeforeAll
    static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("bandhub_mysql");
        repository = new JpaCountryRepository(entityManagerFactory);
    }

    @Test
    void saveInsert() {
        var country = new Country();
        country.setName("Spain");
        country.setId(1);
        country.setPoblation(47350000);

        assertDoesNotThrow(() -> {
            repository.save(country);
        });

        assertTrue(country.getId() > 0);
    }

    @Test
    void saveUpdate() {
        var country = new Country();
        country.setId(1);
        country.setName("United States");

        assertDoesNotThrow(() -> {
            repository.save(country);
        });

        var entityManager = entityManagerFactory.createEntityManager();
        var modifiedCountry = entityManager.find(Country.class,1 );

        assertEquals("", modifiedCountry.getName());
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
        var country = repository.getById(2);

        assertNotNull(country);
    }

    @Test
    void getAll() {
        var countries = repository.getAll();

        assertNotNull(countries);
        assertTrue(countries.size() > 0);
    }

}
