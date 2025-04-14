package br.edu.iftm.PPWIIJava.service;

import java.util.List;

import br.edu.iftm.PPWIIJava.model.User;

public interface UserService {
    List<User> getAllUsers();
    void saveUser(User user);
    User getUserById(long id);
    void deleteUserById(long id);
}
