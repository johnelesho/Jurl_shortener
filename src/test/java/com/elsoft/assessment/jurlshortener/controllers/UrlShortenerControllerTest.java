package com.elsoft.assessment.jurlshortener.controllers;

import com.elsoft.assessment.jurlshortener.dto.UrlRequest;
import com.elsoft.assessment.jurlshortener.entity.UrlEntity;
import com.elsoft.assessment.jurlshortener.repository.UrlRepository;
import com.elsoft.assessment.jurlshortener.service.UrlShortenerService;
import com.elsoft.assessment.jurlshortener.service.impl.UrlShortenerServiceImpl;
import com.elsoft.assessment.jurlshortener.service.impl.UrlShortenerUtilsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static com.elsoft.assessment.jurlshortener.controllers.UrlControllerIntegrationTest.asJsonString;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class UrlShortenerControllerTest {
    @Autowired
    private MockMvc mvc;

    @Mock
    private UrlShortenerService urlService;
@Mock
    UrlRepository urlRepository;



    @Test
            @DisplayName("Test the endpoint to convert to short url")
            public void testConvertToShortUrl1() throws Exception {
        UrlRequest fullUrl = new UrlRequest("https://example.com/foo");

        Mockito.when(urlService.generateShortLink(fullUrl)).thenReturn(urlBase+"gU");
        mvc.perform(post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(fullUrl)))
                .andExpect(status().isCreated())
                .andExpect(content().string(startsWith(urlBase)));

    }
    String urlBase = "jurl.el/";
    /**
     * Method under test: {@link UrlShortenerController#convertToShortUrl(UrlRequest)}
     */
    @Test
    @DisplayName("Test convert to Url")
    public void testConvertToShortUrl() {



        when(urlRepository.findByUrl(any())).thenReturn(null);
        lenient().when(urlRepository.save(any())).thenReturn(new UrlEntity(2L,"https://example.org/example", LocalDateTime.now(),LocalDateTime.now().plusDays(3)));
        UrlShortenerController urlShortenerController = new UrlShortenerController(
                new UrlShortenerServiceImpl(urlRepository, new UrlShortenerUtilsServiceImpl()));
        urlShortenerController.convertToShortUrl(new UrlRequest("https://example.org/example"));
    }
    @Test
    @DisplayName("Test convert to Url")
    public void testReturnConvertedWhenUrlExist() {



        when(urlRepository.findByUrl(any())).thenReturn(new UrlEntity(2L,"https://example.org/example", LocalDateTime.now(),LocalDateTime.now().plusDays(3)));
        lenient().when(urlRepository.save(any())).thenReturn(new UrlEntity(2L,"https://example.org/example", LocalDateTime.now(),LocalDateTime.now().plusDays(3)));
        UrlShortenerController urlShortenerController = new UrlShortenerController(
                new UrlShortenerServiceImpl(urlRepository, new UrlShortenerUtilsServiceImpl()));
        urlShortenerController.convertToShortUrl(new UrlRequest("https://example.org/example"));
    }

    /**
     * Method under test: {@link UrlShortenerController#convertToShortUrl(UrlRequest)}
     */
    @Test
    public void testConvertToShortUrl2() {

        UrlEntity urlEntity = new UrlEntity("https://example.org/example");
        urlEntity.setId(123L);

        when(urlRepository.findByUrl(any())).thenReturn(urlEntity);
        lenient().when(urlRepository.save(any())).thenReturn(new UrlEntity("https://example.org/example"));
        UrlShortenerController urlShortenerController = new UrlShortenerController(
                new UrlShortenerServiceImpl(urlRepository, new UrlShortenerUtilsServiceImpl()));
        ResponseEntity<String> actualConvertToShortUrlResult = urlShortenerController
                .convertToShortUrl(new UrlRequest("https://example.org/example"));
        assertEquals("jurl.el/wy", actualConvertToShortUrlResult.getBody());
        assertEquals(HttpStatusCode.valueOf(201), actualConvertToShortUrlResult.getStatusCode());
        assertTrue(actualConvertToShortUrlResult.getHeaders().isEmpty());
        verify(urlRepository).findByUrl(any());
    }

    /**
     * Method under test: {@link UrlShortenerController#getAll(String, LocalDateTime, LocalDateTime)}
     */
    @Test
    public void testGetAll() {

        ArrayList<UrlEntity> urlEntityList = new ArrayList<>();
        when(urlRepository.findAllByQuery(any(), any(), any()))
                .thenReturn(urlEntityList);
        UrlShortenerController urlShortenerController = new UrlShortenerController(
                new UrlShortenerServiceImpl(urlRepository, new UrlShortenerUtilsServiceImpl()));
        LocalDateTime createdDate = LocalDateTime.of(1, 1, 1, 1, 1);
        ResponseEntity<?> actualAll = urlShortenerController.getAll("https://example.org/example", createdDate,
                LocalDateTime.of(1, 1, 1, 1, 1));
        assertEquals(urlEntityList, actualAll.getBody());
        assertEquals(HttpStatusCode.valueOf(200), actualAll.getStatusCode());
        assertTrue(actualAll.getHeaders().isEmpty());
        verify(urlRepository).findAllByQuery(any(), any(), any());
    }

    /**
     * Method under test: {@link UrlShortenerController#getAll(String, LocalDateTime, LocalDateTime)}
     */
    @Test

    public void testGetAll2() {

        UrlShortenerController urlShortenerController = new UrlShortenerController(
                new UrlShortenerServiceImpl(urlRepository, new UrlShortenerUtilsServiceImpl()));
        LocalDateTime createdDate = LocalDateTime.of(1, 1, 1, 1, 1);
        urlShortenerController.getAll("https://example.org/example", createdDate, LocalDateTime.of(1, 1, 1, 1, 1));
    }

    /**
     * Method under test: {@link UrlShortenerController#getAndRedirect(String)}
     */
    @Test
    public void testGetAndRedirect() {

        when(urlRepository.findById(any())).thenReturn(Optional.of(new UrlEntity("https://example.org/example")));

        (new UrlShortenerController(new UrlShortenerServiceImpl(urlRepository, new UrlShortenerUtilsServiceImpl())))
                .getAndRedirect(urlBase+"89Gh6");
    }

    /**
     * Method under test: {@link UrlShortenerController#getAndRedirect(String)}
     */
    @Test
    public void testGetAndRedirect2() {


        when(urlRepository.findById(any())).thenReturn(Optional.of(new UrlEntity("https://example.org/example")));
        UrlShortenerUtilsServiceImpl urlShortenerUtilsServiceImpl = mock(UrlShortenerUtilsServiceImpl.class);
        when(urlShortenerUtilsServiceImpl.retrieveIdFromString(any())).thenReturn(1L);
        ResponseEntity<Void> actualAndRedirect = (new UrlShortenerController(
                new UrlShortenerServiceImpl(urlRepository, urlShortenerUtilsServiceImpl)))
                .getAndRedirect("https://example.org/example");
        assertNull(actualAndRedirect.getBody());
        assertEquals(HttpStatusCode.valueOf(200), actualAndRedirect.getStatusCode());
        assertEquals(1, actualAndRedirect.getHeaders().size());
        verify(urlRepository).findById(any());
        verify(urlShortenerUtilsServiceImpl).retrieveIdFromString(any());
    }

    /**
     * Method under test: {@link UrlShortenerController#getAndRedirect(String)}
     */
    @Test
    public void testGetAndRedirect3() {

        when(urlRepository.findById(any())).thenReturn(Optional.of(new UrlEntity("https://github.com/johnelesho/Jurl_shortener")));
        UrlShortenerUtilsServiceImpl urlShortenerUtilsServiceImpl = mock(UrlShortenerUtilsServiceImpl.class);
        when(urlShortenerUtilsServiceImpl.retrieveIdFromString(any())).thenReturn(1L);
        (new UrlShortenerController(new UrlShortenerServiceImpl(urlRepository, urlShortenerUtilsServiceImpl)))
                .getAndRedirect("https://example.org/example");
    }
}

