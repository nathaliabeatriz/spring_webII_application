package br.edu.iftm.PPWIIJava.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iftm.PPWIIJava.model.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findUserByEmail(String email);
}
