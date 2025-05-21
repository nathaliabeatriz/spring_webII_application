package br.edu.iftm.PPWIIJava.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.iftm.PPWIIJava.model.Plant;
import br.edu.iftm.PPWIIJava.repository.PlantRepository;
import br.edu.iftm.PPWIIJava.service.PlantService;

@Service
public class PlantServiceImpl implements PlantService{
    @Autowired
    private PlantRepository plantRepository;

    @Override
    public List<Plant> getAllPlants(){
        return plantRepository.findAll();
    }

    @Override
    public void savePlant(Plant Plant){
        plantRepository.save(Plant);
    }

    @Override
    public Plant getPlantById(long id){
        Optional<Plant> optional = plantRepository.findById(id);
        Plant Plant = null;
        if(optional.isPresent()){
            Plant = optional.get();
        } else{
            throw new RuntimeException("Planta não encontrada para o id " + id);
        }
        return Plant;
    }

    @Override
    public void deletePlantById(long id){
        plantRepository.deleteById(id);
    }
}
