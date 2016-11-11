package daos;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Generic Dao.
 *
 * This supplies basic CRUD operations.
 */
public interface GenericDao<Entity,ID> {
    EntityManager getEm();

    @SuppressWarnings("unchecked")
    Class<Entity> getEntityType();

    default Entity selectOne(ID id) {
        return getEm().find(getEntityType(), id);
    }

    default List<Entity> selectAll() {
        String tName = getEntityType().getSimpleName();
        return getEm().createQuery("Select t from " + tName + " t").getResultList();
    }

    default Entity create(Entity entity) {
        getEm().persist(entity);

        return entity;
    }

    default Entity update(Entity entity) {
        getEm().merge(entity);

        return entity;
    }
    default void delete(Entity entity) {
        getEm().remove(entity);
    }

}
