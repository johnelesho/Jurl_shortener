package com.elsoft.assessment.jurlshortener.controllers;

import com.elsoft.assessment.jurlshortener.dto.UrlRequest;
import com.elsoft.assessment.jurlshortener.service.UrlShortenerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UrlControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UrlShortenerService urlService;
    String urlBase = "jurl.el/";

    @Test
    public void givenFullUrlReturnStatusCreated() throws Exception {
        UrlRequest fullUrl = new UrlRequest("https://example.com/foo");

        mvc.perform(post("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl)))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenFullUrlReturnStringWithShortUrlStartWithAGivenValue() throws Exception {
        UrlRequest fullUrl = new UrlRequest("https://example.com/foo");

        mvc.perform(post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(fullUrl)))
                .andExpect(status().isCreated())
                .andExpect(content().string(startsWith(urlBase)));

    }

    @Test
    public void givenFullUrlReturnStringWithShortUrlValueHasHttp() throws Exception {
        UrlRequest fullUrl = new UrlRequest("https://example.com/foo");
        mvc.perform(post("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl)))
                .andExpect(status().isCreated())
                .andExpect(content().string(startsWith(urlBase)));
    }

    @Test
    public void shouldReturnSameUrlIfAlreadyExists() throws Exception {
        UrlRequest fullUrl = new UrlRequest("https://example.com/foo");

        String shortUrl1 = mvc.perform(post("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl)))
                .andExpect(status().isCreated())
                .andExpect(content().string(startsWith(urlBase)))
                .andReturn().getResponse().getContentAsString();

        String shortUrl2 = mvc.perform(post("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl)))
                .andExpect(status().isCreated())
                .andExpect(content().string(startsWith(urlBase)))
                .andReturn().getResponse().getContentAsString();

        assertThat(shortUrl1).isEqualTo(shortUrl2);
    }

    @Test
    public void shouldNotInsertFullUrlIfInvalidUrl() throws Exception {
        UrlRequest fullUrl1 = new UrlRequest("htt p3://example.com/foo1&324");
        UrlRequest fullUrl2 = new UrlRequest("https://example.com-foo2");

        String shortUrl1 = mvc.perform(post("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl1)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(startsWith("Invalid")))
                .andReturn().getResponse().getContentAsString();

        String shortUrl2 = mvc.perform(post("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl2)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(startsWith("Invalid")))
                .andReturn().getResponse().getContentAsString();


        assertNotEquals(shortUrl1, shortUrl2);
    }

    public static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

}