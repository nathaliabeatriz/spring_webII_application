package br.edu.iftm.PPWIIJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iftm.PPWIIJava.model.Plant;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long>{

}
