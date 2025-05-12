package com.worstmovie.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetMinMaxIntervals() throws Exception {
        mockMvc.perform(get("/api/movies/min-max-winner"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {
                    "min": [
                    {
                        "producer": "Joel Silver",
                        "interval": 1,
                        "previousWin": 1990,
                        "followingWin": 1991
                    }
                    ],
                    "max": [
                    {
                        "producer": "Matthew Vaughn",
                        "interval": 13,
                        "previousWin": 2002,
                        "followingWin": 2015
                    }
                    ]
                }
            """));
    }
}
