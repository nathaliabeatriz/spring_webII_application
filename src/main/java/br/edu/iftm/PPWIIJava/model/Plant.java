package br.edu.iftm.PPWIIJava.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name="plants")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "nome_personalizado", nullable = false)
    @NotBlank(message = "Nome é um campo obrigatório")
    @Size(min = 3, max = 100, message = "Nome da planta deve conter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Espécie é um campo obrigatório")
    @Size(min = 3, max = 100, message = "Espécie da planta deve conter entre 3 e 100 caracteres")
    @Column(name = "especie", nullable = false)
    private String especie;

    @Column(name = "data_aquisicao", nullable = false)
    @PastOrPresent(message = "Data inválida")
    private Date dataAquisicao;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private User usuario;
}
