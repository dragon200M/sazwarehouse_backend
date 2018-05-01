package pl.saz.dao.users;

import pl.saz.model.users.UserModel;

import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
public interface UserDao {
    UserModel getUserByName(String name);
    UserModel saveUser(UserModel usr);
    UserModel updateUser(UserModel usr);
    List<UserModel> getAllUser();
    boolean deleteUser(UserModel usr);
}
