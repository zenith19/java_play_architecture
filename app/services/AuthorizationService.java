package services;

import com.google.inject.Singleton;
import daos.SessionDao;
import daos.UserDao;
import helpers.SupplyEM;
import models.Session;
import models.User;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by kentaro.maeda on 2016/11/12.
 */
@Singleton
@SupplyEM
public class AuthorizationService {

    private final SessionDao sessionDao;
    private final UserDao userDao;

    @Inject
    public AuthorizationService(SessionDao sessionDao, UserDao userDao) {
        this.sessionDao = sessionDao;
        this.userDao = userDao;
    }

    public boolean hasAuthorization(String token, String[] roleNames){
        // get user from token;
        Session session = sessionDao.getSession(token);
        if (session == null) {
            return false;
        }
        User user = userDao.selectOne(session.getEmail());
        if (user == null) {
            return false;
        }
        // check role name.
        return !Collections.disjoint(user.getRoles(), Arrays.asList(roleNames));

        /*   disjoint.
             1) disjoint({A,B},  {C,D}) -> true,
             2) disjoint({A,B},  {A,D}) -> false,  same element exists in 2 collection.

         */

    }

}
