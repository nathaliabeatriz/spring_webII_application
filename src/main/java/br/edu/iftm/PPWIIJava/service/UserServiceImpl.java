package br.edu.iftm.PPWIIJava.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.iftm.PPWIIJava.model.User;
import br.edu.iftm.PPWIIJava.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user){
        userRepository.save(user);
    }

    @Override
    public User getUserById(long id){
        Optional<User> optional = userRepository.findById(id);
        User user = null;
        if(optional.isPresent()){
            user = optional.get();
        } else{
            throw new RuntimeException("Usuário não encontrado para o id " + id);
        }
        return user;
    }

    @Override
    public void deleteUserById(long id){
        userRepository.deleteById(id);
    }
}
