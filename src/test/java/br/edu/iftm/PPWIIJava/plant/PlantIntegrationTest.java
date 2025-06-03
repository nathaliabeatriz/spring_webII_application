package br.edu.iftm.PPWIIJava.plant;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.edu.iftm.PPWIIJava.model.Plant;
import br.edu.iftm.PPWIIJava.model.User;
import br.edu.iftm.PPWIIJava.repository.PlantRepository;
import br.edu.iftm.PPWIIJava.repository.UserRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Usa application-test.properties
@Transactional // Limpa o banco após cada teste
public class PlantIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private UserRepository userRepository;

    private User createUser(){
        User user = new User();
        user.setName("Usuário");
        user.setEmail("usuario@gmail.com");
        user.setPassword("usuario");
        user.setRoles(null);

        userRepository.save(user);
        return user;
    }

    @Test
    @WithMockUser(authorities = { "Admin" })
    void testSaveplantIntegration() throws Exception {

        Plant plantA = new Plant();
        plantA.setNome("Planta A");
        plantA.setEspecie("Especie");
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date data = sdf.parse("2025-06-01");
            plantA.setDataAquisicao(data);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        plantA.setUsuario(createUser());

        mockMvc.perform(post("/plant/save")
                .with(csrf())
                .flashAttr("plant", plantA))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/plant"));

        // Verifica no banco se foi salvo
        assertTrue(plantRepository.findAll()
                .stream()
                .anyMatch(p -> "Planta A".equals(p.getNome())));
    }
}