package pl.saz.service.users;

import pl.saz.model.users.UserModel;

import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
public interface UserService {
    UserModel getUserByName(String name);
    UserModel saveUser(UserModel usr);
    UserModel updateUser(UserModel usr);
    List<UserModel> getAllUser();
    boolean deleteUser(UserModel usr);
    boolean checkUserExists(String username, String email);
    boolean checkUsernameExists(String username);
}
