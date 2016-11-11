package daos;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import helpers.NoTxJPA;
import models.UserBranch;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rownak on 11/4/16.
 */
@Singleton
public class UserBranchDao implements GenericDao<UserBranch, String> {
    private final NoTxJPA jpa;

    @Inject
    public UserBranchDao(NoTxJPA jpa) {
        this.jpa = jpa;
    }

    @Override
    public EntityManager getEm() {
        return jpa.currentEm();
    }

    @Override
    public Class<UserBranch> getEntityType() {
        ParameterizedType entityType = Arrays.stream(UserBranchDao.class.getGenericInterfaces())
                .map(t -> ((ParameterizedType)t))
                .filter(t -> t.getRawType().equals(GenericDao.class)).findFirst().get();
        Type t = entityType.getActualTypeArguments()[0];
        return (Class<UserBranch>) t;
    }

    public List<UserBranch> getBranchWithCondition() {
        EntityManager entityManager = jpa.currentEm();
//        QUserBranch qUserBranch = QUserBranch.userBranch;
//        JPQLQuery query = new JPAQuery(entityManager);
//        List<UserBranch> userBranchList = query.from(qUserBranch)
//                .where(qUserBranch.noOfUsers.goe(1))
//                .list(qUserBranch);

        List<UserBranch> userBranchList = (List<UserBranch>) entityManager.createQuery("select u from UserBranch u").getResultList();

        return userBranchList;
    }
}
