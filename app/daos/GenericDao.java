package daos;

import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.persistence.EntityManager;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

/**
 * Generic Dao.
 *
 * This supplies basic CRUD operations.
 */
public interface GenericDao<Entity,ID> {


    EntityManager getEm();

    default Class<Entity> getEntityType() {
        ParameterizedType entityType = Arrays.stream(ProductDao.class.getGenericInterfaces())
                .map(t -> ((ParameterizedType)t))
                .filter(t -> t.getRawType().equals(GenericDao.class)).findFirst().get();
        Type t = entityType.getActualTypeArguments()[0];
        return (Class<Entity>)t;
    }

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
