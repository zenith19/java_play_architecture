package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import daos.UserBranchDao;
import daos.UserDao;
import exceptions.ApplicationException;
import helpers.SupplyEM;
import models.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
    // TODO : note, asAdmin argument is test purpose.
    public User create(User inputUser, boolean asAdmin) {
        String password = inputUser.getPassword();
        inputUser.setPassword(encryptPassword(inputUser.getPassword()));

        if (asAdmin) {
            inputUser.setRoles(Stream.of("admin","default").collect(Collectors.toSet()));
        } else {

            inputUser.setRoles(Stream.of("default").collect(Collectors.toSet()));
        }

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
        
        user.setName(formUser.getName().trim());
        if (formUser.getBranch().trim() != null) {
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
