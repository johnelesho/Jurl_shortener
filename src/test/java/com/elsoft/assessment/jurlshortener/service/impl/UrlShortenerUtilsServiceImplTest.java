package com.elsoft.assessment.jurlshortener.service.impl;

import com.elsoft.assessment.jurlshortener.service.UrlShortenerUtilsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UrlShortenerUtilsServiceImplTest {

    @MockBean
    private UrlShortenerUtilsService shortenerUtilsService;

    @Test
    public void convertToShort_lessThan62() {
        assertEquals("k", shortenerUtilsService.retrieveStringFromId(10));
    }

    @Test
    public void convertToShort_moreThan62() {
        assertEquals("bq", shortenerUtilsService.retrieveStringFromId(78));
    }

    @Test
    public void retrieveIdFromString_singleCharacter() {
        assertEquals(11, shortenerUtilsService.retrieveIdFromString("l"));
    }

}