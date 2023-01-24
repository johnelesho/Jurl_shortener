package com.elsoft.assessment.jurlshortener.service.impl;

import com.elsoft.assessment.jurlshortener.exceptions.BadRequestException;
import com.elsoft.assessment.jurlshortener.service.UrlShortenerUtilsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class UrlShortenerUtilsServiceImplTest {

    @InjectMocks
    private UrlShortenerUtilsService shortenerUtilsService = new UrlShortenerUtilsServiceImpl();



    @Test
    public void convertToShort_lessThan62() {
        assertThat(shortenerUtilsService.retrieveStringFromId(10)).isEqualTo(urlBase+"2");
    }

    @Test
    public void convertToShort_moreThan62() {

        assertThat(shortenerUtilsService.retrieveStringFromId(78)).endsWith("wp");
    }
    private final String urlBase = "jurl.el/";
    @Test
    public void throwBadRequest_givenInvalidShortUrl() {
        assertThatExceptionOfType(BadRequestException.class).isThrownBy(()->shortenerUtilsService.retrieveIdFromString("l"));
    }
    @Test
    public void shouldConvertMaxLongToShortString() {
        String maxIdShortString = shortenerUtilsService.retrieveStringFromId(Long.MAX_VALUE);
        assertThat(maxIdShortString).isNotNull();
        assertThat(maxIdShortString).isNotEqualTo("");
    }
    @Test
    public void shouldThrowExceptionWhenShortStrLongerThanTenChars() {
        assertThatExceptionOfType(Exception.class).isThrownBy(()-> shortenerUtilsService.retrieveIdFromString("sclqgMAPqi2Z"));
    }
    @Test()
    public void shouldThrowExceptionWhenMalformedUrlSuppliedWithoutProtocol() throws MalformedURLException, URISyntaxException {
        assertThatExceptionOfType(MalformedURLException.class).isThrownBy(()-> shortenerUtilsService.getBaseUrl("malformed url dummy text"));
    }

    @Test()
    public void shouldThrowExceptionWhenMalformedUrlSuppliedWithIllegalChars() throws MalformedURLException, URISyntaxException {
        assertThatExceptionOfType(MalformedURLException.class).isThrownBy(()->shortenerUtilsService.getBaseUrl("malformed://example.com/foo"));
    }

    @Test
    public void shouldReturnBaseUrlWhenValidUrlSuppliedWithoutPort() throws MalformedURLException, URISyntaxException {
        assertThat(shortenerUtilsService.getBaseUrl("http://example.com/foo")).isEqualTo("http://example.com/");
    }

    @Test
    public void shouldReturnBaseUrlWhenValidUrlSuppliedWithPort() throws MalformedURLException, URISyntaxException {
        assertThat(shortenerUtilsService.getBaseUrl("http://example.com:8080/foo")).isEqualTo("http://example.com:8080/");
    }


    @Test
    public void encode_moreThan62() {

        assertThat( shortenerUtilsService.retrieveStringFromId(78)).endsWithIgnoringCase("WP");

    }


}