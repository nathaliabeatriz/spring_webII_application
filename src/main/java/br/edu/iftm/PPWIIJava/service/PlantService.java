package br.edu.iftm.PPWIIJava.service;

import java.util.List;

import br.edu.iftm.PPWIIJava.model.Plant;

public interface PlantService {
    List<Plant> getAllPlants();
    void savePlant(Plant plant);
    Plant getPlantById(long id);
    void deletePlantById(long id);
}
