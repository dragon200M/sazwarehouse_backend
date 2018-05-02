package pl.saz.service.users;

import pl.saz.model.users.UserModel;

import java.util.List;

/**
 * Created by maciej on 02.05.18.
 */
public class UserServiceImpl implements UserService{
    @Override
    public UserModel getUserByName(String name) {
        return null;
    }

    @Override
    public UserModel saveUser(UserModel usr) {
        return null;
    }

    @Override
    public UserModel updateUser(UserModel usr) {
        return null;
    }

    @Override
    public List<UserModel> getAllUser() {
        return null;
    }

    @Override
    public boolean deleteUser(UserModel usr) {
        return false;
    }

    @Override
    public boolean checkUserExists(String username, String email) {
        return false;
    }

    @Override
    public boolean checkUsernameExists(String username) {
        return false;
    }
}
