package br.edu.iftm.PPWIIJava.plant;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import br.edu.iftm.PPWIIJava.config.TestConfig;
import br.edu.iftm.PPWIIJava.controller.PlantController;
import br.edu.iftm.PPWIIJava.model.Plant;
import br.edu.iftm.PPWIIJava.model.User;
import br.edu.iftm.PPWIIJava.service.PlantService;
import br.edu.iftm.PPWIIJava.service.UserService;

@WebMvcTest(PlantController.class)
@Import(TestConfig.class)
public class PlantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlantService plantService;
    @Autowired
    private UserService userService;

    @AfterEach
    void resetMocks() {
        reset(plantService);
        reset(userService);
    }

    private User createUser(){
        User user = new User();
        user.setId(1);
        user.setName("Usuário");
        user.setEmail("usuario@gmail.com");
        user.setPassword("usuario");
        user.setRoles(null);

        return user;
    }

    private List<Plant> testCreatePlantList(){
        Plant plantB = new Plant();
        plantB.setId(1L);
        plantB.setNome("Planta B");
        plantB.setEspecie("Especie");
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date data = sdf.parse("2025-06-01");
            plantB.setDataAquisicao(data);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        
        plantB.setUsuario(createUser());

        return List.of(plantB);
    }

    @Test
    @DisplayName("GET /plant - Listar plantas na tela index sem usuário autenticado")
    void testIndexNotAuthenticatedUser() throws Exception {
         mockMvc.perform(get("/plant"))
            .andExpect(status().isFound());
    }

    @Test
    @WithMockUser
    @DisplayName("GET /plant - Listar plantas na tela index com usuário logado")
    void testIndexAuthenticatedUser() throws Exception {
        when(plantService.getAllPlants()).thenReturn(testCreatePlantList());

        mockMvc.perform(get("/plant"))
               .andExpect(status().isOk())
               .andExpect(view().name("plant/index"))
               .andExpect(model().attributeExists("plantsList"))
               .andExpect(content().string(containsString("Listagem de Plantas")))
               .andExpect(content().string(containsString("Planta B")));
    }

    @Test
    @WithMockUser(username = "user@gmail.com", authorities = { "Admin" })
    @DisplayName("GET /plant/create - Exibe formulário de criação")
    void testCreateFormAuthorizedUser() throws Exception {
        mockMvc.perform(get("/plant/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("plant/create"))
                .andExpect(model().attributeExists("plant"))
                .andExpect(content().string(containsString("Cadastrar Planta")));
    }

    @Test
    @WithMockUser(username = "user2@gmail.com", authorities = { "Manager" })
    @DisplayName("GET /plant - Verificar o link de cadastrar para um usuario não admin logado")
    void testCreateFormNotAuthorizedUser() throws Exception {
        when(plantService.getAllPlants()).thenReturn(testCreatePlantList());
       // Obter o HTML da página renderizada pelo controlador
       mockMvc.perform(get("/plant"))
            .andExpect(status().isOk())
            .andExpect(view().name("plant/index"))
            .andExpect(model().attributeExists("plantsList"))
            .andExpect(content().string(not(containsString("<a class=\"dropdown-item\" href=\"/plant/create\">Cadastrar</a>"))));
    }

    @Test
    @WithMockUser(username = "user@gmail.com", authorities = { "Admin" })
    @DisplayName("POST /plant/save - Falha na validação e retorna para o formulário")
    void testSavePlantValidationError() throws Exception {
        Plant plant = new Plant(); // Planta sem nome, o que causará erro de validação

        mockMvc.perform(post("/plant/save")
                        .with(csrf())
                        .flashAttr("plant", plant))
                .andExpect(status().isOk())
                .andExpect(view().name("plant/create"))
                .andExpect(model().attributeHasErrors("plant"));

        verify(plantService, never()).savePlant(any(Plant.class));
    }

    @Test
    @WithMockUser(username = "user@gmail.com", authorities = { "Admin" })
    @DisplayName("POST /plant/save - Planta válida é salva com sucesso")
    void testSaveValidPlant() throws Exception {
        Plant plant = new Plant();
        plant.setNome("Nova Planta");
        plant.setEspecie("Especie");
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date data = sdf.parse("2025-06-01");
            plant.setDataAquisicao(data);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        plant.setUsuario(createUser());

        mockMvc.perform(post("/plant/save")
                        .with(csrf())
                        .flashAttr("plant", plant))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/plant"));

        verify(plantService).savePlant(any(Plant.class));
    }
}