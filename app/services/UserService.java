package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import daos.UserBranchDao;
import daos.UserDao;
import helpers.NoTxJPA;
import models.User;
import models.UserBranch;
import play.i18n.MessagesApi;
import play.mvc.Http;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;


/**
 * Created by zenith on 10/25/16.
 */
@Singleton
public class UserService {
    private final UserDao userDao;
    private final NoTxJPA jpa;
    private final MessagesApi messagesApi;
    private final UserBranchDao userBranchDao;

    @Inject
    public UserService(UserDao userDao, NoTxJPA jpa, MessagesApi messagesApi, UserBranchDao userBranchDao) {
        this.userDao = userDao;
        this.jpa = jpa;
        this.messagesApi = messagesApi;
        this.userBranchDao = userBranchDao;
    }

    private String encryptPassword(String password) {
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        String encodedPassword = Base64.getEncoder().encodeToString(passwordBytes);

        return encodedPassword;
    }

    public User create(User inputUser) {
        return jpa.withDefaultEm(() -> {
                    String password = inputUser.getPassword();
                    inputUser.setPassword(encryptPassword(inputUser.getPassword()));
                    User user = userDao.create(inputUser);
                    user.setPassword(password);
                    return user;
                }
        );
    }

    public List<User> getUserGroupByBranch() {
        return userDao.getUserGroupByBranch();
    }

    public User update(User formUser) {
        play.i18n.Lang lang = Http.Context.current().lang();

        return jpa.withDefaultEm(() -> {
            User user = userDao.getUserByEmail(formUser.getEmail());
            String password = null;

            if (user == null) {
                throw new IllegalStateException(messagesApi.get(lang, "userNotFound"));
            }


            if (!formUser.getBranch().equals(user.getBranch())) {
                UserBranch userBranch = new UserBranch();
                userBranch.setBranch(formUser.getBranch());

                UserBranch newBranch = userBranchDao.getUserBranch(formUser.getBranch());
                Integer userNo = (newBranch == null) ? 0: newBranch.getNoOfUser();
                if (user.getBranch() == null) {
                    userBranch.setNoOfUser(userNo.intValue() + 1);
                }
                else {
                    userBranch.setNoOfUser(userNo.intValue() + 1);
                    UserBranch existedbranch = userBranchDao.getUserBranch(user.getBranch());
                    existedbranch.setNoOfUser(existedbranch.getNoOfUser().intValue() - 1);
                    userBranchDao.create(existedbranch);
                }
                userBranchDao.create(userBranch);
            }

            user.setName(formUser.getName().trim());
            if (!formUser.getBranch().trim().equals(null)) {
                user.setBranch(formUser.getBranch().trim());
            }
            if (formUser.getPassword() != null){
                password = formUser.getPassword();
                user.setPassword(encryptPassword(formUser.getPassword()));
            }
            userDao.update(user);
            if (password != null) {
                user.setPassword(password);
            }

            return user;
        });
    }
}
