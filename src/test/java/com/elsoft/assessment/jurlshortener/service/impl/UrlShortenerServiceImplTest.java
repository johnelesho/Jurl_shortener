package com.elsoft.assessment.jurlshortener.service.impl;

import com.elsoft.assessment.jurlshortener.dto.UrlRequest;
import com.elsoft.assessment.jurlshortener.entity.UrlEntity;
import com.elsoft.assessment.jurlshortener.repository.UrlRepository;
import com.elsoft.assessment.jurlshortener.service.UrlShortenerService;
import com.elsoft.assessment.jurlshortener.service.UrlShortenerUtilsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceImplTest {

    @Mock
    UrlRepository mockUrlRepository;

    @Mock
    UrlShortenerUtilsService urlShortenerUtilsService;


    UrlShortenerService urlService;

    @BeforeEach
    public void setup() {

        urlService = new UrlShortenerServiceImpl(mockUrlRepository, urlShortenerUtilsService);
    }

    @Test
    public void generateShortLinkTest() {
        var url = new UrlEntity("https://linkedln.com/in/johnelesho");
        url.setId(5L);

        when(mockUrlRepository.save(any(UrlEntity.class))).thenReturn(url);
        when(urlShortenerUtilsService.retrieveStringFromId(url.getId())).thenReturn("f");

        var urlRequest = new UrlRequest();
        urlRequest.setUrl("https://github.com/johnelesho/droneAPI");

        assertThat( urlService.generateShortLink(urlRequest)).isEqualTo("f");
    }

    @Test
    public void UrlShortenerController() {
        when(urlShortenerUtilsService.retrieveIdFromString("h")).thenReturn((long) 7);

        var url = new UrlEntity("https://github.com/johnelesho/droneAPI");
        url.setId(7L);

        when(mockUrlRepository.findById((long) 7)).thenReturn(java.util.Optional.of(url));
        assertThat(urlService.getOriginalFull("h")).isEqualTo("https://github.com/johnelesho/droneAPI");

    }

    @Test
    public void convertToShortUrlTest() {
        var url = new UrlEntity("https://github.com/zonkyio/embedded-database-spring-test/issues/131");
        url.setCreatedDate(LocalDateTime.now());
        url.setId(5L);

        when(mockUrlRepository.save(any(UrlEntity.class))).thenReturn(url);
        when(urlShortenerUtilsService.retrieveStringFromId(url.getId())).thenReturn("f");

        var urlRequest = new UrlRequest();
        urlRequest.setUrl("https://stackoverflow.com/questions/63193906/application-run-failed-org-springframework-beans-factory-beandefinitionstoreexce");

        assertThat(urlService.generateShortLink(urlRequest)).isEqualTo("f");
    }

    @Test
    public void getOriginalUrlTest() {
        when(urlShortenerUtilsService.retrieveIdFromString("h")).thenReturn((long) 7);

        var url = new UrlEntity("https://stackoverflow.com/questions/63193906/application-run-failed-org-springframework-beans-factory-beandefinitionstoreexce");
        url.setCreatedDate(LocalDateTime.now());
        url.setId(7L);

        when(mockUrlRepository.findById((long) 7)).thenReturn(java.util.Optional.of(url));
        assertThat( urlService.getOriginalFull("h")).isEqualTo("https://stackoverflow.com/questions/63193906/application-run-failed-org-springframework-beans-factory-beandefinitionstoreexce");

    }
}