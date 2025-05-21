package br.edu.iftm.PPWIIJava.service;

import java.util.List;

import br.edu.iftm.PPWIIJava.model.User;

public interface UserService {
    public Integer saveUser(User user);
    List<User> getAllUsers();
}
