package de.neuefische.springexceptionhandlingtask;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAnimalSpecies_shouldReturnDog() throws Exception {
        mockMvc.perform(get("/api/animals/dog"))
                .andExpect(status().isOk())
                .andExpect(content().string("dog"));
    }

    @Test
    void getAnimalSpecies_shouldReturnBadRequestForWrongSpecies() throws Exception {
        mockMvc.perform(get("/api/animals/cat"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Only 'dog' is allowed"))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    void getAllAnimals_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/animals"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No Animals found"))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }
}