package cat.uvic.teknos.m06.soccerhub.domain.repositories;

import cat.uvic.teknos.m06.soccerhub.domain.models.Country;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class JpaCountryRepository implements Repository<Country, Integer>{
    private final EntityManagerFactory entityManagerFactory;

    public JpaCountryRepository(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
    }
    @Override
    public void save(Country country) {
        if (country.getId() <= 0) {
            insert(country);
        } else {
            update(country);
        }
    }

    private void insert(Country country) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(country);
        entityManager.getTransaction().commit();
    }

    private void update(Country country) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(country);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Integer id) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        var country = entityManager.find(Country.class, id);
        if (country != null) {
            entityManager.remove(country);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public Country getById(Integer id) {
        var entityManager = entityManagerFactory.createEntityManager();
        var country = entityManager.find(Country.class, id);
        entityManager.close();

        return country;
    }

    @Override
    public List<Country> getAll() {
        var entityManager = entityManagerFactory.createEntityManager();
        var query = entityManager.createQuery("SELECT country FROM Country country");
        return query.getResultList();
    }
}
