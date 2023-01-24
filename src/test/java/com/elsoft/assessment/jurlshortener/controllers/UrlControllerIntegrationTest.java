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

import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.startsWith;
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
                .andReturn().getResponse().getContentAsString();

        String shortUrl2 = mvc.perform(post("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl2)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(startsWith("Invalid")))
                .andReturn().getResponse().getContentAsString();


        assertThat(shortUrl1).isNotEqualTo(shortUrl2);
    }
    @Test
    public void shouldNotInsertFullUrlIfInvalidRequestBody() throws Exception {
        UrlRequest fullUrl1 = new UrlRequest("https://github.com/johnelesho/Jurl_shortener", LocalDateTime.now().plusHours(28), 30L);
        UrlRequest fullUrl2 = new UrlRequest("hhttps://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310/2.14.1", null, -72L);
        UrlRequest fullUrl3 = new UrlRequest("hhttps://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310/2.14.1", new Date(), null);

        String shortUrl1 = mvc.perform(post("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl1)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        String shortUrl2 = mvc.perform(post("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl2)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(startsWith("Invalid")))
                .andReturn().getResponse().getContentAsString();

        String shortUrl3 = mvc.perform(post("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl3)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(startsWith("Invalid")))
                .andReturn().getResponse().getContentAsString();

assertThat(shortUrl1).isEqualTo("Enter either an expiry date or active hours (default is 48 hours)");
assertThat(shortUrl2).isEqualTo("Invalid Expiry Date");
        assertThat(shortUrl1).isNotEqualTo(shortUrl2);
    }

    public static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

}