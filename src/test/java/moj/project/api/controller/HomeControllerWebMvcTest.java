package moj.project.api.controller;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = HomeController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class HomeControllerWebMvcTest {
    private MockMvc mockMvc;

    @Test
    void homeWorksCorrectly() throws Exception {
        //given, when, then
        mockMvc.perform(get(HomeController.HOME))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }
}
