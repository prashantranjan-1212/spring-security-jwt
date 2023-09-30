package dev.prashant.controller;


import dev.prashant.TokenService;
import dev.prashant.configuration.WebSecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest({HelloController.class, AuthController.class})
@Import({WebSecurityConfiguration.class, TokenService.class})
class HelloControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void rootWhenUnAuthenticatedThen401() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void rootWhenAuthenticatedThenSayHelloUser() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/token")
                                .with(httpBasic("Prashant", "MySecretPassword")))
                .andExpect(status().isOk()).andReturn();

        String token = mvcResult.getResponse().getContentAsString();

        this.mockMvc.perform(get("/").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, Prashant"));

    }

    @Test
    @WithMockUser
    void rootWhenMockUserStatusIsOk() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk());
    }

}