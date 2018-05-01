package pl.saz.model.users;

/**
 * Created by maciej on 01.05.18.
 */
public class UserModel {
    private Long _id;
    private String _name;
    private String _surname;
    private String _phone;
    private String _desc;
    private UserRole _role;
    private String _login;
    private String _password;

    public UserModel() {}

    public UserModel(String _name, String _surname, String _phone, String _desc, UserRole _role,String _login,String _password) {
        this._name = _name;
        this._surname = _surname;
        this._phone = _phone;
        this._desc = _desc;
        this._role = _role;
        this._login = _login;
        this._password = _password;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_surname() {
        return _surname;
    }

    public void set_surname(String _surname) {
        this._surname = _surname;
    }

    public String get_phone() {
        return _phone;
    }

    public void set_phone(String _phone) {
        this._phone = _phone;
    }

    public String get_desc() {
        return _desc;
    }

    public void set_desc(String _desc) {
        this._desc = _desc;
    }

    public UserRole get_role() {
        return _role;
    }

    public void set_role(UserRole _role) {
        this._role = _role;
    }

    public String get_login() {
        return _login;
    }

    public void set_login(String _login) {
        this._login = _login;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }
}
