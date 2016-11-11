package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import daos.UserBranchDao;
import daos.UserDao;
import exceptions.ApplicationException;
import helpers.SupplyEM;
import models.User;
import models.UserBranch;

import java.util.List;


/**
 * Created by zenith on 10/25/16.
 */
@Singleton
@SupplyEM // TODO : for more easy service writing. not neet NoTxJpa#withDefaultEm in service.
public class UserService {
    private final UserDao userDao;
    private final UserBranchDao userBranchDao;
    private final PasswordEncrypter encrypter;

    @Inject
    public UserService(UserDao userDao,  UserBranchDao userBranchDao, PasswordEncrypter encrypter) {
        this.userDao = userDao;
        this.userBranchDao = userBranchDao;
        this.encrypter = encrypter;
    }

    private String encryptPassword(String password) {
        // TODO : Base64 is not encryption. You should use strong and common encrypt library. I write BCrypt example as a encrypter.
        //byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        //String encodedPassword = Base64.getEncoder().encodeToString(passwordBytes);

        return encrypter.encrypt(password);
    }

    public User create(User inputUser) {
        String password = inputUser.getPassword();
        inputUser.setPassword(encryptPassword(inputUser.getPassword()));
        User user = userDao.create(inputUser);
        // TODO : Shouldn't return password.
        // user.setPassword(password);
        user.setPassword(null);
        return user;


    }

    public List<User> getUserGroupByBranch() {
           return userDao.getUserGroupByBranch();
    }

    public User getUser(String email) {return userDao.selectOne(email);}

    public User update(User formUser) {

        User user = userDao.selectOne(formUser.getEmail());
        String password = null;

        if (user == null) {
            // TODO : this should common exception (e.g, ApplicationException)that has messageCode.
            // TODO ; This is better, because, service doesn't use messageApi and Lang.
            throw new ApplicationException("userNotFound");
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
            user.setPassword(encryptPassword(formUser.getPassword()));
        }
        userDao.update(user);
        user.setPassword(null);

        return user;
    }
}
