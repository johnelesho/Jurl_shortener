package com.elsoft.assessment.jurlshortener;

import com.elsoft.assessment.jurlshortener.controllers.UrlShortenerController;
import com.elsoft.assessment.jurlshortener.repository.UrlRepository;
import com.elsoft.assessment.jurlshortener.service.UrlShortenerService;
import com.elsoft.assessment.jurlshortener.service.UrlShortenerUtilsService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class JUrlShortenerApplicationTests {

    @Autowired
    UrlRepository urlRepository;
    @Autowired
    UrlShortenerUtilsService urlShortenerUtilsService;

    @Autowired
    UrlShortenerService urlShortenerService;

    @Autowired
    UrlShortenerController urlShortenerController;
    @Test
    void contextLoads() {
        Assertions.assertThat(urlRepository).isNotNull();
        Assertions.assertThat(urlShortenerService).isNotNull();
        Assertions.assertThat(urlShortenerUtilsService).isNotNull();
        Assertions.assertThat(urlShortenerController).isNotNull();
    }

}
