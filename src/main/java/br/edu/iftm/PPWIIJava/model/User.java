package br.edu.iftm.PPWIIJava.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

//@Data - escreve os getters e setters e outros métodos comuns da classe
//@Entity - indica que é uma classe do tipo JPA (que é mapeada para uma tabela do banco)
@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "user_role")
    private List<String> roles;
}
