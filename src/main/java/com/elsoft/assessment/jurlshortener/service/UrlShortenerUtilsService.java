package com.elsoft.assessment.jurlshortener.service;

public interface UrlShortenerUtilsService {

    String convertToShortUrl(long id);

    long retrieveLongUrl(String input);
}
