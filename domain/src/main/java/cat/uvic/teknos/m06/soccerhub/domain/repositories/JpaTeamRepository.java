package cat.uvic.teknos.m06.soccerhub.domain.repositories;

import cat.uvic.teknos.m06.soccerhub.domain.models.Team;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class JpaTeamRepository implements Repository<Team, Integer> {
    private final EntityManagerFactory entityManagerFactory;

    public JpaTeamRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(Team team) {
        if (team.getId() <= 0) {
            insert(team);
        } else {
            update(team);
        }
    }

    private void insert(Team team) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(team);
        entityManager.getTransaction().commit();
    }

    private void update(Team team) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(team);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Integer id) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        var team = entityManager.find(Team.class, id);
        if (team != null) {
            entityManager.remove(team);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public Team getById(Integer id) {
        var entityManager = entityManagerFactory.createEntityManager();
        var team = entityManager.find(Team.class, id);
        entityManager.close();

        return team;
    }

    @Override
    public List<Team> getAll() {
        var entityManager = entityManagerFactory.createEntityManager();
        var query = entityManager.createQuery("SELECT team FROM Team team");
        return query.getResultList();
    }
}
