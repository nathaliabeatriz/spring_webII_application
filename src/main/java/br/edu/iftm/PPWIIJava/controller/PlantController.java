package br.edu.iftm.PPWIIJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.iftm.PPWIIJava.model.Plant;
import br.edu.iftm.PPWIIJava.service.PlantService;
import br.edu.iftm.PPWIIJava.service.UserService;

@Controller
public class PlantController {
    //o Autowired injeta instância no objeto criado - não é necessário fazer o new (objeto vazio)
    @Autowired
    private PlantService plantService;
    @Autowired
    private UserService userService;
    
    @GetMapping("/plant")
    public String index(Model model) {
        model.addAttribute("plantsList", plantService.getAllPlants());
        return "plant/index";
 
    }

    //O Model permite que objetos sejam utilizados pelo Thymeleaf na view renderizada
    @GetMapping("/plant/create")
    public String create(Model model) {
        model.addAttribute("plant", new Plant());
        model.addAttribute("usersList", userService.getAllUsers());
        return "plant/create";
    }

    @PostMapping("/plant/save")
    public String postMethodName(@ModelAttribute("plant") Plant plant) {
        plantService.savePlant(plant);
        return "redirect:/plant";
    }

    @GetMapping("/plant/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.plantService.deletePlantById(id);
        return "redirect:/plant";
    }

    @GetMapping("/plant/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Plant plant = plantService.getPlantById(id);
        model.addAttribute("plant", plant);
        model.addAttribute("usersList", userService.getAllUsers());
        return "plant/edit";
    }
}
