package cat.uvic.teknos.m06.soccerhub.domain.repositories;

import cat.uvic.teknos.m06.soccerhub.domain.models.Country;
import cat.uvic.teknos.m06.soccerhub.domain.models.League;
import cat.uvic.teknos.m06.soccerhub.domain.models.League;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class JpaLeagueRepository implements Repository<League, Integer> {
    private final EntityManagerFactory entityManagerFactory;

    public JpaLeagueRepository(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(League league) {
        if (league.getId() <= 0) {
            insert(league);
        } else {
            update(league);
        }
    }

    private void insert(League league) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(league);
        entityManager.getTransaction().commit();
    }

    private void update(League country) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(country);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Integer id) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        var league = entityManager.find(League.class, id);
        if (league != null) {
            entityManager.remove(league);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public League getById(Integer id) {
        var entityManager = entityManagerFactory.createEntityManager();
        var league = entityManager.find(League.class, id);
        entityManager.close();

        return league;
    }

    @Override
    public List<League> getAll() {
        var entityManager = entityManagerFactory.createEntityManager();
        var query = entityManager.createQuery("SELECT league FROM League league");
        return query.getResultList();
    }
}
