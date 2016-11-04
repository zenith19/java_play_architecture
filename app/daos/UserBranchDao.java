package daos;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import helpers.NoTxJPA;
import models.UserBranch;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by rownak on 11/4/16.
 */
@Singleton
public class UserBranchDao {
    private final NoTxJPA jpa;

    @Inject
    public UserBranchDao(NoTxJPA jpa) {
        this.jpa = jpa;
    }

    public UserBranch getUserBranch(String branch) {
        EntityManager entityManager = jpa.currentEm();
        UserBranch userBranch = entityManager.find(UserBranch.class, branch);

        return userBranch;
    }

    public UserBranch create(UserBranch userBranch) {
        EntityManager entityManager = jpa.currentEm();
        entityManager.persist(userBranch);

        return userBranch;
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
