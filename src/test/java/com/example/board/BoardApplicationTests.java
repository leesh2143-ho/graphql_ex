package com.example.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BoardApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetBoards() throws Exception {
        mockMvc.perform(get("/api/boards?page=0&size=10&sort=createdAt,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(10))
                .andExpect(jsonPath("$.content[0].title").value("Title 20"));
    }

    @Test
    void testGetBoard() throws Exception {
        mockMvc.perform(get("/api/boards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title 1"));
    }

    @Test
    void testSearchBoards() throws Exception {
        // Search for "Title 1" should match "Title 1" and "Title 10" through "Title 19"
        mockMvc.perform(get("/api/boards?keyword=Title 1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(11));
    }
}