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
class CarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCarBrand_shouldReturnPorsche() throws Exception {
        mockMvc.perform(get("/api/cars/porsche"))
                .andExpect(status().isOk())
                .andExpect(content().string("porsche"));
    }

    @Test
    void getCarBrand_shouldReturnBadRequestForWrongBrand() throws Exception {
        mockMvc.perform(get("/api/cars/bmw"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Only 'porsche' allowed"))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    void getAllCars_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No Cars found"))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }
}