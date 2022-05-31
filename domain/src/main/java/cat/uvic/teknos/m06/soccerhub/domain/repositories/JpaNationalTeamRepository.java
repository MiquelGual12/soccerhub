package cat.uvic.teknos.m06.soccerhub.domain.repositories;

import cat.uvic.teknos.m06.soccerhub.domain.models.Country;
import cat.uvic.teknos.m06.soccerhub.domain.models.NationalTeam;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class JpaNationalTeamRepository implements Repository<NationalTeam, Integer> {
    private final EntityManagerFactory entityManagerFactory;

    public JpaNationalTeamRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(NationalTeam nationalTeam) {
        if (nationalTeam.getId() <= 0) {
            insert(nationalTeam);
        } else {
            update(nationalTeam);
        }
    }

    private void insert(NationalTeam nationalTeam) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(nationalTeam);
        entityManager.getTransaction().commit();
    }

    private void update(NationalTeam country) {
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
    public List<NationalTeam> getAll() {
        var entityManager = entityManagerFactory.createEntityManager();
        var query = entityManager.createQuery("SELECT nationalTeam FROM NationalTeam nationalTeam");
        return query.getResultList();
    }

}
