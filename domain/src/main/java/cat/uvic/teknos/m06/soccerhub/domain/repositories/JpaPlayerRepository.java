package cat.uvic.teknos.m06.soccerhub.domain.repositories;

import cat.uvic.teknos.m06.soccerhub.domain.models.Player;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class JpaPlayerRepository implements Repository<Player, Integer>{
    private final EntityManagerFactory entityManagerFactory;

    public JpaPlayerRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(Player player) {
        if (player.getId() <= 0) {
            insert(player);
        } else {
            update(player);
        }
    }

    private void insert(Player player) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(player);
        entityManager.getTransaction().commit();
    }

    private void update(Player player) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(player);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Integer id) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        var player = entityManager.find(Player.class, id);
        if (player != null) {
            entityManager.remove(player);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public Player getById(Integer id) {
        var entityManager = entityManagerFactory.createEntityManager();
        var player = entityManager.find(Player.class, id);
        entityManager.close();

        return player;
    }

    @Override
    public List<Player> getAll() {
        var entityManager = entityManagerFactory.createEntityManager();
        var query = entityManager.createQuery("SELECT player FROM Player player");
        return query.getResultList();
    }

}
