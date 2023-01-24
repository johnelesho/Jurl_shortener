package com.elsoft.assessment.jurlshortener.service.impl;

import com.elsoft.assessment.jurlshortener.dto.UrlRequest;
import com.elsoft.assessment.jurlshortener.entity.UrlEntity;
import com.elsoft.assessment.jurlshortener.repository.UrlRepository;
import com.elsoft.assessment.jurlshortener.service.UrlShortenerService;
import com.elsoft.assessment.jurlshortener.service.UrlShortenerUtilsService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UrlShortenerServiceImplTest {

    @Mock
    UrlRepository mockUrlRepository;

    @Mock
    UrlShortenerUtilsService urlShortenerUtilsService;

    @InjectMocks
    UrlShortenerService urlService;

    @Test
    public void generateShortLinkTest() {
        var url = new UrlEntity();
        url.setUrl("https://linkedln.com/in/johnelesho");
//        url.setCreatedDate(new Date());
        url.setId(5L);

        when(mockUrlRepository.save(any(UrlEntity.class))).thenReturn(url);
        when(urlShortenerUtilsService.retrieveStringFromId(url.getId())).thenReturn("f");

        var urlRequest = new UrlRequest();
        urlRequest.setUrl("https://github.com/johnelesho/droneAPI");

        assertEquals("f", urlService.generateShortLink(urlRequest));
    }

    @Test
    public void UrlShortenerController() {
        when(urlShortenerUtilsService.retrieveIdFromString("h")).thenReturn((long) 7);

        var url = new UrlEntity();
        url.setUrl("https://github.com/johnelesho/droneAPI");
        url.setId(7L);

        when(mockUrlRepository.findById((long) 7)).thenReturn(java.util.Optional.of(url));
        assertEquals("https://github.com/johnelesho/droneAPI", urlService.getOriginalFull("h"));

    }
}